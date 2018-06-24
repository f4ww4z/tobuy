package com.jagoancoding.tobuy.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(entities = [Purchase::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {

    abstract fun purchaseDao(): PurchaseDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(applicationContext: Context): AppDatabase? {
            INSTANCE = INSTANCE ?: Room
                    .databaseBuilder(applicationContext,
                            AppDatabase::class.java,
                            "shopping_list")
                    .build()
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}