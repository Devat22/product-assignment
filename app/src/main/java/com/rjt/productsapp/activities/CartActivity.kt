package com.rjt.productsapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.rjt.productsapp.R
import com.rjt.productsapp.adapters.RVCartAdapter
import com.rjt.productsapp.data.Cart
import com.rjt.productsapp.db.CartDB
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.cart_layout.*

class CartActivity : AppCompatActivity(),RVCartAdapter.OnCartInteractions {
    var carts = ArrayList<Cart>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        init()
    }
    fun init(){
        cart_recyler.layoutManager = LinearLayoutManager(this)
        carts = CartDB(this).fetchAllCarts()
        cart_recyler.adapter = RVCartAdapter(carts,this)
        updateCart(carts)

        goToPayment.setOnClickListener{
            val intent = Intent(this,PaymentActivity::class.java)
            intent.putExtra("amount",total_price.text.toString())
            startActivity(intent)
        }

    }
    override fun onPlusInteraction(carts: ArrayList<Cart>) {
        item_counter.text = "Items: "
        total_price.text = "Amount: $"
        updateCart(carts)
    }

    override fun onMinusInteraction(carts: ArrayList<Cart>) {
        item_counter.text = "Items: "
        total_price.text = "Amount: $"
        updateCart(carts)
    }

    override fun onDeleteInteraction(carts:ArrayList<Cart>) {
        item_counter.text = "Items: "
        total_price.text = "Amount: $"
        updateCart(carts)
    }
    fun updateCart(carts: ArrayList<Cart>){
        var totalPrice = 0.0;
        var total_count = 0;
        carts.forEach { el -> total_count += el.count }
        item_counter.append(total_count.toString())
        carts.forEach { el-> totalPrice += el.unitPrice * el.count }
        total_price.append(totalPrice.toString())
    }
}
