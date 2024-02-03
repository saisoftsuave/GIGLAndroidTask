package com.gigl.androidtask.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VBinding : ViewBinding, VM : BaseViewModel> : Fragment() {
    abstract val viewModel: VM

    protected abstract fun getViewBinding(): VBinding
    private var _binding: VBinding? = null

    protected val binding by lazy { _binding!! }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = getViewBinding()
        return binding.root
    }


    fun getParentActivity(): BaseActivity {
        return activity as BaseActivity
    }

    fun getBaseActivity(): BaseActivity {
        return activity as BaseActivity
    }

    fun addFragment(
        fragment: Fragment, isAddToBackStack: Boolean
    ) {
        try {
            getParentActivity().addFragment(fragment, isAddToBackStack)
        } catch (exc: Exception) {
            exc.printStackTrace()
        }
    }

    fun backToPreviousFragment() {
        if (requireActivity().supportFragmentManager.backStackEntryCount > 0) {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }


    fun addFragmentWithBackStack(fragment: Fragment) {
        getParentActivity().addFragmentWithBackStack(fragment)
    }

    fun clearStack() {
        getParentActivity().clearFragmentsStack()
    }

    fun removeLastFragment() {
        requireActivity().supportFragmentManager.popBackStack()
    }

    fun popCurrentFragment() {
        requireActivity().supportFragmentManager.popBackStackImmediate()
    }


}
