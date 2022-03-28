package com.example.domain.repositories.local

import com.example.domain.MyResource
import com.example.domain.entities.ImageInfo
import com.example.domain.entities.PlaceInfo

interface  LocalDataSource {
    fun getData():String
    fun <T>saveData(data:T)

    fun getListPlace():ArrayList<Any>

    fun insertPlaceInfo(placeInfo: PlaceInfo): MyResource<Any>
    fun updatePlaceInfo(oldPlaceInfo:PlaceInfo,newPlaceInfo: PlaceInfo)
    fun deletePlaceInfo(id:String)

    fun addImageToPlace(placeInfo: PlaceInfo,imageInfo: ImageInfo)
    fun remoteImageFromPlace(placeInfo: PlaceInfo, imageInfo: ImageInfo)
}