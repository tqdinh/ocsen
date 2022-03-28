package com.example.data.local.entities

import kotlinx.android.parcel.RawValue

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.domain.entities.ImageInfo
import com.example.domain.entities.PlaceInfo
import kotlinx.android.parcel.Parcelize

import kotlinx.serialization.SerialName
import java.util.*

@Entity(tableName = "LocalPlaceTable")
@TypeConverters(LocalImageConverter::class)
@Parcelize
data class LocalPlace(
    @PrimaryKey
    @SerialName("id") val id: String = UUID.randomUUID().toString(),
    @SerialName("lat")
    val lat: Double = 0.0,
    @SerialName("lon")
    val lon: Double = 0.0,
    @SerialName("imgs")
    val imgs: ArrayList<LocalImage>?= null

): Parcelable

