package com.example.data.local.dao

import androidx.room.*
import com.example.data.local.entities.LocalImage
import com.example.data.local.entities.LocalPlace


@Dao
abstract class LocalPlaceDao {
    @Query("SELECT * FROM LocalPlaceTable")
    abstract fun getListLocalPlace(): List<LocalPlace>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertLocalPlace(place: LocalPlace)

    @Query("DELETE FROM LocalPlaceTable WHERE id=:id")
    abstract fun deletePlace(id: String)


}

@Dao
abstract class LocalImageDao {
    @Query("SELECT * FROM LocalImageTable")
    abstract fun getListLocalImage(): Array<LocalImage>

    @Insert
    abstract fun insertImage(localImage: LocalImage)

    @Query("SELECT * FROM LocalImageTable WHERE place_id=:placeId")
    abstract fun getImagesOnPlace(placeId: String): List<LocalImage>

    @Query("DELETE FROM LocalImageTable WHERE id=:imgId")
    abstract fun deleteImage(imgId: String)

}
