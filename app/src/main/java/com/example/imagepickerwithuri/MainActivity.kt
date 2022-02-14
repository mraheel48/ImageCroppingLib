package com.example.imagepickerwithuri

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.canhub.cropper.CropImage
import com.canhub.cropper.CropImageView
import com.example.imagepickerwithuri.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(),
    CropImageView.OnSetImageUriCompleteListener,
    CropImageView.OnCropImageCompleteListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cropImageView.let {
            it.setOnSetImageUriCompleteListener(this)
            it.setOnCropImageCompleteListener(this)
            it.setAspectRatio(1, 2)
            if (savedInstanceState == null) it.imageResource = R.drawable.cat
        }

        binding.imagePicker.setOnClickListener {
            binding.cropImageView.croppedImageAsync()
        }

    }

    override fun onSetImageUriComplete(view: CropImageView, uri: Uri, error: Exception?) {
        //Log.d("myCropping","onCropImageComplete")
    }

    override fun onCropImageComplete(view: CropImageView, result: CropImageView.CropResult) {
        handleCropResult(result)

        Log.d("myCropping", "onCropImageComplete")
    }

    private fun handleCropResult(result: CropImageView.CropResult?) {
        if (result != null && result.error == null) {
            val imageBitmap = if (binding.cropImageView.cropShape == CropImageView.CropShape.OVAL)
                result.bitmap?.let { CropImage.toOvalBitmap(it) }
            else result.bitmap
            this.let { Log.v("File Path", result.getUriFilePath(it).toString()) }
            SCropResultActivity.start(imageBitmap, result.uriContent)
            startActivity(Intent(this, SCropResultActivity::class.java))


        } else {
            Log.e("AIC", "Failed to crop image", result?.error)
            Toast
                .makeText(this, "Crop failed: ${result?.error?.message}", Toast.LENGTH_SHORT)
                .show()
        }
    }

}