package com.zmnload.trucker.network


import com.zmnload.trucker.R
import com.zmnload.trucker.app.MainApplication
import java.util.*

object Configuration {
    const val DB_VERSION: Long = 2
    private val environment = Environments.Development
    @JvmStatic
    val baseUrl: ArrayList<String>
        get() {
            val links = ArrayList<String>()
            return when (environment) {
                Environments.Development -> {
                    links.add(MainApplication.applicationContext().getString(R.string.development_zmnload))
                    links.add(MainApplication.applicationContext().getString(R.string.development_zmnload_image))
                    links.add(MainApplication.applicationContext().getString(R.string.development_zmnload_deeplink))
                    links
                }
                Environments.Production -> {
                    links.add(MainApplication.applicationContext().getString(R.string.production_zmnload))
                    links.add(MainApplication.applicationContext().getString(R.string.production_zmnload_image))
                    links.add(MainApplication.applicationContext().getString(R.string.production_zmnload_deepLink))
                    links
                }
                else -> {
                    links.add("")
                    links.add("")
                    links.add("")
                    links
                }
            }
        }

    val isProduction: Boolean
        get() {
            return try {
                environment == Environments.Production
            } catch (e: Exception) {
                false
            }
        }

    val isDevelopment: Boolean
        get() {
            return try {
                environment == Environments.Development
            } catch (e: Exception) {
                false
            }
        }

    val isLocal: Boolean
        get() {
            return try {
                environment == Environments.Local
            } catch (e: Exception) {
                false
            }
        }

    val environmentName: String
        get() = environment.toString()

    enum class Environments {
        Development, Production, Local, Staging
    }
}