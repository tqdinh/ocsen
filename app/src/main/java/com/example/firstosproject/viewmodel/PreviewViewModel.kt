package com.example.firstosproject.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.camera.core.CameraSelector
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
import com.google.android.gms.location.sample.foregroundlocation.data.LocationRepository
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
    val locationRepository: LocationRepository,
    @ProviderMapuseCase val usecases: MapUsecases
) : ViewModel() {
    var lensFacing: Int = CameraSelector.LENS_FACING_BACK

    fun updateCameraLensFacing(lensFacing: Int) {
        this.lensFacing = lensFacing
    }

    val lastLocation =locationRepository.lastLocation

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

}