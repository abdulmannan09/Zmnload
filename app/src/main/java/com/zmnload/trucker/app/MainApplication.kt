package com.zmnload.trucker.app

import android.content.Context
import android.os.StrictMode
import com.akexorcist.localizationactivity.core.LocalizationApplicationDelegate
import com.akexorcist.localizationactivity.ui.LocalizationApplication
import com.zmnload.trucker.helper.uiHelperModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import java.util.*

class MainApplication : LocalizationApplication() {

    companion object {
        private var instance: MainApplication? = MainApplication()
        fun applicationContext(): Context {
            if (instance == null) {
                instance = MainApplication()
            }
            return instance!!.applicationContext
        }

    }

    override fun getDefaultLanguage(): Locale {
        return Locale.getDefault()
    }

    private val localizationDelegate = LocalizationApplicationDelegate()

    override fun attachBaseContext(base: Context) {
        localizationDelegate.setDefaultLanguage(base, Locale.ENGLISH)
        super.attachBaseContext(localizationDelegate.attachBaseContext(base))
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
        startKoin {
            androidContext(this@MainApplication)
            androidLogger()
            modules(
                listOf(mainModule,uiHelperModule))
        }
/*
        startKoin {
            androidContext(this@ApplicationState)
            androidLogger()
            modules(
                listOf(
                    uiHelperModule,
                    preferenceModule, internetModule, permissionModule, homeRepositryModule,
                    homeViewModelModule, loadingStatusModule, addressRepositryModule,
                    addressViewModelModule, doctorRepositryModule, doctorViewModelModule,
                    calcenderViewModelModule, calenderRepositryModule, registrationRepositryModule,
                    registrationViewModelModule, patientDetailModule, patientDetailRepositry,
                    symptomViewModelModule, symptomsRepositoryModule, invoiceViewModelModule,
                    invoiceRepositryModule, feedBackViewModelModule, feedbackRepositoryModule,
                    appointmentHistoryRepositryModule, appointmetHistoryViewModelModule,
                    myStatsRepositryModule, myStatsViewModelModule, doctorManageSlotRepositryModule,
                    doctorManageSlotViewModelModule
                )
            )
        }*/
    }
}