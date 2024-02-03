package com.gigl.androidtask.ui.home

import ApiResponse
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gigl.androidtask.ApiService.ApiService
import com.gigl.androidtask.models.ImageResponse
import com.gigl.androidtask.models.StoredDetails
import com.gigl.androidtask.room.ImageDetailsDao
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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
                    endResponse.postValue(ApiResponse.Success(response.body()))
                } else {
                    endResponse.postValue(ApiResponse.Error("Error occurred"))
                }
            }

            override fun onFailure(call: Call<List<ImageResponse>?>, t: Throwable) {
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