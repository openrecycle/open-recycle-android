package com.punksta.apps.openrecycle

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.support.v4.app.FragmentActivity
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.punksta.apps.openrecycle.entity.FilePhoto
import com.punksta.apps.openrecycle.entity.Photo
import com.punksta.apps.openrecycle.entity.RamPhoto
import com.punksta.apps.openrecycle.entity.UriPhoto

/**
 * Created by stanislav on 3/11/17.
 */

fun Context.getRequestManager(): RequestManager = when (this) {
    is ContextWrapper -> this.baseContext.getRequestManager()
    is FragmentActivity -> Glide.with(this)
    is Activity -> Glide.with(this)
    else -> Glide.with(this)
}

fun ImageView.showPhoto(photo: Photo) {
    val glide = context.getRequestManager()
    when (photo) {
        is FilePhoto -> {
            glide.load(photo.file).centerCrop().into(this)
        }
        is RamPhoto -> {
            glide.load(photo.byteArray).centerCrop().into(this)
        }
        is UriPhoto -> {
            glide.load(photo.uri).centerCrop().into(this)
        }
    }
}