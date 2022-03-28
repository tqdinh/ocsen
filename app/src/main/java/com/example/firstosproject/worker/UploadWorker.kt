package com.example.firstosproject.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.lang.Exception

class UploadWorker(appContext: Context, workerParams: WorkerParameters):Worker(appContext,workerParams)
{
    override fun doWork(): Result {
        try {

//            val imageUriInput =  inputData.getString(Constraints.KEY_IMAGE_URI)
//            respone
            return Result.success()
        }
        catch (e:Exception){
            return Result.failure()
        }
    }
//    fun upload(imageUri: String): Response {
//
//    }

}