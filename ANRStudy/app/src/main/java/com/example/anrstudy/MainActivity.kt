package com.example.anrstudy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlin.math.sqrt
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val result = findViewById<TextView>(R.id.result)
        findViewById<Button>(R.id.btn).setOnClickListener {
            Toast.makeText(this,
            "Clicked!",
            Toast.LENGTH_SHORT).show()
        }
        findViewById<Button>(R.id.anr).setOnClickListener {
            Thread (Runnable {
                var sum = 0.0
                for (i in 1..60){
                    sum += sqrt(Random.nextDouble())
                    Thread.sleep(100)
                }
            runOnUiThread{
                result.text = sum.toString()
            }

            }).start()
        }
    }
}