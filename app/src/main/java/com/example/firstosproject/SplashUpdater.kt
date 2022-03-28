package com.example.firstosproject

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.example.domain.repositories.local.LocalDataSource
//import com.example.firstosproject.DI.DaggerMainComponent
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.ActivityResult
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.AppUpdateType.IMMEDIATE
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.w3c.dom.Text
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.*
import javax.inject.Inject

class SplashUpdater : AppCompatActivity() {

    val TAG = "CHK_UPDATE"
    val MY_REQUEST_CODE = 1024

    lateinit var ll_text_view: LinearLayout

    @Inject
    lateinit var local: LocalDataSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_updater)

        ll_text_view = findViewById(R.id.ll_text_view)


        // flatMapConcat()
        // flatMapLastest()
        //flatMapMerge()
        combine()
    }


    private fun flatMapConcat() {

        runBlocking {
            fun requestFlow(i: Int): Flow<String> = flow {
                emit("$i: First")
                emit("$i: delay")
                delay(500)
                emit("$i: Second")
            }

            (1..2).asFlow().onEach { delay(100) }
                .flatMapConcat { requestFlow(it) }
                .flowOn(Dispatchers.IO)
                .collect { value ->

                    var tv = TextView(this@SplashUpdater)
                    tv.setText(value)
                    ll_text_view.addView(tv)
                }
        }
    }

    private fun flatMapLastest() {
        runBlocking {
            fun requestFlow(i: Int): Flow<String> = flow {
                emit("$i: First")
                emit("$i: delay")
                delay(500)
                emit("$i: Second")
            }

            (1..3).asFlow().onEach { delay(100) }
                .flatMapLatest { requestFlow(it) }
                .collect { value ->

                    var tv: TextView = TextView(this@SplashUpdater)
                    tv.setText(value)

                    ll_text_view.addView(tv)


                }
        }
    }


    private fun combine()
    {
        runBlocking {
            val flow1 = (1..2).asFlow()
            val flow2 = listOf<Int>(3).asFlow()
            flow1.combine(flow2) { d1, d2 ->
                Pair(d1, d2)
            }.collect {
                var tv: TextView = TextView(this@SplashUpdater)
                tv.setText("[${it.first } -- ${it.second}]")

                ll_text_view.addView(tv)
            }
        }

    }

    private fun flatMapMerge() {
        runBlocking {
            fun requestFlow(i: Int): Flow<String> = flow {
                emit("$i: First")
                emit("$i: delay")
                delay(500)
                emit("$i: Second")
            }

            (1..3).asFlow().onEach { delay((it * 100).toLong()) }
                .flatMapMerge { requestFlow(it) }
                .flowOn(Dispatchers.IO)
                .collect { value ->

                    var threadValue="${Thread.currentThread().name} got value ${value}"
                    var tv: TextView = TextView(this@SplashUpdater)
                    tv.setText(threadValue)
                    ll_text_view.addView(tv)

                }
        }
    }


    fun convertAmountToHumanReadableString(amount: Double): String {

        val formatter = NumberFormat.getInstance(Locale.US) as DecimalFormat
        val symbols: DecimalFormatSymbols = formatter.decimalFormatSymbols

        symbols.setGroupingSeparator(',')
        formatter.decimalFormatSymbols = symbols
        //println()
        var k = formatter.format(amount)
        Log.d("kkkkk", k)
        return k

    }





    override fun onResume() {
        super.onResume()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == MY_REQUEST_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    Log.d(TAG, "" + "Result Ok")
                    //  handle user's approval }
                }
                Activity.RESULT_CANCELED -> {
                    {
//if you want to request the update again just call checkUpdate()
                    }
                    Log.d(TAG, "" + "Result Cancelled")
                    //  handle user's rejection  }
                }
                ActivityResult.RESULT_IN_APP_UPDATE_FAILED -> {
                    //if you want to request the update again just call checkUpdate()
                    Log.d(TAG, "" + "Update Failure")
                    //  handle update failure
                }
            }
        }
    }
}