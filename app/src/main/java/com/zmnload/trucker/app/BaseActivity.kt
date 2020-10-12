package com.zmnload.trucker.app

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.Window
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.zmnload.trucker.respository.DataRepository
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins
import java.io.IOException
import java.net.SocketException

open class BaseActivity : LocalizationActivity() {


    private val myReceiverSetLanguage: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val extras = intent.extras
            val language = extras?.getString("language")
            if (language != null)
                setLanguage(language)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        initRxErrorHandler()
        DataRepository.activity = this
        registerReceiver(
            myReceiverSetLanguage,
            IntentFilter("LanguageActivity.INTENT_FILTER_LANGUAGE")
        )

    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(myReceiverSetLanguage)
    }

    fun View.preventDoubleClick() {
        isClickable = false
        Handler(Looper.getMainLooper()).postDelayed({ isClickable = true }, 1000L)
    }

    private fun initRxErrorHandler() {
        RxJavaPlugins.setErrorHandler { throwable ->
            if (throwable is UndeliverableException) {
                throwable.cause?.let {
                    Thread.currentThread().uncaughtExceptionHandler?.uncaughtException(
                        Thread.currentThread(),
                        it
                    )
                    return@setErrorHandler
                }
            }
            if (throwable is IOException || throwable is SocketException) {
                // fine, irrelevant network problem or API that throws on cancellation
                return@setErrorHandler
            }
            if (throwable is InterruptedException) {
                // fine, some blocking code was interrupted by a dispose call
                return@setErrorHandler
            }
            if (throwable is NullPointerException || throwable is IllegalArgumentException) {
                // that's likely a bug in the application
                Thread.currentThread().uncaughtExceptionHandler?.uncaughtException(
                    Thread.currentThread(),
                    throwable
                )
                return@setErrorHandler
            }
            if (throwable is IllegalStateException) {
                // that's a bug in RxJava or in a custom operator
                Thread.currentThread().uncaughtExceptionHandler?.uncaughtException(
                    Thread.currentThread(),
                    throwable
                )
                return@setErrorHandler
            }
            Log.w("Undeliverable exception", throwable)
        }
    }

}