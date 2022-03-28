package com.example.data

import com.example.domain.MyResource
import com.example.domain.entities.ImageInfo
import com.example.domain.entities.PlaceInfo
import com.example.domain.repositories.Repository
import com.example.domain.repositories.local.LocalDataSource
import com.example.domain.repositories.remote.RemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RepositoryWithBind @Inject constructor()  : Repository {

    @Inject
    lateinit var  localDataSource: LocalDataSource
    @Inject
    lateinit var remoteDataSource: RemoteDataSource
    override fun insertPlaceInfo(placeInfo: PlaceInfo): Flow<MyResource<Any>> {
        TODO("Not yet implemented")
    }

    override fun updatePlaceInfo(
        oldPlaceInfo: PlaceInfo,
        newPlaceInfo: PlaceInfo
    ): MyResource<Any> {
        TODO("Not yet implemented")
    }

    override fun deletePlaceInfo(id: String): MyResource<Any> {
        TODO("Not yet implemented")
    }

    override fun getPlacesInfo(): Flow<MyResource<Any>> {
        TODO("Not yet implemented")
    }


    override fun addImageToPlace(placeInfo: PlaceInfo, imageInfo: ImageInfo): MyResource<Any> {
        TODO("Not yet implemented")
    }

    override fun remoteImageFromPlace(placeInfo: PlaceInfo, imageInfo: ImageInfo): MyResource<Any> {
        TODO("Not yet implemented")
    }


//    override fun getData() = flow<MyDataHolder<Any>> {
//        var data = "..................."
//        data = localDataSource.getData()
//        if (data.isNullOrBlank()) {
//            data = remoteDataSource.fetch()
//            localDataSource.saveData(data)
//        }
//        emit(MyDataHolder.Loading())
//        emit(MyDataHolder.Success(data+ "Binder"))
//    }.catch { emit(MyDataHolder.Fail()) }.flowOn(Dispatchers.IO)




}