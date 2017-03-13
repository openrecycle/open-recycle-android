package com.punksta.apps.openrecycle

import android.content.Intent
import android.os.Bundle
import com.flurgle.camerakit.CameraListener
import com.punksta.apps.openrecycle.entity.RamPhoto
import com.punksta.apps.openrecycle.model.Model
import com.punksta.apps.openrecycle.ui.BaseActivity
import com.punksta.apps.openrecycle.ui.MenuItemHolder

class MainActivity : BaseActivity(), MenuItemHolder {
    override val menuNameId: Int
        get() = R.string.menu_sort

    private val cameraView
        get() = findViewById(R.id.camera_view) as com.flurgle.camerakit.CameraView

    private val listener = object : CameraListener() {
        override fun onPictureTaken(jpeg: ByteArray) {
            super.onPictureTaken(jpeg)
            val captureSize = cameraView.captureSize
            Model.targetImage = RamPhoto(captureSize, jpeg)
            startActivity(Intent(this@MainActivity, PhotoUploadActivity::class.java))
            overridePendingTransition(0, 0)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById(R.id.take_picture).setOnClickListener {
            cameraView.captureImage()
        }
    }

    override fun onResume() {
        super.onResume()
        cameraView.setJpegQuality(Model.JPEG_QUALITY)
        cameraView.setCameraListener(listener)

        cameraView.start()
    }

    override fun onPause() {
        super.onPause()
        cameraView.stop()
    }
}