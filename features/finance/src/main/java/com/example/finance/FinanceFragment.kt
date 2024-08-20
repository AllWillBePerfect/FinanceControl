package com.example.finance

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.Toast
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.finance.adapter.BudgetUiDiffUtilCallBack
import com.example.finance.adapter.BudgetUiItemDecoration
import com.example.finance.adapter.item_delegate.DateItemDelegate
import com.example.finance.adapter.item_delegate.SpendingItemDelegate
import com.example.finance.adapter.models.BudgetUi
import com.example.finance.adapter.swipe.SwipeCallback
import com.example.finance.databinding.FragmentFinanceBinding
import com.example.finance.viewmodel.FinanceViewModel
import com.example.ui.BaseFragment
import com.example.ui.TextFormater
import com.example.ui.adapter.UniversalRecyclerViewAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject
import kotlin.random.Random

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

        val colorInt = requireContext().themeColor(com.google.android.material.R.attr.colorPrimary)

        val gradientNoiseBitmap = generateGradientNoise(1080, 1920, intColor = colorInt)
        val drawable = BitmapDrawable(resources, gradientNoiseBitmap)
//        requireActivity().window.decorView.background = drawable
        binding.background.background = drawable

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

    private fun generateGradientNoise(
        width: Int,
        height: Int,
        hexColor: String? = null,
        intColor: Int? = null
    ): Bitmap {
        // Разложение HEX-цвета на компоненты R, G, B

        var redBase = 0
        var greenBase = 0
        var blueBase = 0
        if (hexColor != null) {
            val colorInt = Color.parseColor(hexColor)
            redBase = Color.red(colorInt)
            greenBase = Color.green(colorInt)
            blueBase = Color.blue(colorInt)
        }
        if (intColor != null) {
            redBase = Color.red(intColor)
            greenBase = Color.green(intColor)
            blueBase = Color.blue(intColor)
        }



        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val paint = Paint()

        val random = Random(System.currentTimeMillis())

        for (y in 0 until height) {
            for (x in 0 until width) {
                // Генерация случайного шума
                val noise =
                    random.nextInt(-20, 20)  // Шум в диапазоне от -20 до 20 для каждого компонента

                // Применение шума к каждому компоненту цвета
                val red = (redBase + noise).coerceIn(0, 255)
                val green = (greenBase + noise).coerceIn(0, 255)
                val blue = (blueBase + noise).coerceIn(0, 255)

                // Формирование итогового цвета с учетом градиента (градиент можно добавить, если потребуется)
                val color = Color.rgb(red, green, blue)

                paint.color = color
                canvas.drawPoint(x.toFloat(), y.toFloat(), paint)
            }
        }

        return bitmap
    }


    private fun saveBitmapToFile(bitmap: Bitmap, filePath: String): Boolean {
        var fileOutputStream: FileOutputStream? = null
        return try {
            fileOutputStream = FileOutputStream(filePath)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        } finally {
            fileOutputStream?.close()
        }
    }

    @ColorInt
    fun Context.themeColor(@AttrRes attrRes: Int): Int = TypedValue()
        .apply { theme.resolveAttribute(attrRes, this, true) }
        .data
}