package com.example.domain.repositories

import com.example.domain.MyResource
import com.example.domain.entities.ImageInfo
import com.example.domain.entities.PlaceInfo
import kotlinx.coroutines.flow.Flow


interface Repository {
    //    fun getData():Flow<MyDataHolder<Any>>
//
    fun insertPlaceInfo(placeInfo: PlaceInfo): Flow<MyResource<Any>>
    fun updatePlaceInfo(oldPlaceInfo: PlaceInfo, newPlaceInfo: PlaceInfo): MyResource<Any>
    fun deletePlaceInfo(id: String): MyResource<Any>

    fun getPlacesInfo(): Flow<MyResource<Any>>

    fun addImageToPlace(placeInfo: PlaceInfo, imageInfo: ImageInfo): MyResource<Any>
    fun remoteImageFromPlace(placeInfo: PlaceInfo, imageInfo: ImageInfo): MyResource<Any>
}