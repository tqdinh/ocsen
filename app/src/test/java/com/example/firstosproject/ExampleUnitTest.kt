package com.example.firstosproject

import com.example.domain.entities.PlaceInfo
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
         var olace:PlaceInfo = PlaceInfo(lat=1.0,lon=4.0)
        var newPlace=olace.let { it->
            PlaceInfo(id=it.id,lat = 3.0,lon=4.0)
        }

        assertEquals(olace.id,newPlace.id)
        assertNotEquals(olace.lat,newPlace.lat)

    }
}