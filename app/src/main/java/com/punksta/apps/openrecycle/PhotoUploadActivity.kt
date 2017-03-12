package com.punksta.apps.openrecycle

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import com.punksta.apps.openrecycle.model.Model
import com.punksta.apps.openrecycle.ui.showPhoto
import rx.android.schedulers.AndroidSchedulers
import rx.subscriptions.CompositeSubscription

/**
 * Created by stanislav on 3/11/17.
 */
class PhotoUploadActivity : AppCompatActivity() {
    private val compositeSubsctiption: CompositeSubscription = CompositeSubscription()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val photo = Model.targetImage

        if (photo == null)
            finish()
        else {
            setContentView(R.layout.upload_photo_activity)
            (findViewById(R.id.image) as ImageView).showPhoto(photo)
        }
    }

    override fun onStart() {
        super.onStart()
        Model.classify(Model.targetImage!!)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    AlertDialog.Builder(this)
                            .setTitle("Мусор обработон")
                            .setCancelable(false)
                            .setMessage("Результат распознования: ${it.type}")
                            .setPositiveButton("Ок", { d, e -> finish() })
                            .show()
                }, {
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

    override fun onStop() {
        super.onStop()
        compositeSubsctiption.clear()
    }
}