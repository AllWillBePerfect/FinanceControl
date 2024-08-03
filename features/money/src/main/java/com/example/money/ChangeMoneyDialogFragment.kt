package com.example.money

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.activityViewModels
import com.example.money.databinding.DialogFragmetChangeMoneyBinding
import com.example.money.viewmodel.ChangeMoneyViewModel
import com.example.ui.BaseDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangeMoneyDialogFragment :
    BaseDialogFragment<DialogFragmetChangeMoneyBinding>(DialogFragmetChangeMoneyBinding::inflate) {

    private val viewModel by activityViewModels<ChangeMoneyViewModel>()

    private val handler = Handler(Looper.getMainLooper())


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialogBinding.TextInputEditText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.changeMoneyTotal(dialogBinding.TextInputEditText.text.toString())
            }
            true
        }

        dialogBinding.addButton.setOnClickListener {
            viewModel.changeMoneyTotal(dialogBinding.TextInputEditText.text.toString())
        }

        viewModel.observeSuccessChangeMoneyLiveData(viewLifecycleOwner) {
            it.event?.let {
                dialog?.dismiss()
            }
        }

        viewModel.observeErrorChangeMoneyLiveData(viewLifecycleOwner) {
            it.event?.let { (exception, message) ->
                Log.d("AddDialogFragment", "$exception: $message")
                dialogBinding.TextInputLayout.error = "$message"
            }

        }

        handler.postDelayed({
            if (dialogBinding.TextInputEditText.hasFocus()) return@postDelayed
            dialogBinding.TextInputEditText.requestFocus()
            WindowCompat.getInsetsController(
                dialog!!.window!!,
                dialogBinding.TextInputEditText
            ).show(
                WindowInsetsCompat.Type.ime()
            )
        }, 300)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }
}