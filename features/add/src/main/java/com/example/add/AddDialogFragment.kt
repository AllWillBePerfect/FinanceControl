package com.example.add

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.postDelayed
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.add.databinding.DialogFragmentAddBinding
import com.example.add.viewmodel.AddDialogViewModel
import com.example.models.ExpensesType
import com.example.ui.BaseDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddDialogFragment :
    BaseDialogFragment<DialogFragmentAddBinding>(DialogFragmentAddBinding::inflate) {

    private val viewModel by activityViewModels<AddDialogViewModel>()

    private val handler = Handler(Looper.getMainLooper())
//    private lateinit var viewModel: AddDialogViewModel

//    private var _dialogBinding: DialogFragmentAddBinding? = null
//    private val dialogBinding get() = _dialogBinding!!


//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        _dialogBinding = DialogFragmentAddBinding.inflate(layoutInflater, null, false)
//
//        dialogBinding.TextInputEditText.setOnEditorActionListener { v, actionId, event ->
//            if (actionId == EditorInfo.IME_ACTION_DONE) {
//                Toast.makeText(requireContext(), "Click", Toast.LENGTH_SHORT).show()
//
//            }
//            true
//        }
//
//        viewModel.observeErrorAddRecordLiveData(viewLifecycleOwner) { (exception, message) ->
//            Log.d("AddDialogFragment", "$exception: $message")
//            Toast.makeText(requireContext(), "$exception: $message", Toast.LENGTH_SHORT).show()
//        }
//
//        val dialog = MaterialAlertDialogBuilder(requireContext())
//            .setView(dialogBinding.root)
//            .setTitle("Добавить")
//            .setCancelable(false)
//            .setNeutralButton("Neutral") { _, _ ->
//                return@setNeutralButton
//            }
//            .setPositiveButton("Add") { _, _ ->
//                viewModel.createRecord(dialogBinding.TextInputEditText.text.toString())
//            }
//        return dialog.create()
//    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialogBinding.toggleGroupButtons.setSingleSelection(true)
        dialogBinding.toggleGroupButtons.isSelectionRequired = true
        val id = when(viewModel.getType()) {
            ExpensesType.SPENDING -> R.id.first_toggle_button
            ExpensesType.INCOME -> R.id.second_toggle_button
        }
        dialogBinding.toggleGroupButtons.check(id)

        dialogBinding.toggleGroupButtons.addOnButtonCheckedListener { group, checkedId, isChecked ->

            if (isChecked)
                when (checkedId) {
                    R.id.first_toggle_button -> {
                        viewModel.changeType(ExpensesType.SPENDING)

                    }

                    R.id.second_toggle_button -> {
                        viewModel.changeType(ExpensesType.INCOME)
                    }
                }
        }

        dialogBinding.TextInputEditText.doAfterTextChanged {
            dialogBinding.TextInputLayout.error = null
        }

        dialogBinding.TextInputEditText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.createRecord(dialogBinding.TextInputEditText.text.toString())
            }
            true
        }

        dialogBinding.addButton.setOnClickListener {
            viewModel.createRecord(dialogBinding.TextInputEditText.text.toString())
        }

        viewModel.observeSuccessAddRecordLiveData(viewLifecycleOwner) {event ->
            event.event?.let {
                dialog?.dismiss()
            }
        }

        viewModel.observeErrorAddRecordLiveData(viewLifecycleOwner) { event ->
            event.event?.let { (exception, message) ->
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