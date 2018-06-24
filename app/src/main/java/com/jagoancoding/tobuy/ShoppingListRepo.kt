package com.jagoancoding.tobuy

import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import com.jagoancoding.tobuy.db.AppDatabase
import com.jagoancoding.tobuy.db.Purchase
import com.jagoancoding.tobuy.db.PurchaseDao

object ShoppingListRepo {

    // SQLite Database backend
    private var db: AppDatabase? = null
    var hasInitialized = false

    private lateinit var purchaseDao: PurchaseDao

    var allPurchases: LiveData<List<Purchase>>? = null

    fun init(application: Application) {
        db = AppDatabase.getInstance(application.applicationContext)

        if (db != null) {
            hasInitialized = true
            purchaseDao = db!!.purchaseDao()

            allPurchases = purchaseDao.allPurchases
        }
    }

    fun getPurchaseById(id: Long): LiveData<Purchase> = purchaseDao.loadPurchaseById(id)

    fun getPurchaseByName(name: String): LiveData<Purchase> = purchaseDao.loadPurchaseByName(name)

    fun add(purchase: Purchase) {
        if (hasInitialized) InsertPurchaseAsyncTask(purchaseDao).execute(purchase)
    }

    fun update(purchase: Purchase) {
        if (hasInitialized) UpdatePurchaseAsyncTask(purchaseDao).execute(purchase)
    }

    fun delete(purchase: Purchase) {
        if (hasInitialized) DeletePurchaseAsyncTask(purchaseDao).execute(purchase)
    }

    fun deleteAllPurchases() {
        if (hasInitialized) DeleteAllPurchasesAsyncTask(purchaseDao).execute()
    }

    class InsertPurchaseAsyncTask(private val dao: PurchaseDao) : AsyncTask<Purchase, Void?, Void?>() {
        override fun doInBackground(vararg params: Purchase): Void? {
            dao.insert(params[0])
            return null
        }
    }

    class UpdatePurchaseAsyncTask(private val dao: PurchaseDao) : AsyncTask<Purchase, Void?, Void?>() {
        override fun doInBackground(vararg params: Purchase): Void? {
            dao.update(params[0])
            return null
        }
    }

    class DeletePurchaseAsyncTask(private val dao: PurchaseDao) : AsyncTask<Purchase, Void?, Void?>() {
        override fun doInBackground(vararg params: Purchase): Void? {
            dao.delete(params[0])
            return null
        }
    }

    class DeleteAllPurchasesAsyncTask(private val dao: PurchaseDao) : AsyncTask<Void?, Void?, Void?>() {
        override fun doInBackground(vararg params: Void?): Void? {
            dao.deleteAll()
            return null
        }
    }
}