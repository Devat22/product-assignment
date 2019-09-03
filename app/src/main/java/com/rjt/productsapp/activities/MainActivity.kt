package com.rjt.productsapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.rjt.productsapp.R
import com.rjt.productsapp.adapters.RVAdapter
import com.rjt.productsapp.adapters.RVCartAdapter
import com.rjt.productsapp.data.Cart
import com.rjt.productsapp.data.Product
import com.rjt.productsapp.db.CartDB
import com.rjt.productsapp.db.ProductDB
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(),RVAdapter.onItemInteractionListener {


    override fun onItemInteraction(bundle: Product) {

        startActivity(Intent(this,Details::class.java).putExtra("products",bundle))
    }

    var producs:ArrayList<Product> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()

    }
    fun cartsCounter():Int{
        val dbCarts = CartDB(this)
        val carts = dbCarts.fetchAllCarts()
        var counts = 0
        carts.forEach { el-> counts +=el.count}
        return counts
    }
    fun init(){
        val dbProduct = ProductDB(this)
        recycle_view.layoutManager = LinearLayoutManager(this)

        producs = dbProduct.fetchProducts()
//        dbProduct.update()
        recycle_view.adapter = RVAdapter(producs,this)

        cart_count.text = cartsCounter().toString()
        gotocart.setOnClickListener{
            val intent = Intent(this,CartActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        cart_count.text = cartsCounter().toString()
    }
}
