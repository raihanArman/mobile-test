package com.raydev.mobile_test.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.raydev.mobile_test.util.ext.toast

abstract class BaseBottomSheet<B : ViewBinding>: BottomSheetDialogFragment(), BaseView {
    private var _binding: B? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = getViewBinding()
        return binding.root
    }

    abstract fun getViewBinding(): B

    override fun onMessage(message: String?) {
        toast(message)
    }

    override fun onMessage(stringResId: Int) {
        onMessage(getString(stringResId))
    }

    /**
     * check internet connection
     */
    override fun isNetworkConnect(): Boolean {
        return true //TODO(make a utilities class for this)
    }
}