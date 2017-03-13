package com.punksta.apps.openrecycle

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.punksta.apps.openrecycle.entity.FilePhoto
import com.punksta.apps.openrecycle.model.Model
import com.punksta.apps.openrecycle.model.Model.garbageTypes
import com.punksta.apps.openrecycle.ui.BaseActivity
import com.punksta.apps.openrecycle.ui.MenuItem
import com.punksta.apps.openrecycle.ui.MenuItemHolder
import com.punksta.apps.openrecycle.ui.isPermissionsGranted
import pl.aprilapps.easyphotopicker.DefaultCallback
import pl.aprilapps.easyphotopicker.EasyImage
import rx.subscriptions.CompositeSubscription
import java.io.File

/**
 * Created by stanislav on 3/12/17.
 */
class HelpActivity : BaseActivity(), MenuItemHolder {
    private val compositeSubscription = CompositeSubscription()

    override val menuNameId: Int
        get() = MenuItem.HELP.titleRes

    val spinnerView
        get() = findViewById(R.id.spinner) as Spinner

    val currentSelectedType: String
        get() = spinnerView.selectedItemPosition
                .let(com.punksta.apps.openrecycle.model.Model.garbageTypes::get)
                .let(Pair<String, *>::first)

    private val CAMERA = 1;
    private val GALLERY = 2;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)
        spinnerView.adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, garbageTypes.map { it.second })

        findViewById(R.id.camera_button).setOnClickListener {
            if (isPermissionsGranted(*cameraPermission)) {
                EasyImage.openCamera(this, 0)
            } else {
                ActivityCompat.requestPermissions(this, cameraPermission, CAMERA)
            }
        }

        findViewById(R.id.gallery_button).setOnClickListener {
            if (isPermissionsGranted(*galleryPermission)) {
                EasyImage.openChooserWithGallery(this, getString(R.string.select_gallery), 0)
            } else {
                ActivityCompat.requestPermissions(this, cameraPermission, GALLERY)
            }
        }
    }

    private val galleryPermission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    private val cameraPermission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)

    private val callback = object : DefaultCallback() {
        override fun onImagePicked(imageFile: File, source: EasyImage.ImageSource?, type: Int) {
            uploadPhoto(imageFile)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CAMERA -> {
                if (isPermissionsGranted(*cameraPermission)) {
                    EasyImage.openCamera(this, 0)
                }
            }

            GALLERY -> {
                if (isPermissionsGranted(*cameraPermission)) {
                    EasyImage.openChooserWithGallery(this, getString(R.string.select_gallery), 0)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        EasyImage.handleActivityResult(requestCode, resultCode, data, this, callback)
    }

    private fun uploadPhoto(file: File) {
        Model.targetImage = FilePhoto(file)
        val intent = with(Intent(this, PhotoUploadActivity::class.java)) {
            putExtra("type", currentSelectedType)
        }
        startActivity(intent)
    }
}