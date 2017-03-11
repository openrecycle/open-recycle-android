package com.punksta.apps.openrecycle

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import com.punksta.apps.openrecycle.model.Model

/**
 * Created by stanislav on 3/11/17.
 */
class PhotoUploadActivity : AppCompatActivity() {
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


    override fun onPause() {
        super.onPause()

    }
}