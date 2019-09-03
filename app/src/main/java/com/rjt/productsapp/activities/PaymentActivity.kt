package com.rjt.productsapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.rjt.productsapp.R
import com.rjt.productsapp.db.CartDB
import kotlinx.android.synthetic.main.activity_payment.*

class PaymentActivity : AppCompatActivity() {
    var payment:Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        init()
    }
    fun init(){
        pay.text = "Pay "+intent.extras.get("amount").toString().substring(intent.extras.get("amount").toString().indexOf(" "),intent.extras.get("amount").toString().length)
        cash.setOnClickListener{
            credit.isChecked = false
            online.isChecked = false
            payment = 0
        }
        credit.setOnClickListener{
            cash.isChecked = false
            online.isChecked = false
            payment = 1
        }
        online.setOnClickListener{
            credit.isChecked = false
            cash.isChecked = false
            payment = 2
        }

        pay.setOnClickListener{
            when(payment){
                0->{

                    CartDB(this).deleteAll()
                    val intent = Intent(this,EndActivity::class.java)
                    startActivity(intent)
                }
                1->{

                    Toast.makeText(this,"Credit card is not supported yet, will be available soon please choose cash",Toast.LENGTH_LONG).show()
                }
                else->{

                    Toast.makeText(this,"Online is not supported yet, will be available soon please choose cash",Toast.LENGTH_LONG).show()
                }

            }
        }
    }
}
