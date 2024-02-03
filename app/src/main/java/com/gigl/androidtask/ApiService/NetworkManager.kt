package com.gigl.androidtask.ApiService

import com.gigl.androidtask.constants.Constants
import com.gigl.androidtask.constants.Constants.BASE_URL
import com.gigl.androidtask.BuildConfig
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkManager {
    inline fun <reified T> apiService(): T = retrofitInstance.create(T::class.java)

    private var OAUTH_ACCESS_TOKEN: String? = null

    val retrofitInstance: Retrofit by lazy {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(client).build()
    }

    val INTERCEPTOR = Interceptor { chain ->

        if (OAUTH_ACCESS_TOKEN.isNullOrBlank()) {
            OAUTH_ACCESS_TOKEN = "app base auth token"
        }
        val original: Request = chain.request()
        val originalHttpUrl: HttpUrl = original.url
        val url: HttpUrl =
            originalHttpUrl.newBuilder().addQueryParameter(Constants.TOKEN, OAUTH_ACCESS_TOKEN)
                .build()
        val requestBuilder: Request.Builder = original.newBuilder()
            .addHeader(Constants.CONTENT_TYPE, Constants.CONTENT_TYPE_JSON).url(url)
        val request: Request = requestBuilder.build()
        chain.proceed(request)
    }

    private val client: OkHttpClient
        get() {
            val client: OkHttpClient.Builder =
                OkHttpClient.Builder().connectTimeout(Constants.API_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(Constants.API_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(Constants.API_TIMEOUT, TimeUnit.SECONDS)
            client.addInterceptor(getLogInterceptor())
            return client.build()
        }

    fun getLogInterceptor(): Interceptor {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        )
        return logging
    }
}

