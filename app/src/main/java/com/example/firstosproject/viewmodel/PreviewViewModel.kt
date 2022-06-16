package com.example.firstosproject.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.*
import com.example.data.local.entities.LocalImage


import com.example.domain.MyResource
import com.example.domain.entities.ImageInfo
import com.example.domain.entities.PlaceInfo
import com.example.domain.repositories.Repository
import com.example.domain.usecases.MapUsecases
import com.example.firstosproject.DI.BinderMapUsecase
import com.example.firstosproject.DI.ProviderMapuseCase
import com.example.firstosproject.DI.ProviderProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PreviewViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val savedStateHandle: SavedStateHandle,
    @ProviderMapuseCase val usecases: MapUsecases
) : ViewModel() {
    val latCoordinate: Double? = savedStateHandle["LATCOOR"]
    val longCoordinate: Double? = savedStateHandle["LONGCOOR"]

    val bindTrigger = MutableStateFlow<Boolean>(false)
//    val uiLiveDataBind: LiveData<MyDataHolder<Any>> = bindTrigger.flatMapLatest {
//        if(it)
//            usecases.
//            getData()
//            //repository.getData()
//        else
//            flow {  }
//
//    }.asLiveData()

    val placeInfo: MutableStateFlow<PlaceInfo> = MutableStateFlow(PlaceInfo(id = ""))
    val liveDataPlaceInfo: LiveData<MyResource<PlaceInfo>> = placeInfo.flatMapLatest {
        if (it.id.isNotBlank()) {
            usecases.insertPlaceInfo(placeInfo.value) as Flow<MyResource<PlaceInfo>>

        } else
            flow { }

    }.asLiveData()

    fun getPlaceListPlace(): LiveData<MyResource<ArrayList<PlaceInfo>>> {
        return (usecases.getPlacesInfo() as Flow<MyResource<ArrayList<PlaceInfo>>>).asLiveData()
    }

    fun addImageInfo(placeId: String, imageInfo: ImageInfo): LiveData<MyResource<LocalImage>> {
        return (usecases.addImage(placeId, imageInfo) as Flow<MyResource<LocalImage>>).asLiveData()

    }


    fun getImages(placeId: String): LiveData<MyResource<List<ImageInfo>>> {
        return (usecases.getImagesInPlace(placeId) as Flow<MyResource<List<ImageInfo>>>).asLiveData()
    }


    fun addPlace(placeInfo: PlaceInfo, latAndLong: Pair<Double, Double>): String {

        placeInfo.lat = latAndLong.first
        placeInfo.lon = latAndLong.second
        this.placeInfo.value = placeInfo
        return placeInfo.id
    }

    fun updatePlace(id: String, imageInFoList: ArrayList<ImageInfo>) {

    }

//    @SuppressLint("MissingPermission")
//    fun getLatAndLong(): Pair<Double, Double> {
//        var lat = -90.0
//        var long = 180.0
//        var location: Location? = null
//        try {
//            val locationManager =
//                applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
//            // Providers are passive,gps,network
//            val providers = locationManager.getProviders(true)
//            for (provider in providers) {
//                val locationListener = object : LocationListener {
//                    override fun onLocationChanged(location: Location) {}
//                    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
//                    override fun onProviderEnabled(provider: String) {}
//                    override fun onProviderDisabled(provider: String) {}
//                }
//                locationManager.requestLocationUpdates(provider, 0, 0F, locationListener)
//                val lastKnownLocation = locationManager.getLastKnownLocation(provider)
//                if (lastKnownLocation != null) {
//                    location = lastKnownLocation
//                    locationManager.removeUpdates(locationListener)
//                }
//            }
//            if (location != null) {
//                lat = location.latitude
//                long = location.longitude
//            } else {
//                Log.d("LOCATION", "Cannot get device location")
//            }
//        } catch (e: Exception) {
//            Log.e("LOCATION", e.toString())
//
//        }
//        Log.d("LOCATION", "Location: $lat, $long")
//        return lat to long
//    }
}