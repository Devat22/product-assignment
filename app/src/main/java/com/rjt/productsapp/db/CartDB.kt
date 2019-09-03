package com.rjt.productsapp.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.rjt.productsapp.data.Cart

class CartDB(context: Context):SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {
    companion object {
        private val DATABASE_NAME = "products"
        private val DATABASE_VERSION = 14
        private val TABLE_NAME = "cart"
        private val COLUMN_ID = "id"
        private val COLUMN_P_NAME = "product_name"
        private val COLUMN_UNIT_PRICE = "unit_price"
        private val COLUMN_COUNT = "count"
        private val COLUMN_TOTAL_PRICE = "total_price"
        private val COLUMN_P_IMAGE = "image"

    }
    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE = (
                "create table "+ TABLE_NAME+"("+
                        COLUMN_ID+ " INTEGER, "+
                        COLUMN_P_NAME+" text, "+
                        COLUMN_UNIT_PRICE+ " DOUBLE PRECISION, "+
                        COLUMN_COUNT+ " INTEGER, "+
                        COLUMN_TOTAL_PRICE+ " DOUBLE PRECISION ,"+
                        COLUMN_P_IMAGE+ " INTEGER )"
                )
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(DROP_TABLE)
        onCreate(db)
    }

    public fun addCart(cart:Cart){
        val db = this.writableDatabase
        var values = ContentValues()
        values.put(COLUMN_ID,cart.id)
        values.put(COLUMN_P_NAME,cart.product)
        values.put(COLUMN_UNIT_PRICE,cart.unitPrice)
        values.put(COLUMN_COUNT,cart.count)
        values.put(COLUMN_TOTAL_PRICE,cart.totalCost)
        values.put(COLUMN_P_IMAGE,cart.image)
        db.insert(TABLE_NAME,null,values)
        db.close()
    }

    public fun updateCart(cart: Cart){
        Log.d("cart","cart +1")
        val db = this.writableDatabase
        var values = ContentValues()
        values.put(COLUMN_COUNT,cart.count)
        values.put(COLUMN_TOTAL_PRICE,cart.totalCost)
        db.update(TABLE_NAME,values, COLUMN_ID+"=?", arrayOf(cart.id.toString()))
        db.close()
    }

    public fun deleteCart(cart: Cart){
        val db = this.writableDatabase
        db.delete(TABLE_NAME, COLUMN_ID+"=?", arrayOf(cart.id.toString()))
        db.close()
    }
    public fun deleteAll(){
        val db = this.writableDatabase
        db.delete(TABLE_NAME, null,null)
        db.close()
    }
    public fun fetchAllCarts():ArrayList<Cart>{
        var carts = ArrayList<Cart>()
        val db = this.writableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val c = db.rawQuery(query,null)
        if(c.moveToFirst()){
            do {
                carts.add(
                    Cart(
                        c.getInt(c.getColumnIndex(COLUMN_ID)),
                        c.getString(c.getColumnIndex(COLUMN_P_NAME)),
                        c.getDouble(c.getColumnIndex(COLUMN_UNIT_PRICE)),
                        c.getInt(c.getColumnIndex(COLUMN_COUNT)),
                        c.getDouble(c.getColumnIndex(COLUMN_TOTAL_PRICE)),
                        c.getInt(c.getColumnIndex(COLUMN_P_IMAGE))
                        )
                )
            }while (c.moveToNext())
        }
        return carts
    }

    fun getCartsCount():Int{
        return fetchAllCarts().size
    }

    fun isCartExist(id:Int):Boolean{
        val db = this.writableDatabase
        val query = "SELECT * FROM ${TABLE_NAME} WHERE ${COLUMN_ID} = ${id}"
        val c = db.rawQuery(query,null)
        if(c.moveToFirst()){
            return true
        }
        return false

    }
}