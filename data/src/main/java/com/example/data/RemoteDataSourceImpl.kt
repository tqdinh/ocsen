package com.example.data

import com.example.domain.repositories.remote.RemoteDataSource
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(): RemoteDataSource {
    override fun fetch(): String {
        TODO("Not yet implemented")
    }
}