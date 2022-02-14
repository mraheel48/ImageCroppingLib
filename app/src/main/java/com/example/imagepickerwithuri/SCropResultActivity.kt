package com.example.imagepickerwithuri

import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import com.example.imagepickerwithuri.databinding.ActivityScropResultBinding

class SCropResultActivity : AppCompatActivity() {

    companion object {

        fun start(imageBitmap: Bitmap?, uri: Uri?) {
            image = imageBitmap
            URI = uri
        }

        var image: Bitmap? = null

        private const val SAMPLE_SIZE = "SAMPLE_SIZE"
        var URI: Uri? = null
    }

    private lateinit var binding: ActivityScropResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = ActivityScropResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.resultImageView.setOnClickListener {
            releaseBitmap()
            finish()
        }

        image?.let {
            binding.resultImageView.setImageBitmap(it)
            val sampleSize = intent.getIntExtra(SAMPLE_SIZE, 1)
            val ratio = (10 * it.width / it.height.toDouble()).toInt() / 10.0
            val byteCount: Int = it.byteCount / 1024
            val desc =
                "(${it.width}, ${it.height}), Sample: $sampleSize, Ratio: $ratio, Bytes: $byteCount K"

        } ?: run {

            if (URI != null) binding.resultImageView.setImageURI(URI)
            else Toast.makeText(this, "No image is set to show", Toast.LENGTH_LONG).show()

        }
    }


    private fun releaseBitmap() {
        image?.let {
            it.recycle()
            image = null
        }
    }
}