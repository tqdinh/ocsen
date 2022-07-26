package com.example.data


import com.example.domain.MyResource
import com.example.domain.entities.ImageInfo
import com.example.domain.entities.PlaceInfo
import com.example.domain.repositories.Repository
import com.example.domain.repositories.local.LocalDataSource
import com.example.domain.repositories.remote.RemoteDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class RepositoryWithProvider @Inject constructor(
    val localDataSource: LocalDataSource,
    val remoteDataSource: RemoteDataSource
) : Repository {

//    override fun getData() = flow<MyDataHolder<Any>> {
//
//        var data = "..................."
//        data = localDataSource.getData()
//        if (data.isNullOrBlank()) {
//            data = remoteDataSource.fetch()
//            localDataSource.saveData(data)
//        }
//        emit(MyDataHolder.Loading())
//        emit(MyDataHolder.Success(data + " Provider"))
//    }.catch {
//        emit(MyDataHolder.Fail())
//    }.flowOn(Dispatchers.IO)

    //    override fun getListPlace(): ArrayList<Any> {
//        return localDataSource.getListPlace()
//    }
//
//    override fun insertPlace(place: PlaceInfo) {
//        localDataSource.insertPlaceInfo(place)
//    }
    override fun insertPlaceInfo(placeInfo: PlaceInfo): Flow<MyResource<Any>> {

        return flow<MyResource<Any>> {
            emit(MyResource.LOADING(null))
            emit(MyResource.SUCCESS(localDataSource.insertPlaceInfo(placeInfo)))
        }.catch { e ->

            emit(MyResource.FAIL(null, e.toString()))
        }
            .flowOn(Dispatchers.IO)

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

    override fun addImageInfo(id: String, imageInfo: ImageInfo) :Flow<MyResource<Any>>{
        return flow<MyResource<Any>> {
            emit(MyResource.LOADING(null))
            emit(MyResource.SUCCESS(localDataSource))
        }.catch { e ->

            emit(MyResource.FAIL(null, e.toString()))
        }
            .flowOn(Dispatchers.IO)
    }

    override fun getPlacesInfo(): Flow<MyResource<Any>> {
        return flow<MyResource<Any>> {
            emit(MyResource.LOADING(null))
            emit(MyResource.SUCCESS(localDataSource.getListPlace()))
        }
            .catch { e ->
                emit(MyResource.FAIL(null, e.toString()))
            }
            .flowOn(Dispatchers.IO)
    }

    override fun addImage(placeId:String,imageInfo: ImageInfo): Flow<MyResource<Any>> {
        return flow<MyResource<Any>> {
            emit(MyResource.LOADING(null))
            emit(MyResource.SUCCESS(localDataSource.addImage(placeId,imageInfo)))
        }
            .catch { e ->
                emit(MyResource.FAIL(null, e.toString()))
            }
            .flowOn(Dispatchers.IO)
    }

    override fun getImagesInPlace(placeId: String): Flow<MyResource<Any>> {
        return flow<MyResource<Any>> {
            emit(MyResource.LOADING(null))
            emit(MyResource.SUCCESS(localDataSource.getImagesInPlace(placeId)))
        }
            .catch { e ->
                emit(MyResource.FAIL(null, e.toString()))
            }
            .flowOn(Dispatchers.IO)
    }


    override fun remoteImageFromPlace(placeInfo: PlaceInfo, imageInfo: ImageInfo): MyResource<Any> {
        TODO("Not yet implemented")
    }

    override fun getAllImage(): Any {
        return localDataSource.getAllImage()
    }

    override fun deleteImage(imageId: String) {
        localDataSource.deleteImage(imageId)
    }


}


