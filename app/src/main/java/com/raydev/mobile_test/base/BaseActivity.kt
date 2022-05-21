package com.raydev.mobile_test.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.raydev.mobile_test.util.ext.toast

abstract class BaseActivity<B : ViewBinding>(
): AppCompatActivity(), BaseView {
    private var _binding: B? = null
    val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = getViewBinding()
        setContentView(binding.root)
    }

    abstract fun getViewBinding(): B

    override fun onMessage(message: String?) {
        toast(message)
    }

    override fun onMessage(stringResId: Int) {
        onMessage(getString(stringResId))
    }

    abstract fun loadingData()

    abstract fun dataIsEmpty()

    abstract fun dataIsExist()

    /**
     * check internet connection
     */
    override fun isNetworkConnect(): Boolean {
        return true //TODO(make a utilities class for this)
    }
}

