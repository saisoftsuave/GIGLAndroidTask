package com.gigl.androidtask.ui.activity

import android.os.Bundle
import android.os.Handler
import com.gigl.androidtask.base.BaseActivity
import com.gigl.androidtask.constants.Constants.SPLASH_DURATION
import com.gigl.androidtask.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class MainActivity : BaseActivity() {


    private var _binding: ActivityMainBinding? = null
    private val binding by lazy { _binding!! }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        foreground = true

    }

    private fun navigateToNext() {
        Handler().postDelayed({
            handleNavigation()
            finish()
        }, SPLASH_DURATION)
    }

    private fun handleNavigation() {
        HomeActivity.newIntent(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        foreground = false
        background = false
    }

    override fun onResume() {
        super.onResume()
        navigateToNext()
        foreground = true
        background = false
    }

    override fun onPause() {
        super.onPause()
        background = true
    }

    override fun onStop() {
        super.onStop()
        pushforeground = false
    }

    companion object {
        var foreground: Boolean = false
        var background: Boolean = false
        var pushforeground: Boolean = false
    }

}
