package com.punksta.apps.openrecycle.model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.flurgle.camerakit.Size
import com.google.gson.Gson
import com.punksta.apps.openrecycle.RApplicaiton
import com.punksta.apps.openrecycle.entity.*
import com.punksta.apps.openrecycle.model.utils.FileUtils
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import rx.Single
import rx.schedulers.Schedulers
import java.io.ByteArrayOutputStream
import java.util.concurrent.LinkedBlockingDeque
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

/**
 * Created by stanislav on 3/11/17.
 */

object Model {
    val JPEG_QUALITY = 60
    private val maxWidth = 450
    private val maxHeight = 700
    private val apiUrl = "http://209.205.126.27:5000"
    private val userId = "android"
    private val source = "android"

    private val gson: Gson = Gson()
    private val apiScheduler = Schedulers.from(ThreadPoolExecutor(1, 4, 2L, TimeUnit.MINUTES, LinkedBlockingDeque()))
    private val api: ApiInterface by lazy { ApiFactory.getApi(apiUrl, apiScheduler, gson) }

    var targetImage: Photo? = null

    fun preProcessImage(jpeg: ByteArray, camptureSize: Size): Bitmap {

        var finalSize = Size(camptureSize.width, camptureSize.height)
        var inSampleSize = 1
        while (finalSize.width > maxWidth || finalSize.height > maxHeight) {
            finalSize = Size(finalSize.width / 2, finalSize.width / 2)
            inSampleSize *= 2
        }
        return BitmapFactory.decodeByteArray(jpeg, 0, jpeg.size, BitmapFactory.Options().also { it.inSampleSize = inSampleSize })
    }


    private fun createPartFromPhoto(filename: String, photo: Photo): Single<MultipartBody.Part> {
        return Single.create<MultipartBody.Part> {

            val part = when (photo) {
                is RamPhoto -> {
                    val b = preProcessImage(photo.byteArray, photo.size)
                    val stream = ByteArrayOutputStream()
                    b.compress(Bitmap.CompressFormat.PNG, JPEG_QUALITY, stream)
                    b.recycle()
                    MultipartBody.Part.createFormData(filename, filename, RequestBody.create(MediaType.parse("image/*"), stream.toByteArray()))
                }
                is FilePhoto -> {
                    MultipartBody.Part.createFormData(filename, filename, RequestBody.create(MediaType.parse("image/*"), photo.file))
                }
                is UriPhoto -> {
                    val file = FileUtils.getPath(RApplicaiton.instance, photo.uri)
                    MultipartBody.Part.createFormData(filename, filename, RequestBody.create(MediaType.parse("image/*"), file))
                }
            }
            it.onSuccess(part)
        }
    }

    private fun getPhotoName(photo: Photo): String {
        return "example.jpg"
    }

    fun uploadMarkedData(photo: Photo, type: String): Single<Response> {
        val name = getPhotoName(photo)
        return createPartFromPhoto(name, photo)
                .flatMap { api.uploadMarkedData(it, name, type, userId, source) }
    }

    fun classify(photo: Photo): Single<Response> {
        val name = getPhotoName(photo)
        return createPartFromPhoto(name, photo)
                .flatMap { api.classify(it, name, userId, source) }
    }

    val garbageTypes = listOf(
            "plastic bottle" to "бутылка PET прозрачная",
            "glass bottle" to "бутылка стеклянная",
            "thermal paper receipt" to "лента чековая",
            "plastic bag" to "мягкий пластик",
            "disposable paper cup" to "бумажный стаканчик"
    )
}
