package com.example.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

abstract class BaseFragment<T : ViewBinding>(private val inflate: (inflater: LayoutInflater, container: ViewGroup?, attachToParent: Boolean) -> T) :
    Fragment() {
    protected var _binding: T? = null
    val binding: T get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

abstract class BaseDialogFragment<T : ViewBinding>(private val inflate: (inflater: LayoutInflater, container: ViewGroup?, attachToParent: Boolean) -> T) :
    DialogFragment() {
    protected var _dialogBinding: T? = null
    val dialogBinding: T get() = _dialogBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _dialogBinding = inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.bg_all_corners))
        return dialogBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _dialogBinding = null
    }
}