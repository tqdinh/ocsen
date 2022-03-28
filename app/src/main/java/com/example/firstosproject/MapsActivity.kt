package com.example.firstosproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.example.domain.ResourceStatus
import com.example.domain.entities.PlaceInfo


import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

import com.example.firstosproject.viewmodel.MapViewModel

import com.example.firstosproject.databinding.ActivityMapsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapsActivity : AppCompatActivity(), OnMapReadyCallback, LifecycleOwner {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val exampleViewModel: MapViewModel by viewModels()

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        var buttonSet = binding.set
        var buttonGet = binding.get

        exampleViewModel.liveDataPlaceInfo.observe(this, { it ->
            when (it.status) {
                ResourceStatus.LOADING -> {

                }
                ResourceStatus.FAIL -> {

                }
                ResourceStatus.SUCCESS -> {
                    Toast.makeText(MapsActivity@ this, "Set Success", Toast.LENGTH_SHORT).show()


                }


            }

        })

        buttonSet.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                exampleViewModel.addPlace(PlaceInfo(lat = 10.1, lon = 11.1))
            }
        })

        buttonGet.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                exampleViewModel.getPlaceListPlace().observe(this@MapsActivity, {
                    when (it.status) {
                        ResourceStatus.SUCCESS -> {
                            var places = it.data as ArrayList<PlaceInfo>
                            Log.d("_TABLE_",places.toString())
                        }
                        ResourceStatus.FAIL -> {
                            Log.d("FAILLL",it.message)
                        }
                    }
                })
            }
        })

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


//        exampleViewModel.uiLiveDataBind.observe(this, { it ->
//            if (it is MyDataHolder.Success) {
//                it.data
//                var k = it.data.toString()
//
//                Log.d("DDDD", k)
//                Toast.makeText(MapsActivity@ this, k, Toast.LENGTH_SHORT).show()
//
//            } else
//                if (it is MyDataHolder.Loading) {
//                    Toast.makeText(MapsActivity@ this, "Loading", Toast.LENGTH_SHORT).show()
//                    Log.d("DDDD", "loading")
//                } else {
//                    if (it is MyDataHolder.Fail) {
//                        Toast.makeText(MapsActivity@ this, "Fail", Toast.LENGTH_SHORT).show()
//                        Log.d("DDDD", "fail")
//                    } else {
//                        Toast.makeText(MapsActivity@ this, "Noppppppe!!!", Toast.LENGTH_SHORT)
//                            .show()
//                        Log.d("DDDD", "Noppppppe!!!")
//                    }
//                }
//
//        })


    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}