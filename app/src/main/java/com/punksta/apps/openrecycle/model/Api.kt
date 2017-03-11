package com.punksta.apps.openrecycle.model

import android.content.Context
import com.punksta.apps.openrecycle.entity.ClassificationResult
import com.punksta.apps.openrecycle.entity.GarbageType
import com.punksta.apps.openrecycle.entity.Photo
import com.punksta.apps.openrecycle.entity.SendingImagesResult
import rx.Single

/**
 * Created by stanislav on 3/11/17.
 */
interface Api {
    fun classify(cameraResult: Photo): Single<ClassificationResult>
    fun sendImages(type: GarbageType, array: Array<Photo>): Single<SendingImagesResult>

    companion object Factory {
        fun getApi(context: Context): Api {
            return object : Api {
                override fun classify(cameraResult: Photo): Single<ClassificationResult> {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun sendImages(type: GarbageType, array: Array<Photo>): Single<SendingImagesResult> {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

            }
        }
    }
}