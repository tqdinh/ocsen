package com.example.data.local.database
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.local.dao.LocalImageDao
import com.example.data.local.dao.LocalPlaceDao
import com.example.data.local.entities.LocalImage
import com.example.data.local.entities.LocalPlace

import com.example.domain.entities.PlaceInfo

@Database(entities = [LocalPlace::class, LocalImage::class], version = 1)
abstract class LocalDatabase :RoomDatabase(){
    abstract fun localPlaceDao(): LocalPlaceDao
    abstract fun localImageDao():LocalImageDao
}

