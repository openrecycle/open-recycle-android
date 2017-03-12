package com.punksta.apps.openrecycle

import android.app.Application
import pl.aprilapps.easyphotopicker.EasyImage

/**
 * Created by stanislav on 3/11/17.
 */
class RApplicaiton : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this

        EasyImage.configuration(this)
                .setImagesFolderName("open recycle")
                .saveInAppExternalFilesDir()
    }

    companion object {
        lateinit var instance: RApplicaiton
    }
}