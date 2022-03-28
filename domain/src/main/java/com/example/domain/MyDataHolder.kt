package com.example.domain

import java.util.*

//sealed class MyDataHolder<out T : Any> {

//    companion object {
//        const val DEFAULT_ERROR_STR = "Error"
//        const val DEFAULT_LOADING_STR = "Loading"
//
//    }
//
//    enum class FailType {
//        ERROR, WARNING, INFO
//    }
//
//    data class Success<out T : Any>(val data: T) : MyDataHolder<T>() {
//        var isObservered = false
//            private set
//
//        fun setObserved() {
//            isObservered = true
//        }
//    }
//
//    data class Fail(
//        val errorResourceId: Int? = null,
//        val errStr: String = DEFAULT_ERROR_STR,
//        val error: BaseError? = null,
//        val failType: FailType = FailType.ERROR,
//        val cancellable: Boolean = false
//    ) : MyDataHolder<Nothing>() {
//        var isObserved = false
//            private set
//
//        fun setObserved() {
//            isObserved = true
//        }
//
//    }
//
//    data class Loading(
//        val loadingResource: Int? = null,
//        val loadingStr: String = DEFAULT_LOADING_STR,
//        val cancellable: Boolean = false,
//        var progress: Int = 0,
//        var tag: String = UUID.randomUUID().toString()
//    ) : MyDataHolder<Nothing>()
//
//    object Initial : MyDataHolder<Nothing>()
//
//    fun <T : Any, R : Any> MyDataHolder<T>.handleSuccess(
//        successBlock: (dataHolder: MyDataHolder.Success<T>) -> R
//    ): MyDataHolder<R> = when (this) {
//        is MyDataHolder.Success<T> -> MyDataHolder.Success(successBlock.invoke(this))
//        is MyDataHolder.Fail -> this
//        is MyDataHolder.Loading -> this
//        is MyDataHolder.Initial -> this
//    }
//
//
//    suspend fun <T : Any, R : Any> MyDataHolder<T>.handleSuccessSuspend(
//        successBlock: suspend (dataHolder: MyDataHolder.Success<T>) -> R
//    ): MyDataHolder<R> = when (this) {
//        is MyDataHolder.Success<T> -> MyDataHolder.Success(successBlock.invoke(this))
//        is MyDataHolder.Fail -> this
//        is MyDataHolder.Loading -> this
//        is MyDataHolder.Initial -> this
//    }

//}

class BaseError {

}
enum class ResourceStatus {
    SUCCESS, FAIL, LOADING
}

sealed class MyResource<T>(val status: ResourceStatus, val data: T?, val message: String) {

    data class SUCCESS<T>(val _data: T) : MyResource<T>(ResourceStatus.SUCCESS, _data, "")

    data class FAIL<T>(val _data: T?, val _message: String) :
        MyResource<T>(ResourceStatus.FAIL, _data, _message)

    data class LOADING<T>(val _data: T?, val _message: String = "...Loading...") :
        MyResource<T>(ResourceStatus.LOADING, _data, _message)
}