package com.rjt.productsapp.data

import java.io.Serializable

data class Product(var id:Int, var product_name: String, var produc_desc: String, var price:Double, var image:Int) : Serializable {

}