package com.gigl.androidtask.ui.home

import ApiResponse
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.gigl.androidtask.base.BaseViewModel
import com.gigl.androidtask.models.ImageResponse
import com.gigl.androidtask.models.StoredDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepository) :
    BaseViewModel() {



    private val _userDetails = MediatorLiveData<ApiResponse<List<ImageResponse>?>>()
    val userDetails: LiveData<ApiResponse<List<ImageResponse>?>> get() = _userDetails

    fun fetchData() {
        viewModelScope.launch {
            val data = repository.getData()
            _userDetails.addSource(data) {
                _userDetails.value = it
            }
        }
    }

    fun getChildDetailsList(): MutableList<StoredDetails> {
        return repository.getStoredDetailsList()
    }


    suspend fun insertDetailList(details: MutableList<StoredDetails>) {
        return repository.insertDetailList(details)
    }

}
