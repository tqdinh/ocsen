package com.example.data


import com.example.domain.MyResource
import com.example.domain.entities.ImageInfo
import com.example.domain.entities.PlaceInfo
import com.example.domain.repositories.Repository
import com.example.domain.usecases.MapUsecases
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MapUseCasesImpl @Inject constructor(val repository: Repository) : MapUsecases {
    override fun insertPlaceInfo(placeInfo: PlaceInfo): Flow<MyResource<Any>> {
        return repository.insertPlaceInfo(placeInfo)
    }

    override fun updatePlaceInfo(oldPlaceInfo: PlaceInfo, newPlaceInfo: PlaceInfo) {
        TODO("Not yet implemented")
    }

    override fun deletePlaceInfo(id: String) {
        TODO("Not yet implemented")
    }

    override fun addImageInfo(id: String, imageInfo: ImageInfo) {

    }

    override fun getPlacesInfo(): Flow<MyResource<Any>> {
        return repository.getPlacesInfo()
    }

    override fun addImage(placeId: String, imageInfo: ImageInfo): Flow<MyResource<Any>> {
        return repository.addImage(placeId, imageInfo)
    }

    override fun getImagesInPlace(placeId: String): Flow<MyResource<Any>> {
        return repository.getImagesInPlace(placeId)
    }

    override fun getAllImage(): Any {
        return repository.getAllImage()
    }

    override fun deleteImage(imageId: String) {
        repository.deleteImage(imageId)
    }


}