package com.gigl.androidtask.ApiService

import com.gigl.androidtask.models.ImageResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("/photos/?client_id=VT4-pcdg3s3PnYg9L4SD-3TB47IzVsAsEwjBt4OYiKQ&per_page=20")
    fun getData(): Call<List<ImageResponse>?>

}