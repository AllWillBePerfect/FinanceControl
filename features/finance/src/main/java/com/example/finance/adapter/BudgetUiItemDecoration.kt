package com.example.finance.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.finance.R
import com.example.ui.dpToPx

class BudgetUiItemDecoration : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)

        if (view.id == R.id.dateTextView)
            outRect.top = 16.dpToPx()

        if (view.id == R.id.item_spending)
            outRect.bottom = 2.dpToPx()

        if (position == 0)
            outRect.bottom = 64.dpToPx()

    }
}