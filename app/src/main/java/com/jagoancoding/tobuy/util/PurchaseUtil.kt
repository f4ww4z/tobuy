package com.jagoancoding.tobuy.util

import com.jagoancoding.tobuy.db.Purchase

object PurchaseUtil {

    fun List<Purchase>.sumUpPurchases() = filter { it.included }.map { it.totalPrice }.sum()
}