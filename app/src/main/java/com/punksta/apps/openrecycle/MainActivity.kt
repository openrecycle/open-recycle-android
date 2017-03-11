package com.punksta.apps.openrecycle

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.flurgle.camerakit.CameraListener
import com.punksta.apps.openrecycle.entity.RamPhoto
import com.punksta.apps.openrecycle.model.Model

class MainActivity : AppCompatActivity() {
    private val cameraView
        get() = findViewById(R.id.camera_view) as com.flurgle.camerakit.CameraView

    private val tabs
        get() = findViewById(R.id.tabs) as android.support.design.widget.TabLayout

    private val listener = object : CameraListener() {
        override fun onPictureTaken(jpeg: ByteArray) {
            super.onPictureTaken(jpeg)
            val camputeSize = cameraView.captureSize
            Model.targetImage = RamPhoto(camputeSize, jpeg)
            startActivity(Intent(this@MainActivity, PhotoUploadActivity::class.java))
            overridePendingTransition(0, 0)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        arrayOf(
                tabs.newTab().setText(R.string.tab_items_classify),
                tabs.newTab().setText(R.string.tab_item_send)
        ).forEach(tabs::addTab)

        cameraView.setJpegQuality(Model.JPEG_QUALITY)
        cameraView.setCameraListener(listener)
        cameraView.captureSize
        findViewById(R.id.take_picture).setOnClickListener {
            cameraView.captureImage()
        }
    }

    override fun onResume() {
        super.onResume()
        cameraView.start()
    }

    override fun onPause() {
        super.onPause()
        cameraView.stop()
    }
}