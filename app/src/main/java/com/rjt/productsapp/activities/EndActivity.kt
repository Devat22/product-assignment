package com.rjt.productsapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rjt.productsapp.R
import com.rjt.productsapp.db.CartDB
import kotlinx.android.synthetic.main.activity_end.*

class EndActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_end)
        home.setOnClickListener{

            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
}
