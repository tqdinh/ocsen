package com.example.domain.repositories.local

import com.example.domain.MyResource
import com.example.domain.entities.ImageInfo
import com.example.domain.entities.PlaceInfo
import kotlinx.coroutines.flow.Flow

interface  LocalDataSource {
    fun getData():String
    fun <T>saveData(data:T)

    fun getListPlace():ArrayList<Any>

    fun insertPlaceInfo(placeInfo: PlaceInfo): MyResource<Any>
    fun updatePlaceInfo(oldPlaceInfo:PlaceInfo,newPlaceInfo: PlaceInfo)
    fun deletePlaceInfo(id:String)
    fun addImageInfo(id:String,imageInfo: ImageInfo)
    fun addImage(placeId:String,imageInfo: ImageInfo):Any
    fun getImagesInPlace(placeId:String):Any
    fun remoteImageFromPlace(placeInfo: PlaceInfo, imageInfo: ImageInfo)

    fun getAllImage():Any
    fun deleteImage(imageId: String)

}