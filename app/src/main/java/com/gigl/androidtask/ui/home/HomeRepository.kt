package com.gigl.androidtask.ui.home

import ApiResponse
import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.gigl.androidtask.ApiService.ApiService
import com.gigl.androidtask.models.ImageResponse
import com.gigl.androidtask.models.StoredDetails
import com.gigl.androidtask.room.ImageDetailsDao
import kotlinx.coroutines.CoroutineScope
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val apiService: ApiService, private val imageDetailsDao: ImageDetailsDao
) {
    fun getData(): LiveData<ApiResponse<List<ImageResponse>?>> {
        val endResponse = MutableLiveData<ApiResponse<List<ImageResponse>?>>()

        val retrofitData = apiService.getData()
        retrofitData.enqueue(object : Callback<List<ImageResponse>?> {
            override fun onResponse(
                call: Call<List<ImageResponse>?>,
                response: Response<List<ImageResponse>?>
            ) {
                if (response.isSuccessful) {
                    Timber.d("LoginApiSuccess")
                    endResponse.postValue(ApiResponse.Success(response.body()))
                } else {
                    Timber.e("LoginApiError: ${response.code()}")
                    endResponse.postValue(ApiResponse.Error("Error occurred"))
                }
            }

            override fun onFailure(call: Call<List<ImageResponse>?>, t: Throwable) {
                Timber.e("NetworkError: ${t.message}")
                endResponse.postValue(ApiResponse.Error("Network error"))
            }
        })

        return endResponse
    }



    fun getStoredDetailsList(): MutableList<StoredDetails>  {
        return imageDetailsDao.getAllImageDetails()
    }

    suspend fun insertDetailList(storedDetails: MutableList<StoredDetails>) {
        return imageDetailsDao.insertAll(storedDetails)
    }


}