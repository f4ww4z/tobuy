package com.jagoancoding.tobuy

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jagoancoding.tobuy.ui.tobuy.ToBuyFragment

class ToBuyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.to_buy_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, ToBuyFragment.newInstance())
                    .commitNow()
        }
    }

}
