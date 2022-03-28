package com.example.data.local.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.SerialName
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.ArrayList

@Entity(tableName = "LocalImageTable")
@Parcelize
data class LocalImage(

    @PrimaryKey
    @SerialName("id")
    val id:String= UUID.randomUUID().toString(),
    @SerialName("path")
    val path:String="",
    @SerialName("title")
    val tille:String="",
    @SerialName("desc")
    val desc:String=""
): Parcelable

class LocalImageConverter
{


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