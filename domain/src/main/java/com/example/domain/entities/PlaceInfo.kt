package com.example.domain.entities

import kotlinx.serialization.SerialName
import java.util.*
import kotlin.collections.ArrayList


data class PlaceInfo(
    @SerialName("id")
    val id:String=UUID.randomUUID().toString(),
    @SerialName("lat")
    val lat:Double =0.0,
    @SerialName("lon")
    val lon:Double = 0.0,
    @SerialName("img")
    val imgs:ArrayList<ImageInfo> = ArrayList()
)

data class ImageInfo(

    @SerialName("id")
    val id:String=UUID.randomUUID().toString(),
    @SerialName("path")
    val path:String="",
    @SerialName("title")
    val tille:String="",
    @SerialName("desc")
    val desc:String=""
)