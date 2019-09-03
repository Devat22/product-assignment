package com.rjt.productsapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.rjt.productsapp.R
import com.rjt.productsapp.data.Cart
import com.rjt.productsapp.data.Product
import com.rjt.productsapp.db.CartDB
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.activity_details.image
import kotlinx.android.synthetic.main.activity_details.productDesc
import kotlinx.android.synthetic.main.activity_details.productName
import java.util.*

class Details : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        init()
    }
    fun init(){
        val db = CartDB(this)
        val intent = intent.extras.get("products") as Product
        this.productName.text = intent.product_name
        this.productDesc.text = intent.produc_desc
        this.productPrice.text = "Price: $"+intent.price.toString()
        this.image.setImageResource(intent.image)
        if(db.isCartExist(intent.id)){
            add.visibility = View.INVISIBLE
            return
        }
        add.setOnClickListener{
            db.addCart(Cart(intent.id,intent.product_name,intent.price,1,intent.price,intent.image))
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
