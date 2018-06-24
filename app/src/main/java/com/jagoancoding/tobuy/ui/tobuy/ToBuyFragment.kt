package com.jagoancoding.tobuy.ui.tobuy

import android.app.Activity
import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.jagoancoding.tobuy.R
import com.jagoancoding.tobuy.util.PurchaseUtil.sumUpPurchases
import com.jagoancoding.tobuy.util.TextUtil.format2Dp

class ToBuyFragment : Fragment() {

    companion object {
        const val SCROLL_DOWN_SENSITIVITY = 2

        fun newInstance() = ToBuyFragment()
    }

    private lateinit var vm: ToBuyViewModel

    // Views
    private var purchasesRV: RecyclerView? = null
    private var addFAB: FloatingActionButton? = null
    private var mAdapter: PurchasesAdapter? = null
    private var totalPriceTV: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.to_buy_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        vm = ViewModelProviders.of(this).get(ToBuyViewModel::class.java)

        totalPriceTV = view?.findViewById(R.id.tv_total_price)

        mAdapter = PurchasesAdapter(listOf(), vm)

        purchasesRV = view?.findViewById<RecyclerView>(R.id.rv_purchases)?.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(context)

            // Hide Floating Action Button on scroll down
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                    if (dy > SCROLL_DOWN_SENSITIVITY) {
                        addFAB?.hide()
                    } else {
                        addFAB?.show()
                    }
                    super.onScrolled(recyclerView, dx, dy)
                }
            })

            // Swipe to remove item functionality
            ItemTouchHelper(object : SwipeToRemoveCallback(context) {

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    mAdapter?.removeAt(viewHolder.adapterPosition)
                }

            }).attachToRecyclerView(this)
        }

        addFAB = view?.findViewById<FloatingActionButton>(R.id.fab_add_purchase)?.apply {
            setOnClickListener {
                // vm.addPurchase("Cake", "1 pound, rather large", 13.2, 3)
                showAddPurchaseDialog()
            }
        }

        vm.purchases?.observe(this, Observer { purchases ->
            if (purchases != null) {
                mAdapter?.setData(purchases)
                // Also sum up
                totalPriceTV?.text = purchases.sumUpPurchases().format2Dp()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.my_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?) = when (item?.itemId) {
        R.id.action_delete_all -> {
            showDeleteAllPurchasesDialog()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun showAddPurchaseDialog() {
        val dialog = PurchaseDialog()
        if (context is Activity) {
            dialog.show((context as Activity).fragmentManager, "AddPurchaseDialog")
        }
    }

    private fun showDeleteAllPurchasesDialog() {
        AlertDialog.Builder(context)
                .setTitle(R.string.dialog_delete_purchases_title)
                .setMessage(R.string.dialog_delete_purchases_message)
                .setPositiveButton(R.string.yes) { _, _ ->
                    vm.deleteAllShoppingItems()
                    mAdapter?.dataSize = -1
                }.setNegativeButton(android.R.string.cancel, null)
                .show()
    }
}
