package com.jagoancoding.tobuy.ui.tobuy

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import com.jagoancoding.tobuy.R
import com.jagoancoding.tobuy.ShoppingListRepo
import com.jagoancoding.tobuy.db.Purchase
import com.jagoancoding.tobuy.util.TextUtil.formatToPrice
import com.jagoancoding.tobuy.util.TextUtil.formatToQuantity
import com.jagoancoding.tobuy.util.TextUtil.formatToTotalPrice

class PurchasesAdapter(private var data: List<Purchase>, private val viewModel: ToBuyViewModel)
    : RecyclerView.Adapter<PurchasesAdapter.PurchaseViewHolder>() {

    var dataSize = -1

    class PurchaseViewHolder(root: View) : RecyclerView.ViewHolder(root) {
        val nameTV: TextView = root.findViewById(R.id.tv_p_name)
        val descTV: TextView = root.findViewById(R.id.tv_p_desc)
        val priceTV: TextView = root.findViewById(R.id.tv_p_price)
        val quantityTV: TextView = root.findViewById(R.id.tv_p_quantity)
        val totalPriceTV: TextView = root.findViewById(R.id.tv_p_totalprice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            LayoutInflater.from(parent.context)
                    .inflate(R.layout.purchase_item, parent, false)
                    .run {
                        PurchaseViewHolder(this)
                    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: PurchaseViewHolder, position: Int) {
        val p = data[position]
        with(holder) {
            itemView.animateOnInsert(position)

            // Change background depending on the @included variable
            itemView.setBackgroundResource(
                    if (p.included) R.color.purchaseBgIncluded else R.color.purchaseBg)

            nameTV.text = p.name
            descTV.text = p.description
            priceTV.text = p.price.formatToPrice(itemView.context)
            quantityTV.text = p.quantity.formatToQuantity(itemView.context)
            totalPriceTV.text = p.totalPrice.formatToTotalPrice(itemView.context)
            itemView.setOnClickListener {
                //TODO: Display Snackbar (and show price per item)
                ShoppingListRepo.update(p.apply { included = !included })
            }
            itemView.setOnLongClickListener {
                // On long click, update item
                val updatePurchaseDialog = PurchaseDialog().apply {
                    purchase = p
                }
                if (itemView.context is Activity) {
                    updatePurchaseDialog.show((itemView.context as Activity).fragmentManager,
                            "UpdatePurchaseDialogFragment")
                }
                true
            }
        }
    }

    fun setData(new: List<Purchase>) {
        data = new
        notifyDataSetChanged()
    }

    fun removeAt(position: Int) {
        ShoppingListRepo.delete(data[position])
    }

    private fun View.animateOnInsert(position: Int) {
        if (position > dataSize) {
            //TODO: Not able to animate newly inserted items after deletion
            val animation = AnimationUtils.loadAnimation(context, android.R.anim.fade_in)
            startAnimation(animation)
            dataSize = position
        }
    }
}