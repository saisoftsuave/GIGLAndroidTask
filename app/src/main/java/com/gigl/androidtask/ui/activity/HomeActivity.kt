package com.gigl.androidtask.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.gigl.androidtask.base.BaseActivity
import com.gigl.androidtask.constants.Constants
import com.gigl.androidtask.databinding.ActivityHomeBinding
import com.gigl.androidtask.ui.home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeActivity : BaseActivity() {

    private var _binding: ActivityHomeBinding? = null
    private val binding by lazy { _binding!! }
    private lateinit var mHomeFragment: HomeFragment

    companion object {
        val TAG = HomeActivity::class.java.simpleName

        fun newIntent(context: Context?, bundle: Bundle? = null) {
            val intent = Intent(context, HomeActivity::class.java)
            if (bundle != null) {
                intent.putExtra(Constants.BUNDLE, bundle)
            }
            context?.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addFragments()
    }

    private fun addFragments() {
        Handler(Looper.getMainLooper()).post {
            mHomeFragment = HomeFragment.newInstance()
            addFragment(mHomeFragment, false)
        }
    }
}