package com.example.ui

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.core.content.ContextCompat
import java.text.DecimalFormat

object TextFormater {

    val format = DecimalFormat("0.#")

    fun formatMoney(amount: Double): String {
//        val cropAmount = format.format(amount).toDouble()
        return String.format("%,#.2f", amount) + " ₽"
    }

    fun formatMoneySpending(amount: Double): CharSequence {
//        val cropAmount = format.format(amount).toDouble()
        return "-" + String.format("%,#.2f", amount) + " ₽"
    }

    fun formatMoneyIncome(amount: Double): CharSequence {
//        val cropAmount = format.format(amount).toDouble()
        val string = SpannableString("+" +String.format("%,#.2f", amount) + " ₽")
        val foregroundSpan = ForegroundColorSpan(
            Color.parseColor("#00b300")
        )
        string.setSpan(foregroundSpan, 0, string.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        return string
    }
}