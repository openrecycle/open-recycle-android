package com.punksta.apps.openrecycle

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import com.punksta.apps.openrecycle.entity.ClassificationResult
import com.punksta.apps.openrecycle.entity.Photo
import com.punksta.apps.openrecycle.entity.Response
import com.punksta.apps.openrecycle.model.Model
import com.punksta.apps.openrecycle.ui.showPhoto
import rx.Single
import rx.android.schedulers.AndroidSchedulers
import rx.subscriptions.CompositeSubscription

/**
 * Created by stanislav on 3/11/17.
 */
class PhotoUploadActivity : AppCompatActivity() {
    private val compositeSubsctiption: CompositeSubscription = CompositeSubscription()

    private val type: String?
        get() = intent.getStringExtra("type")

    private var loaded = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val photo = Model.targetImage

        if (photo == null)
            finish()
        else {
            setContentView(R.layout.upload_photo_activity)
            (findViewById(R.id.image) as ImageView).showPhoto(photo)
            findViewById(R.id.cancel_uploading).setOnClickListener {
                finish()
            }
        }
    }

    companion object {
        private var cacheClassify: Single<ClassificationResult>? = null
        private var cacheLearn: Single<Response>? = null

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        loaded = savedInstanceState.getBoolean("loaded", false)
    }

    private fun uploadForLearning(photo: Photo, type: String) {
        when (cacheLearn) {
            null -> {
                Model.uploadMarkedData(photo, type).cache().apply {
                    cacheLearn = this
                }
            }
            else -> {
                cacheLearn!!
            }
        }.observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    loaded = true
                    AlertDialog.Builder(this)
                            .setTitle("Загрузка завершена")
                            .setCancelable(false)
                            .setMessage("Спасибо за фотографию")
                            .setPositiveButton("Ок", { d, e -> finish() })
                            .show()
                }, {
                    loaded = true
                    it.printStackTrace()
                    AlertDialog.Builder(this)
                            .setTitle("Ошибка загрузки")
                            .setCancelable(false)
                            .setMessage("Не удалось загрузить фото")
                            .setPositiveButton("Ок", { d, e -> finish() })
                            .show()
                })
                .run(compositeSubsctiption::add)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("loaded", loaded)
    }

    private fun uploadForClassify(photo: Photo) {
        when (cacheClassify) {
            null -> {
                Model.classify(photo).cache().apply {
                    cacheClassify = this
                }
            }
            else -> {
                cacheClassify!!
            }
        }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    loaded = true
                    AlertDialog.Builder(this)
                            .setTitle("Мусор обработан")
                            .setCancelable(false)
                            .setMessage("Результат распознования: ${it.type}")
                            .setPositiveButton("Ок", { d, e -> finish() })
                            .show()
                }, {
                    loaded = true
                    it.printStackTrace()
                    AlertDialog.Builder(this)
                            .setTitle("Ошибка загрузки")
                            .setCancelable(false)
                            .setMessage("Не удалось загрузить фото")
                            .setPositiveButton("Ок", { d, e -> finish() })
                            .show()
                })
                .run(compositeSubsctiption::add)
    }

    override fun onStart() {
        super.onStart()
        val t = type

        if (!loaded) {
            if (t != null) {
                uploadForLearning(Model.targetImage!!, t)
            } else {
                uploadForClassify(Model.targetImage!!)
            }
        }
    }

    override fun finish() {
        super.finish()
        cacheClassify = null
        cacheLearn = null
        Model.targetImage = null
    }

    override fun onStop() {
        super.onStop()
        compositeSubsctiption.clear()
    }
}