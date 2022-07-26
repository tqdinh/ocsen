package com.example.domain.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.SerialName
import java.util.*
import kotlin.collections.ArrayList


data class PlaceInfo(
    @SerialName("id")
    val id: String = UUID.randomUUID().toString(),
    @SerialName("lat")
    var lat: Double = 0.0,
    @SerialName("lon")
    var lon: Double = 0.0,
    @SerialName("img")
    val imgs: ArrayList<ImageInfo> = ArrayList()
)

@Parcelize
data class ImageInfo(
    @SerialName("path")
    val path: String = "",
    @SerialName("title")
    val title: String = "",
    @SerialName("desc")
    val desc: String = "",
    @SerialName("lat")
    var lat: Double = 0.0,
    @SerialName("lon")
    var lon: Double = 0.0,
) : Parcelable