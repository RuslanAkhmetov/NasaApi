package com.geekbrain.android.nasa_api.view.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class ViewBindingFragment<T: ViewBinding> (
    private val inflateBinding: (
            inflater: LayoutInflater, root: ViewGroup?, attachToRoot: Boolean
            ) -> T
): Fragment(){
    var myTag: String = "TAG"
        get() = field

    private var _binding: T? = null

    protected val binding: T
            get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflateBinding.invoke(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}