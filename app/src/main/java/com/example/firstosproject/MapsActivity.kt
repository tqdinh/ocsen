package com.example.firstosproject


import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.example.domain.ResourceStatus
import com.example.domain.entities.ImageInfo
import com.example.domain.entities.PlaceInfo
import com.example.firstosproject.Util.Companion.encodeAsBitmap
import com.example.firstosproject.Util.Companion.getCurrentDateTime
import com.example.firstosproject.Util.Companion.getJsonString
import com.example.firstosproject.databinding.ActivityMapsBinding
import com.example.firstosproject.services.ForegroundLocationService
import com.example.firstosproject.viewmodel.MapViewModel
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GooglePlayServicesUtil
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


@AndroidEntryPoint
class MapsActivity : AppCompatActivity(), OnMapReadyCallback, LifecycleOwner {
    val PHOTO_EXTENSION = ".jpg"
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private val imageCapture: ImageCapture by lazy {
        ImageCapture.Builder().setCaptureMode(ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY).build()
    }
    val REQUEST_LOCATION = 1011
    val REQUEST_CAMERA = 1012
    private lateinit var rawPath: String
    private lateinit var cameraExecutor: ExecutorService
    private var lensFacing = CameraSelector.LENS_FACING_BACK
    val viewModel: MapViewModel by viewModels()
    var latCoordinate = 0.0
    var longCoordinate = 0.0

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cameraExecutor = Executors.newSingleThreadExecutor()
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.toggleLocationUpdates()

        if (checkPermission(Manifest.permission.CAMERA)) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                REQUEST_CAMERA
            )
        } else {
            if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                AlertDialog.Builder(this)
                    .setMessage("Need camera permission to capture image. Please provide permission to access your camera.")
                    .setPositiveButton("OK") { dialogInterface, i ->
                        dialogInterface.dismiss()
                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(Manifest.permission.CAMERA),
                            REQUEST_CAMERA
                        )
                    }
                    .setNegativeButton("Cancel") { dialogInterface, i ->
                        dialogInterface.dismiss()
                    }
                    .create()
                    .show();
            }
        }

        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            REQUEST_LOCATION
        )


        var buttonSet = binding.set
        var buttonGet = binding.get

        var buttonGetImages = binding.btnGetImgs

        viewModel.liveDataPlaceInfo.observe(this, { it ->
            it?.let {
                when (it.status) {
                    ResourceStatus.LOADING -> {

                    }
                    ResourceStatus.FAIL -> {

                    }
                    ResourceStatus.SUCCESS -> {

                        Toast.makeText(
                            MapsActivity@ this,
                            "Set Success ${it.data?.id}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }


        })

        buttonSet.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {


            }
        })

        buttonGet.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                viewModel.getPlaceListPlace().observe(this@MapsActivity, {
                    when (it.status) {
                        ResourceStatus.SUCCESS -> {
                            var places = it.data as ArrayList<PlaceInfo>
                            Log.d("_TABLE_", places.toString())
                        }
                        ResourceStatus.FAIL -> {
                            Log.d("FAILLL", it.message)
                        }
                    }
                })
            }
        })

        binding.captureImageWithCoor.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {

                val intent = Intent(this@MapsActivity, CameraPreview::class.java)
                intent.putExtra("LATCOOR", latCoordinate)
                intent.putExtra("LONGCOOR", longCoordinate)
                startActivity(intent)
            }

        })

        buttonGetImages.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                viewModel.getImages(
                    "123456"
                ).observe(this@MapsActivity, {
                    when (it.status) {
                        ResourceStatus.SUCCESS -> {
                            var listImage = it.data as List<ImageInfo>
                            Log.d("_TABLE_", listImage.toString())
                        }
                        ResourceStatus.FAIL -> {
                            Log.d("FAILLL", it.message)
                        }
                    }
                })
            }
        })


        lifecycleScope.launch {
            viewModel.lastLocation.collect {
                it?.let {
                    Log.d("--LOCATION--", "${it.latitude},--,${it.longitude}")
                    latCoordinate = it.latitude
                    longCoordinate = it.longitude
                    binding.captureImageWithCoor.text =
                        getString(R.string.capture_image_with_latlng, it.latitude, it.longitude)
                }
            }
        }

    }


    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)
        viewModel.toggleLocationUpdates()
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    private fun checkPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CAMERA) {

            Log.d("REQUEST_CAMERA", "")
        } else if (requestCode == REQUEST_LOCATION) {
            Log.d("REQUEST_LOCATION", "")
        }
    }

    fun servicesOK(): Boolean {
        val result = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this)
        if (result == ConnectionResult.SUCCESS) {
            return true
        } else if (GooglePlayServicesUtil.isUserRecoverableError(result)) {
            val dialog: Dialog? =
                GooglePlayServicesUtil.getErrorDialog(result, this, 1234)
            dialog?.show()
        } else {
            Toast.makeText(this, "nonononono", Toast.LENGTH_LONG)
                .show()
        }
        return false
    }

    fun getOutputDirectory(context: Context): File {
        val appContext = context.applicationContext
        // If the app name contains (e.g. dev app) a space, it is a not converted to %20 automatically.
        val appNameLowerUnderscore =
            "dinh_app".lowercase().replace(" ", "_")
        val mediaDir = context.externalMediaDirs.firstOrNull()?.let {
            File(it, appNameLowerUnderscore).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else appContext.filesDir
    }

    private fun onImageSaved(savedUri: Uri) {
        rawPath = savedUri.path.toString()
        Log.d("PATH_IMGE", rawPath)
    }


    private fun takePhoto() {

        val photoFile =
            File(getOutputDirectory(this), System.currentTimeMillis().toString() + PHOTO_EXTENSION)

        val outputFileOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(outputFileOptions, cameraExecutor,
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(error: ImageCaptureException) {
                    Log.d("PATH_IMGE", "error+${error.toString()}")
                    // progressDialog?.dismiss()
                }

                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val savedUri = outputFileResults.savedUri ?: Uri.fromFile(photoFile)
                    onImageSaved(savedUri!!)
                }
            })
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build()

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                val cameraSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)

                // preview.setSurfaceProvider(binding.previewCam.surfaceProvider)

            } catch (exc: Exception) {
                Log.d("PATH_IMGE", "Failed to bind preview")
            }

        }, ContextCompat.getMainExecutor(this))
    }

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        private const val REQUEST_CODE_PERMISSIONS = 100
    }


    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onStart() {
        super.onStart()
        val serviceIntent = Intent(this, ForegroundLocationService::class.java)
        bindService(serviceIntent, viewModel, BIND_AUTO_CREATE)
    }

    override fun onStop() {
        super.onStop()
        unbindService(viewModel)
    }

}