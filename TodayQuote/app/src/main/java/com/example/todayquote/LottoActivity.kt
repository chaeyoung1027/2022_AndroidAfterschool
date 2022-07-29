package com.example.todayquote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import java.util.*

class LottoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lotto)

        var nums = findViewById<TextView>(R.id.lotto_nums)
        /*
        var n1 = (1..45).random()
        var n2 = (1..45).random()
        var n3 = (1..45).random()
        var n4 = (1..45).random()
        var n5 = (1..45).random()
        var n6 = (1..45).random()
        nums.text = "${n1}-${n2}-${n3}-${n4}-${n5}-${n6}"
        */

        var lottoNums = mutableListOf<Int>()
        for (i in 1..6) lottoNums.add((1..45).random())
        nums.text = "${lottoNums[0]}-${lottoNums[1]}-${lottoNums[2]}-${lottoNums[3]}-${lottoNums[4]}-${lottoNums[5]}"


    }
}