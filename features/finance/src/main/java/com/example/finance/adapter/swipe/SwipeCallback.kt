package com.example.finance.adapter.swipe

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.graphics.drawable.shapes.RoundRectShape
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.finance.adapter.item_delegate.DateItemDelegate
import com.example.finance.adapter.item_delegate.SpendingItemDelegate
import com.example.finance.adapter.models.BudgetUi
import com.example.ui.adapter.UniversalRecyclerViewAdapter


class SwipeCallback(
    private val adapter: UniversalRecyclerViewAdapter<BudgetUi>,
    private val context: Context,
    val swipeDeleteCallback: (Long) -> Unit
) : ItemTouchHelper.SimpleCallback(ItemTouchHelper.LEFT, ItemTouchHelper.LEFT) {

    private val backgroundCornerOffset = 20
    private val background: ColorDrawable = ColorDrawable(Color.RED)
    private val shapeBackground = ShapeDrawable(RectShape())
    private val backgroundLayer: ColorDrawable = ColorDrawable(Color.GRAY)
    private val icon: Drawable =
        ContextCompat.getDrawable(
            context,
            com.example.ui.R.drawable.baseline_delete_24
        )!!

    init {
        shapeBackground.paint.color = Color.RED
        val cornerSize = 16F
        val noCornerSize = 0F
        val floatArray =
            floatArrayOf(cornerSize, cornerSize, cornerSize, cornerSize, cornerSize, cornerSize, cornerSize, cornerSize)
        val rectShape = RoundRectShape(floatArray, null, null)
        shapeBackground.shape = rectShape
    }

    override fun getSwipeDirs(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val isActive = viewHolder is SpendingItemDelegate.SpendingViewHolder
        return if (isActive) return super.getSwipeDirs(recyclerView, viewHolder) else 0
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean = false

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        if (viewHolder is SpendingItemDelegate.SpendingViewHolder) {
            val item = adapter.items[viewHolder.adapterPosition]
            adapter.notifyItemChanged(viewHolder.adapterPosition)
            swipeDeleteCallback.invoke((item as BudgetUi.Spending).id)
        }
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

        if (viewHolder is DateItemDelegate.DateViewHolder) return

        val itemView = viewHolder.itemView
        val maxWidth = -itemView.width

        val iconMargin = (itemView.height - icon.intrinsicHeight) / 2
        val iconTop = itemView.top + (itemView.height - icon.intrinsicHeight) / 2
        val iconBottom = iconTop + icon.intrinsicHeight


        if (dX > 0) {
            shapeBackground.setBounds(
                itemView.left,
                itemView.top,
                itemView.left + dX.toInt(),
                itemView.bottom
            )
        } else if (dX >= maxWidth) {
            icon.setBounds(itemView.left - 100, itemView.top, itemView.right, itemView.bottom)

            val iconLeft = itemView.right - iconMargin - icon.intrinsicWidth
            val iconRight = itemView.right - iconMargin
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)

            shapeBackground.setBounds(
                itemView.right + dX.toInt() - backgroundCornerOffset,
                itemView.top,
                itemView.right,
                itemView.bottom
            )

        } else {
            shapeBackground.setBounds(0, 0, 0, 0)
            icon.setBounds(0, 0, 0, 0)
        }

        if (dX == 0F) {
            shapeBackground.setBounds(0, 0, 0, 0)
            icon.setBounds(0, 0, 0, 0)
        }

        shapeBackground.draw(c)
        icon.draw(c)
    }
}