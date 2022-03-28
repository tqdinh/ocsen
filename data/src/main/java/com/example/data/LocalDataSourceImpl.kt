package com.example.data

import androidx.room.RoomDatabase
import com.example.data.local.database.LocalDatabase
import com.example.data.local.entities.LocalPlace
import com.example.domain.MyResource

import com.example.domain.entities.ImageInfo
import com.example.domain.entities.PlaceInfo
import com.example.domain.repositories.local.LocalDataSource
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(val database: LocalDatabase) : LocalDataSource {
    override fun getData(): String {
        return "impl"
        // TODO("Not yet implemented")
    }

    override fun <T> saveData(data: T) {
        //  TODO("Not yet implemented")
    }

    override fun getListPlace(): ArrayList<Any> {
        return database.localPlaceDao().getListLocalPlace() as ArrayList<Any>

    }

    override fun insertPlaceInfo(placeInfo: PlaceInfo): MyResource<Any> {
        database.localPlaceDao()
            .insertLocalPlace(placeInfo.let { LocalPlace(lat = it.lat, lon = it.lon) })
        return MyResource.SUCCESS(placeInfo)
    }

//    override fun insertPlaceInfo(placeInfo: PlaceInfo) {
//        var localPlace: LocalPlace = placeInfo.let {
//            LocalPlace(lat=it.lat,lon=it.lon)
//        }
//        database.localPlaceDao().insertLocalPlace(localPlace)
//    }

    override fun updatePlaceInfo(oldPlaceInfo: PlaceInfo, newPlaceInfo: PlaceInfo) {
        TODO("Not yet implemented")
    }

    override fun deletePlaceInfo(id: String) {
        TODO("Not yet implemented")
    }

    override fun addImageToPlace(placeInfo: PlaceInfo, imageInfo: ImageInfo) {
        TODO("Not yet implemented")
    }

    override fun remoteImageFromPlace(placeInfo: PlaceInfo, imageInfo: ImageInfo) {
        TODO("Not yet implemented")
    }
}