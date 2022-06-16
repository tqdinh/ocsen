package com.example.firstosproject.viewmodel

import android.content.ServiceConnection
import android.location.Location
import androidx.lifecycle.*


import com.example.domain.MyResource
import com.example.domain.entities.ImageInfo
import com.example.domain.entities.PlaceInfo
import com.example.domain.usecases.MapUsecases
import com.example.firstosproject.DI.ProviderMapuseCase
import com.example.firstosproject.services.ForegroundLocationServiceConnection
import com.google.android.gms.location.sample.foregroundlocation.data.LocationPreferences
import com.google.android.gms.location.sample.foregroundlocation.data.LocationRepository
import com.google.android.gms.location.sample.foregroundlocation.data.PlayServicesAvailabilityChecker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

//import com.google.android.gms.location.sample.foregroundlocation.data.LocationPreferences

@HiltViewModel
class MapViewModel @Inject constructor(
    @ProviderMapuseCase val usecases: MapUsecases,
    playServicesAvailabilityChecker: PlayServicesAvailabilityChecker,
    val locationRepository: LocationRepository,
    private val locationPreferences: LocationPreferences,
    private val serviceConnection: ForegroundLocationServiceConnection
) :
    ViewModel(), ServiceConnection by serviceConnection {

    val isReceivingLocationUpdates = locationRepository.isReceivingLocationUpdates
    val lastLocation =locationRepository.lastLocation


//    fun triggerOnBind() {
//        viewModelScope.launch {
//            bindTrigger.emit(true)
//        }
//    }

//    fun triggerOnProvide() {
//        viewModelScope.launch {
//            bindTrigger.emit(true)
//        }
//    }


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

    fun addImageInfo(placeId: String, imageInfo: ImageInfo): LiveData<MyResource<ImageInfo>> {
        return (usecases.addImage(placeId, imageInfo) as Flow<MyResource<ImageInfo>>).asLiveData()
    }


    fun getImages(placeId: String): LiveData<MyResource<List<ImageInfo>>> {
        return (usecases.getImagesInPlace(placeId) as Flow<MyResource<List<ImageInfo>>>).asLiveData()
    }


    fun addPlace(placeInfo: PlaceInfo) {
        this.placeInfo.value = placeInfo
    }

    fun updatePlace(id: String, imageInFoList: ArrayList<ImageInfo>) {

    }


    fun toggleLocationUpdates() {
        if (isReceivingLocationUpdates.value) {
            stopLocationUpdates()
        } else {
            startLocationUpdates()
        }
    }

    private fun startLocationUpdates() {
        serviceConnection.service?.startLocationUpdates()
        // Store that the user turned on location updates.
        // It's possible that the service was not connected for the above call. In that case, when
        // the service eventually starts, it will check the persisted value and react appropriately.
        viewModelScope.launch {
            locationPreferences.setLocationTurnedOn(true)
        }
    }

    private fun stopLocationUpdates() {
        serviceConnection.service?.stopLocationUpdates()
        // Store that the user turned off location updates.
        // It's possible that the service was not connected for the above call. In that case, when
        // the service eventually starts, it will check the persisted value and react appropriately.
        viewModelScope.launch {
            locationPreferences.setLocationTurnedOn(false)
        }
    }

}

