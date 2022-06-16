package com.example.firstosproject.reviewImage

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.example.firstosproject.Util
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ReviewViewmodel @Inject constructor():ViewModel(){
    private val _bitmapConverted = MutableStateFlow<Bitmap?>(null)
    val bitmapConverted = _bitmapConverted.asStateFlow()

    fun mergeBitmapWithRQ(fullPath:String,qrString:String)
    {
        val mergeBitmap = Util.mergeQRToImageWithPath(
            fullPath,
            qrString
        )
        _bitmapConverted.value = mergeBitmap
    }

}