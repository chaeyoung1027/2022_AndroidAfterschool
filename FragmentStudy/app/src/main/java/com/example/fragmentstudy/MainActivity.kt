package com.example.fragmentstudy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import org.w3c.dom.Text

class MainActivity : AppCompatActivity(),
    CurrencyConverterFragment3.CurrencyCalculationListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val transcation = supportFragmentManager.beginTransaction()
        //transcation.add(R.id.fragment_container, CurrencyConverterFragment1())
//        transcation.add(R.id.fragment_container,
//            CurrencyConverterFragment2.newInstance("USD", "KRW"))
//        transcation.add(R.id.fragment_container,
//            CurrencyConverterFragment2.newInstance("KRW", "USD"))
        transcation.add(R.id.fragment_container,
            CurrencyConverterFragment3.newInstance("USD", "KRW"))
        transcation.commit()
    }

    override fun onCalculate(result: Double, amount: Double, from: String, to: String) {
        Log.d("mytag", result.toString())
        findViewById<TextView>(R.id.result).text = result.toString()
    }
}