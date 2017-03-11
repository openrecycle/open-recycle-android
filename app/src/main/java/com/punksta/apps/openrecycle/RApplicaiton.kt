package com.punksta.apps.openrecycle

import android.app.Application

/**
 * Created by stanislav on 3/11/17.
 */
class RApplicaiton : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: RApplicaiton
    }
}