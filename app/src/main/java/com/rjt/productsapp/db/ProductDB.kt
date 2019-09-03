package com.rjt.productsapp.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.rjt.productsapp.data.Product

class ProductDB(context: Context):SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object{
        private val DATABASE_NAME = "products"
        private val DATABASE_VERSION = 14
        private val TABLE_NAME = "product"
        private val COLUMN_ID = "id"
        private val COLUMN_P_NAME = "product_name"
        private val COLUMN_P_DESC = "product_description"
        private val COLUMN_P_PRICE = "product_price"
        private val COLUMN_P_IMAGE = "product_image"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE = (
                "create table "+ TABLE_NAME +"( "+
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+
                        COLUMN_P_NAME + " text, "+
                        COLUMN_P_DESC + " text, "+
                        COLUMN_P_PRICE + " DOUBLE PRECISION , "+
                        COLUMN_P_IMAGE + " INTEGER )"
                )
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val DROP_TABLE = "DROP TABLE IF EXISTS "+TABLE_NAME
        db?.execSQL(DROP_TABLE)
        onCreate(db)

    }

    fun addProduct(product: Product){
        val db = this.writableDatabase
        var values = ContentValues()
        values.put(COLUMN_P_NAME,product.product_name)
        values.put(COLUMN_P_DESC, product.produc_desc)
        values.put(COLUMN_P_PRICE, product.price)
        values.put(COLUMN_P_IMAGE, product.image)
        db.insert(TABLE_NAME,null,values)
        db.close()
    }

    fun fetchProducts():ArrayList<Product>{
        val db = this.writableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val c = db.rawQuery(query,null)

        var products = ArrayList<Product>()
        if(c.moveToFirst()){
            do{
                products.add(
                    Product(
                        c.getInt(c.getColumnIndex(COLUMN_ID)),
                        c.getString(c.getColumnIndex(COLUMN_P_NAME)),
                        c.getString(c.getColumnIndex(COLUMN_P_DESC)),
                        c.getDouble(c.getColumnIndex(COLUMN_P_PRICE)),
                        c.getInt(c.getColumnIndex(COLUMN_P_IMAGE))
                    )
                )
            }while (c.moveToNext())
        }
        return products
    }
//    fun update(){
//        val db = this.writableDatabase
//        var values = ContentValues()
//        values.put(COLUMN_P_NAME,"Iphone XS MAX")
//
//        val s = db.update(TABLE_NAME,values, COLUMN_ID+"=?", arrayOf(2.toString()))
//        Log.d("one",s.toString())
//        db.close()
//    }
}