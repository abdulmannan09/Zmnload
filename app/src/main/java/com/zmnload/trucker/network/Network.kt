package com.zmnload.trucker.network


import com.zmnload.trucker.app.MainApplication
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.UnsupportedEncodingException
import java.util.*
import java.util.concurrent.TimeUnit

class Network() {
    private var networkClient: Retrofit? = null
    private var services: ApiInterface? = null

    init {
        var credentials: String = ""
        try {
            credentials = Credentials.basic("allodoc", "allodoc1234")
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
//        val cacheDir = File(MainApplication().cacheDir, UUID.randomUUID().toString())
//        val cache = Cache(cacheDir, 10 * 1024 * 1024)
        val gson = GsonBuilder()
            .setLenient()
            .create()
        val httpClient = OkHttpClient.Builder()
        httpClient.retryOnConnectionFailure(true)
//        httpClient.cache(cache)
        httpClient.connectTimeout(30, TimeUnit.SECONDS)
        httpClient.readTimeout(60, TimeUnit.SECONDS)
        httpClient.writeTimeout(60, TimeUnit.SECONDS)
        httpClient.addInterceptor(getLoggingIntercept())
        httpClient.addInterceptor { chain: Interceptor.Chain ->
   /*         if (User.retrieveUser()!=null && User.retrieveUser()?.api_token?.isNotEmpty()!!) {
                val original = chain.request()
                val request = original.newBuilder()
                    .header("Authorization", "Bearer ${User.retrieveUser()?.api_token}")
                    .method(original.method(), original.body())
                    .build()
                return@addInterceptor chain.proceed(request)
            } else {*/
            val original = chain.request()
            val request = original.newBuilder()
                    .method(original.method, original.body)
                    .addHeader("Authorization", credentials)
                    .build()
                return@addInterceptor chain.proceed(request)
//            }
        }
        networkClient = Retrofit.Builder()
            .baseUrl(Configuration.baseUrl[0])
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(httpClient.build())
            .build()
        services = networkClient!!.create(ApiInterface::class.java)
    }


    private fun getLoggingIntercept(): Interceptor {

        return run {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }
    }
    }

    fun apis(): ApiInterface? {
        return getApiInterface()
    }


    fun getApiInterface(): ApiInterface? {
        return services
    }

    fun getNetworkClient(): Retrofit? {

        return networkClient
    }


}