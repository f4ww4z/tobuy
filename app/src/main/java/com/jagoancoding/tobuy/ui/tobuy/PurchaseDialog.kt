package com.jagoancoding.tobuy.ui.tobuy

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.app.DialogFragment
import com.jagoancoding.tobuy.R
import com.jagoancoding.tobuy.ShoppingListRepo
import com.jagoancoding.tobuy.db.Purchase
import com.jagoancoding.tobuy.util.TextUtil.text
import com.jagoancoding.tobuy.util.TextUtil.validateNotEmpty

class PurchaseDialog : DialogFragment() {

    lateinit var nameTIL: TextInputLayout
    lateinit var descTIL: TextInputLayout
    lateinit var priceTIL: TextInputLayout
    lateinit var quantityTIL: TextInputLayout
    /**
     * If null, add a new item, otherwise pass in values to update item
     */
    var purchase: Purchase? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val isNewPurchase = purchase == null

        val dialogView =
                activity?.layoutInflater?.inflate(R.layout.purchase_dialog, null)?.also {
                    nameTIL = it.findViewById(R.id.til_p_name)
                    descTIL = it.findViewById(R.id.til_p_desc)
                    priceTIL = it.findViewById(R.id.til_p_price)
                    quantityTIL = it.findViewById(R.id.til_p_quantity)
                }

        if (!isNewPurchase) {
            with(purchase!!) {
                nameTIL.editText?.setText(name)
                descTIL.editText?.setText(description)
                priceTIL.editText?.setText(price.toString())
                quantityTIL.editText?.setText(quantity.toString())
            }
        }

        val mDialog: AlertDialog = AlertDialog.Builder(dialogView?.context)
                .setMessage(if (isNewPurchase)
                    getString(R.string.dialog_purchase_add)
                else
                    getString(R.string.dialog_purchase_update, purchase?.name))
                .setView(dialogView)
                .setPositiveButton(if (isNewPurchase) R.string.add else R.string.update, null)
                .setNegativeButton(android.R.string.cancel) { dialog, _ -> dialog.cancel() }
                .create()

        mDialog.setOnShowListener {
            mDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                if (nameTIL.validateNotEmpty()
                        && priceTIL.validateNotEmpty()
                        && quantityTIL.validateNotEmpty()) {

                    val p = Purchase(name = nameTIL.text(),
                            description = descTIL.text(),
                            price = priceTIL.text().toDouble(),
                            quantity = quantityTIL.text().toInt())

                    if (isNewPurchase) {
                        ShoppingListRepo.add(p)
                    } else {
                        ShoppingListRepo.update(p.apply { id = purchase?.id })
                    }

                    mDialog.dismiss()
                }
            }
        }

        return mDialog
    }
}