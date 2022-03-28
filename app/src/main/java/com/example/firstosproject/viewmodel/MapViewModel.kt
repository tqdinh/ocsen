package com.example.firstosproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData


import com.example.domain.MyResource
import com.example.domain.entities.PlaceInfo
import com.example.domain.repositories.Repository
import com.example.domain.usecases.MapUsecases
import com.example.firstosproject.DI.BinderMapUsecase
import com.example.firstosproject.DI.ProviderMapuseCase
import com.example.firstosproject.DI.ProviderProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
//class MapViewModel @Inject constructor(@ProviderProvider val repository: Repository) : ViewModel() {
class MapViewModel @Inject constructor(@ProviderMapuseCase val usecases: MapUsecases) :
    ViewModel() {

//    val bindTrigger = MutableStateFlow<Boolean>(false)
//    val uiLiveDataBind: LiveData<MyDataHolder<Any>> = bindTrigger.flatMapLatest {
//        if(it)
//            usecases.
//            getData()
//            //repository.getData()
//        else
//            flow {  }
//
//    }.asLiveData()


//    fun triggerOnBind() {
//        viewModelScope.launch {
//            bindTrigger.emit(true)
//        }
//    }

//    fun triggerOnProvide() {
//        viewModelScope.launch {
//            bindTrigger.emit(true)
//        }
//    }


    val placeInfo: MutableStateFlow<PlaceInfo> = MutableStateFlow(PlaceInfo(id = ""))
    val liveDataPlaceInfo: LiveData<MyResource<PlaceInfo>> = placeInfo.flatMapLatest {
        if (it.id.isNotBlank()) {
            usecases.insertPlaceInfo(placeInfo.value) as Flow<MyResource<PlaceInfo>>
        } else
            flow { }

    }.asLiveData()

    fun getPlaceListPlace(): LiveData<MyResource<ArrayList<PlaceInfo>>> {
        return (usecases.getPlacesInfo() as Flow<MyResource<ArrayList<PlaceInfo>>>).asLiveData()
    }


    fun addPlace(placeInfo: PlaceInfo) {
        this.placeInfo.value = placeInfo
    }

}