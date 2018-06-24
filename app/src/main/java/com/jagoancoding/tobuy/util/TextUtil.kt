package com.jagoancoding.tobuy.util

import android.content.Context
import android.support.design.widget.TextInputLayout
import com.jagoancoding.tobuy.R

object TextUtil {

    fun Int.formatToQuantity(context: Context): String =
            context.getString(R.string.purchase_quantity, this)

    fun Double.format2Dp(): String = "%.2f".format(this)

    fun Double.formatToPrice(context: Context): String =
            context.getString(R.string.purchase_price, "%.2f".format(this))

    fun Double.formatToTotalPrice(context: Context): String =
            context.getString(R.string.purchase_total_price, "%.2f".format(this))

    fun TextInputLayout.text(): String = editText?.text.toString()

    fun TextInputLayout.validateNotEmpty(): Boolean =
            (editText != null && editText!!.text.isNotEmpty()).also {
                error = if (it) null else context.getString(R.string.empty_edittext)
            }
}