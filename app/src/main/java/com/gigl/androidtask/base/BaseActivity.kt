package com.gigl.androidtask.base


import android.app.Dialog
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.gigl.androidtask.R
import java.lang.ref.WeakReference

open class BaseActivity : AppCompatActivity() {

    @JvmField
    protected val TAG: String = javaClass.simpleName


    //loader
    private val mProgressDialog: Dialog by lazy { DialogUtils.loader(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    val isTab: Boolean
        get() {
            val xlarge =
                resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK == 4
            val large =
                resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK == Configuration.SCREENLAYOUT_SIZE_LARGE
            return xlarge || large
        }

    fun addFragment(
        fragment: Fragment,
        isAddToBackStack: Boolean = false,
    ) {
        if (!supportFragmentManager.isDestroyed) {
            val transaction = supportFragmentManager.beginTransaction()

            transaction.add(R.id.frame_layout, fragment, fragment.javaClass.name)
            if (isAddToBackStack) transaction.addToBackStack(fragment.javaClass.name)
            transaction.commit()
        }
    }



    fun addAndHideFragment(
        fragment:  BaseFragment<*, *>,
        isAddToBackStack: Boolean = false
    ) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.frame_layout, fragment, fragment.javaClass.name)
        transaction.hide(fragment)
        if (isAddToBackStack)
            transaction.addToBackStack(fragment.javaClass.name)
        transaction.commit()
    }



    fun addFragmentWithBackStack(fragment: Fragment) {
        try {
            if (!supportFragmentManager.isDestroyed) {
                val fragmentTransaction = supportFragmentManager.beginTransaction()

                fragmentTransaction.replace(
                    R.id.frame_layout, fragment, fragment.javaClass.name
                ).addToBackStack(fragment.javaClass.name).commit()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun clearFragmentsStack() {
        if (!supportFragmentManager.isDestroyed) {
            val fragmentManager = supportFragmentManager
            val count = fragmentManager.backStackEntryCount
            for (i in 0 until count) {
                fragmentManager.popBackStack()
            }
        }
    }

    fun getCurrentVisibleFragment(): BaseFragment<*, *>? {
        var visibleFragment: BaseFragment<*, *>? = null
        val allFragments = supportFragmentManager.fragments

        for (fragment in allFragments) {
            if (fragment.isVisible) {
                visibleFragment = fragment as BaseFragment<*, *>
                break
            }
        }
        return visibleFragment
    }

    fun getCurrentFragment(): Fragment? {
        return supportFragmentManager.findFragmentById(R.id.frame_layout)
    }


    fun showProgress() {
        if(!mProgressDialog.isShowing)
            mProgressDialog.show()
    }

    fun hideProgress() {
        mProgressDialog.dismiss()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}

object DialogUtils {

    fun loader(context: Context, isCancel: Boolean = false): Dialog {
        val weakRef = WeakReference(context)
        val dialog = Dialog(weakRef.get()!!, R.style.MaterialDialog)
        dialog.setContentView(R.layout.layout_progress_bar)
        dialog.setCancelable(isCancel)
        dialog.setCanceledOnTouchOutside(isCancel)
        return dialog
    }
}
