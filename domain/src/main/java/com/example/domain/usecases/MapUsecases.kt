package com.example.domain.usecases


import com.example.domain.MyResource
import com.example.domain.entities.ImageInfo
import com.example.domain.entities.PlaceInfo
import com.example.domain.repositories.Repository
import kotlinx.coroutines.flow.Flow

interface MapUsecases {
    fun insertPlaceInfo(placeInfo: PlaceInfo): Flow<MyResource<Any>>
    fun updatePlaceInfo(oldPlaceInfo: PlaceInfo, newPlaceInfo: PlaceInfo)
    fun deletePlaceInfo(id: String)
    fun addImageInfo(id: String, imageInfo: ImageInfo)

    fun getPlacesInfo(): Flow<MyResource<Any>>

    fun addImage(placeId: String, imageInfo: ImageInfo): Flow<MyResource<Any>>
    fun getImagesInPlace(placeId: String): Flow<MyResource<Any>>
    fun getAllImage(): Any
    fun deleteImage(imageId:String)
}