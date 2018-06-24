package com.jagoancoding.tobuy.db

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey

@Entity
data class Purchase(@PrimaryKey(autoGenerate = true) var id: Long? = null,
                    @ColumnInfo(name = "name") var name: String = "",
                    @ColumnInfo(name = "description") var description: String = "",
                    @ColumnInfo(name = "price") var price: Double = 0.0,
                    @ColumnInfo(name = "quantity") var quantity: Int = 0,
                    @ColumnInfo(name = "included") var included: Boolean = true) {
    var totalPrice = price * quantity
}