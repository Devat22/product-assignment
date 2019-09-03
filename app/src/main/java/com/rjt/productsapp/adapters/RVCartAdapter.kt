package com.rjt.productsapp.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rjt.productsapp.R
import com.rjt.productsapp.data.Cart
import com.rjt.productsapp.db.CartDB
import kotlinx.android.synthetic.main.cart_layout.view.*

class RVCartAdapter(val products:ArrayList<Cart>, val context: Context):RecyclerView.Adapter<RVCartAdapter.ViewHolder>() {
    var listener:OnCartInteractions? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.cart_layout,parent,false))
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder?.productName.text = products.get(position).product
        holder?.productPrice.text = "$"+products.get(position).unitPrice.toString()
        holder?.productCounter.text = products.get(position).count.toString()
        holder?.productImage.setImageResource(products.get(position).image)
        holder?.ProductTotalPrice.text = "$"+(products.get(position).count * products.get(position).unitPrice).toString()

    }
    interface OnCartInteractions{
        fun onDeleteInteraction(carts:ArrayList<Cart>)
        fun onPlusInteraction(carts: ArrayList<Cart>)
        fun onMinusInteraction(carts: ArrayList<Cart>)
    }
    inner class ViewHolder(view:View):RecyclerView.ViewHolder(view){

        var productName = view.productName
        var productPrice = view.productPrice
        var productImage = view.image
        var productCounter = view.cartsCounter
        var ProductTotalPrice = view.unit_total

        init {

            view.plusCart.setOnClickListener{

                val cart = products.get(adapterPosition)
                cart.count ++;
                cart.totalCost += cart.unitPrice
                CartDB(context).updateCart(cart)
                onBindViewHolder(this,adapterPosition)
                listener = context as OnCartInteractions
                listener?.onPlusInteraction(products)
            }
            view.minusCart.setOnClickListener{
                val cart = products.get(adapterPosition)
                cart.count --;
                cart.totalCost -= cart.unitPrice
                CartDB(context).updateCart(cart)

                onBindViewHolder(this,adapterPosition)
                listener = context as OnCartInteractions
                if(cart.count <=1)
                    listener?.onDeleteInteraction(products)

                else listener?.onMinusInteraction(products)
            }

            view.removeCart.setOnClickListener{
                Log.d("add","+1")
                val cart = products.get(adapterPosition)
                CartDB(context).deleteCart(cart)
                products.remove(cart)
                notifyItemRemoved(adapterPosition);
                notifyItemRangeChanged(adapterPosition, products.size);
                listener = context as OnCartInteractions
                listener?.onDeleteInteraction(products)
            }
        }
    }
}