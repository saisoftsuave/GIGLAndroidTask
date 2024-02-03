package com.gigl.androidtask.ui.home

import ApiResponse
import android.os.Bundle
import android.view.View
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gigl.androidtask.base.BaseFragment
import com.gigl.androidtask.databinding.HomeFragmentBinding
import com.gigl.androidtask.models.StoredDetails
import com.gigl.androidtask.ui.adapter.VerticalVideoAdapter
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeFragmentBinding, HomeViewModel>() {

    override val viewModel: HomeViewModel by viewModels()

    private lateinit var fullVideoAdapter: VerticalVideoAdapter
    override fun getViewBinding(): HomeFragmentBinding {
        return HomeFragmentBinding.inflate(layoutInflater)
    }

    companion object {
        fun newInstance(): HomeFragment {
            val args = Bundle()
            val fragment = HomeFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateUI()
        initAdapter()
        manageListeners()
        getResponseObserver()
    }

    private fun manageListeners() {

    }

    private fun initAdapter() {
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.longVideoRecyclerView.layoutManager = layoutManager
        fullVideoAdapter = VerticalVideoAdapter()
        binding.longVideoRecyclerView.adapter = fullVideoAdapter
    }


    private fun updateUI() {
        viewModel.fetchData()
        getParentActivity().showProgress()
    }


    private fun getResponseObserver() {
        viewModel.userDetails.observe(viewLifecycleOwner) { response ->
            Timber.tag("response").d(response.toString())
            when (response) {
                is ApiResponse.Success -> {

                    getParentActivity().hideProgress()
                    val responseData = response.data
                    val detailList: MutableList<StoredDetails> = mutableListOf()
                    val listOfLists = mutableListOf<Any>()
                    responseData?.forEach { responseItem ->
                        val storedDetail = StoredDetails(
                            id = responseItem.id,
                            profileImage = responseItem.user.profile_image.small ?: "",
                            thumbnailUrl = responseItem.urls.regular ?: "",
                            description = responseItem.alt_description ?: "",
                            shortDescription = responseItem.alt_description ?: ""
                        )
                        detailList.add(storedDetail)
                    }
                    listOfLists.addAll(detailList)
                    listOfLists.add(3, detailList)
                    listOfLists.add(6, detailList)
                    listOfLists.add(9, detailList)
                    fullVideoAdapter.setDetails(listOfLists)
                }

                is ApiResponse.Loading -> {
                    getParentActivity().showProgress()
                }

                is ApiResponse.Error -> {

                    getParentActivity().hideProgress()
                    Timber.tag("Error").d(response.toString())
                }

                else -> {

                    getParentActivity().hideProgress()
                }
            }

        }

    }

}