package com.example.data.local.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.domain.entities.ImageInfo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.SerialName
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.ArrayList

@Entity(tableName = "LocalImageTable", primaryKeys = ["id", "place_id"])
@Parcelize
@TypeConverters(ImageInfoConverter::class)
data class LocalImage(
    @SerialName("id")
    val id: String = UUID.randomUUID().toString(),
    @SerialName("place_id")
    val place_id: String,

    @SerialName("imageInfo")
    val imageInfo: ImageInfo?=null

) : Parcelable


class ImageInfoConverter {
    @TypeConverter
    fun stringToModel(data: String?): ImageInfo? {
        return Gson().fromJson(data, ImageInfo::class.java)
    }

    @TypeConverter
    fun modelToString(model: ImageInfo?): String? {
        return Gson().toJson(model)
    }

}

class LocalImageConverter {

    @TypeConverter
    fun stringToLocalImageList(data: String?): ArrayList<LocalImage?>? {
        val listType: Type = object : TypeToken<ArrayList<LocalImage?>?>() {}.type
        return Gson().fromJson(data, listType)
    }

    @TypeConverter
    fun localImageListToString(media: ArrayList<LocalImage?>?): String? {
        return Gson().toJson(media)
    }


}