package com.example.data

import androidx.room.RoomDatabase
import com.example.data.local.database.LocalDatabase
import com.example.data.local.entities.LocalImage
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
            .insertLocalPlace(placeInfo.let { LocalPlace(id=placeInfo.id,lat = it.lat, lon = it.lon) })
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

    override fun addImageInfo(id: String, imageInfo: ImageInfo) {

    }

    override fun addImage(placeId: String, imageInfo: ImageInfo): Any {

        val localPlace = LocalImage(
            place_id = placeId,
            imageInfo = imageInfo
        )
        database.localImageDao().insertImage(localPlace)

        return localPlace
    }

    override fun getImagesInPlace(placeId: String): Any {
        return database.localImageDao().getImagesOnPlace(placeId)
    }


    override fun remoteImageFromPlace(placeInfo: PlaceInfo, imageInfo: ImageInfo) {
        TODO("Not yet implemented")
    }

    override fun getAllImage(): Any {
        return database.localImageDao().getListLocalImage()
    }

    override fun deleteImage(imageId: String) {
        database.localImageDao().deleteImage(imageId)
    }
}