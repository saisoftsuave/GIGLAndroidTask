package com.gigl.androidtask.ApiService


import com.gigl.androidtask.constants.Constants
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor() : Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()
        val originalHttpUrl: HttpUrl = original.url
        val url: HttpUrl = originalHttpUrl.newBuilder()
            .build()

        val requestBuilder: Request.Builder = original.newBuilder()
            .addHeader(Constants.CONTENT_TYPE, Constants.CONTENT_TYPE_JSON)
            .addHeader(
                Constants.AUTHORIZATION,
                "${Constants.BEARER_TOKEN_KEY} ${""}"
            )
            .url(url)
        val request: Request = requestBuilder.build()
        val response = chain.proceed(request)
        if (response.code == 419 || response.code == 401) {
            response.close()
        }
        return response
    }
}