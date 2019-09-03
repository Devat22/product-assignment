package com.rjt.productsapp.adapters

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.rjt.productsapp.R
import com.rjt.productsapp.activities.Details
import com.rjt.productsapp.adapters.RVAdapter.*
import com.rjt.productsapp.data.Product
import kotlinx.android.synthetic.main.custom_layout.view.*
import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList
import com.rjt.productsapp.adapters.RVAdapter.onItemInteractionListener as onItemInteractionListener1

class RVAdapter(val products:ArrayList<Product>, val context: Context): RecyclerView.Adapter<ViewHolder>() {
    private var lisitenr: onItemInteractionListener1?=null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.custom_layout,parent,false))
    }

    override fun getItemCount(): Int {
        return products.size
    }

    fun onAttach(){
        lisitenr = context as onItemInteractionListener
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products.get(position)
        holder?.productName?.text = product.product_name
        holder?.productDesc?.text = product.produc_desc
        holder?.productPrice?.text = product.price.toString()
        holder?.image.setImageResource(product.image)
        onAttach()
    }

    inner class ViewHolder(view:View): RecyclerView.ViewHolder(view){

        var productName = view.productName
        var productDesc = view.productDesc
        var productPrice = view.productPrice
        var image = view.image
        init {
            view.setOnClickListener{
                var product = products.get(adapterPosition)
                var intent = Intent(view.context,Details::class.java)

                lisitenr?.onItemInteraction(product)
            }
        }
    }
    interface onItemInteractionListener{
        public fun onItemInteraction(bundle: Product)
    }
}