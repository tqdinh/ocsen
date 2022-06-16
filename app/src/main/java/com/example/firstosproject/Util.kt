package com.example.firstosproject

import android.graphics.*
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.util.DisplayMetrics
import androidx.annotation.RequiresApi
import com.google.android.play.core.assetpacks.dp
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*


class Util {
    companion object {
        @Throws(WriterException::class)
        fun encodeAsBitmap(str: String?): Bitmap? {
            val writer = QRCodeWriter()
            val bitMatrix = writer.encode(str, BarcodeFormat.QR_CODE, 600, 600)
            val w = bitMatrix.width
            val h = bitMatrix.height
            val pixels = IntArray(w * h)
            for (y in 0 until h) {
                for (x in 0 until w) {
                    pixels[y * w + x] = if (bitMatrix[x, y]) Color.BLACK else Color.WHITE
                }
            }
            val bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
            bitmap.setPixels(pixels, 0, w, 0, 0, w, h)
            return bitmap
        }

        fun combineImages(srcBitmap: Bitmap, overlayed: Bitmap): Bitmap? {
            var cs: Bitmap? = null
            val matrix = Matrix().apply { postRotate(90f) }
            val background = Bitmap.createBitmap(
                srcBitmap,
                0,
                0,
                srcBitmap.width,
                srcBitmap.height,
                matrix,
                true
            )

            val width: Int = background.width
            var height = background.height

            cs = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            val comboImage = Canvas(cs)
            comboImage.drawBitmap(background, 0f, 0f, null)
            val overlay_left = width - overlayed.width
            val overlay_top = height - overlayed.height
            comboImage.drawBitmap(overlayed, overlay_left.toFloat(), overlay_top.toFloat(), null)

            return cs
        }

        @RequiresApi(Build.VERSION_CODES.N)
        fun getCurrentDateTime(): String {
            val currentTime: Date = Calendar.getInstance().getTime()
            val sdf = SimpleDateFormat("HH:mm:ss dd-MM-yyyy-MM")

            return sdf.format(currentTime).toString()
        }

        @RequiresApi(Build.VERSION_CODES.N)
        fun getJsonString(lat: String, lng: String): String {
            val jsonObject = JSONObject()
            jsonObject.put("TIME", getCurrentDateTime())
            jsonObject.put("LAT", lat)
            jsonObject.put("LNG", lng)
            return jsonObject.toString()
        }

        fun saveFileBitmapWithPath(fullPath: String, bitmap: Bitmap) {
            try {
                FileOutputStream(fullPath).use { out ->
                    bitmap?.compress(
                        Bitmap.CompressFormat.PNG,
                        100,
                        out
                    ) // bmp is your Bitmap instance
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        fun mergeQRToImageWithPath(fullPath: String, qr: String): Bitmap? {
            var ret: Bitmap? = null
            val image = File(fullPath)
            val bmOptions = BitmapFactory.Options()
            var background = BitmapFactory.decodeFile(image.getAbsolutePath(), bmOptions)
            var qrCode = encodeAsBitmap(qr)
            if (null != qrCode) {
                ret = combineImages(background, qrCode)
//                 mergedFile
//
            }
            return ret

        }
    }


}