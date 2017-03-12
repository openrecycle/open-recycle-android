package com.punksta.apps.openrecycle.entity

import android.net.Uri
import com.flurgle.camerakit.Size
import java.io.File

/**
 * Created by stanislav on 3/11/17.
 */
sealed class Photo

class RamPhoto(val size: Size, val byteArray: ByteArray) : Photo()

class FilePhoto(val file: File) : Photo()

class UriPhoto(val uri: Uri) : Photo()
