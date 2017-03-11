package com.punksta.apps.openrecycle.model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.flurgle.camerakit.Size
import com.punksta.apps.openrecycle.RApplicaiton
import com.punksta.apps.openrecycle.entity.Photo
import rx.schedulers.Schedulers
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
    private val api: Api by lazy { Api.Factory.getApi(RApplicaiton.instance) }
    private val apiScheduler = Schedulers.from(ThreadPoolExecutor(0, 4, 2L, TimeUnit.MINUTES, LinkedBlockingDeque()))

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
//
//    fun classify(photo: Photo) : Single<ClassificationResult> {
//
//    }
}
