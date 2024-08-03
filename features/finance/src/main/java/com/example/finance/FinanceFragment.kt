package com.example.finance

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.finance.adapter.models.BudgetUi
import com.example.finance.adapter.BudgetUiDiffUtilCallBack
import com.example.finance.adapter.item_delegate.DateItemDelegate
import com.example.finance.adapter.item_delegate.SpendingItemDelegate
import com.example.finance.adapter.BudgetUiItemDecoration
import com.example.finance.adapter.swipe.SwipeCallback
import com.example.finance.databinding.FragmentFinanceBinding
import com.example.finance.viewmodel.FinanceViewModel
import com.example.ui.BaseFragment
import com.example.ui.TextFormater
import com.example.ui.adapter.UniversalRecyclerViewAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FinanceFragment : BaseFragment<FragmentFinanceBinding>(FragmentFinanceBinding::inflate) {

    private val viewmodel by activityViewModels<FinanceViewModel>()
    private lateinit var adapter: UniversalRecyclerViewAdapter<BudgetUi>

    @Inject
    lateinit var navigator: FinanceFragmentContract

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        setupLiveData()

        binding.addButton.setOnClickListener { navigator.launchAddDialogFragment() }
        binding.money.setOnClickListener { navigator.launchChangeMoneyDialogFragment() }

    }


    private fun setupAdapter() {
        adapter = UniversalRecyclerViewAdapter(
            delegates = listOf(
                SpendingItemDelegate(
                    onItemClickListener = {},
                    cornersDrawable = SpendingItemDelegate.CornersDirectionsContainer(
                        topCornersDrawable = ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.item_top_corners
                        ),
                        bottomCornersDrawable = ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.item_bottom_corners
                        ),
                        allCornersDrawable = ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.item_all_corners
                        ),
                        notCornersDrawable = ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.item_not_corners
                        ),

                        )
                ),
                DateItemDelegate()
            ),
            diffUtilCallback = BudgetUiDiffUtilCallBack()
        )

        ContextCompat.getDrawable(requireContext(), R.drawable.item_all_corners)

        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(BudgetUiItemDecoration())

        val itemTouchHelper =
            ItemTouchHelper(SwipeCallback(adapter, requireContext(), this::deleteRecord))

        itemTouchHelper.attachToRecyclerView(binding.recyclerView)

    }

    private fun deleteRecord(deleteId: Long) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Удалить запись")
            .setPositiveButton("Удалить") { _, _ ->
                viewmodel.deleteRecord(deleteId)
            }
            .setNeutralButton("Отмена") { _, _ ->
            }
            .show()
    }

    private fun setupLiveData() {

        viewmodel.observeSuccessMoneyTotalLiveData(viewLifecycleOwner) { moneyTotal ->
            binding.money.text = TextFormater.formatMoney(moneyTotal.value)
        }

        viewmodel.observeLoadingMoneyTotalLiveData(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.money.visibility = View.GONE
                binding.moneyTotalProgressIndicator.visibility = View.VISIBLE
            } else {
                binding.moneyTotalProgressIndicator.visibility = View.GONE
                binding.money.visibility = View.VISIBLE
            }
        }

        viewmodel.observeSuccessBudgetListLiveData(viewLifecycleOwner) { list ->
            adapter.items = list
            binding.recyclerView.smoothScrollToPosition(list.size)

        }

        viewmodel.observeErrorBudgetListLiveData(viewLifecycleOwner) { errorHolder ->
            Toast.makeText(
                requireContext(),
                "${errorHolder.error}: ${errorHolder.message}",
                Toast.LENGTH_SHORT
            ).show()
        }

        viewmodel.observeLoadingBudgetListLiveData(viewLifecycleOwner) { isLoading ->
            if (isLoading)
                binding.recyclerViewProgressIndicator.visibility = View.VISIBLE
            else
                binding.recyclerViewProgressIndicator.visibility = View.GONE
        }
    }

}