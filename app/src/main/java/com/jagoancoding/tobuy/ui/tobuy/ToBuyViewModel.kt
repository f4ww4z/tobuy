package com.jagoancoding.tobuy.ui.tobuy

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.jagoancoding.tobuy.ShoppingListRepo
import com.jagoancoding.tobuy.db.Purchase

class ToBuyViewModel : ViewModel() {

    val purchases: LiveData<List<Purchase>>?
        get() = ShoppingListRepo.allPurchases

    fun deleteAllShoppingItems() {
        ShoppingListRepo.deleteAllPurchases()
    }

    fun addPurchase(name: String,
                    description: String,
                    price: Double,
                    quantity: Int,
                    included: Boolean = true) {
        ShoppingListRepo.add(Purchase(
                name = name,
                description = description,
                price = price,
                quantity = quantity,
                included = included))
    }

    fun updatePurchase(name: String,
                    description: String,
                    price: Double,
                    quantity: Int,
                    included: Boolean = true) {
        ShoppingListRepo.update(Purchase(
                name = name,
                description = description,
                price = price,
                quantity = quantity,
                included = included))
    }

    fun getPurchase(id: Long) = ShoppingListRepo.getPurchaseById(id)

    fun getPurchase(name: String) = ShoppingListRepo.getPurchaseByName(name)
}