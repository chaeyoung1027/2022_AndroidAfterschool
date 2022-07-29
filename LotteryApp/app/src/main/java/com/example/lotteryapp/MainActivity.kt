package com.example.lotteryapp

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import org.w3c.dom.Text
import java.net.HttpCookie.parse
import java.net.URI
import java.util.logging.Level.parse


class MainActivity : AppCompatActivity() {
    lateinit var currentNums :String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pref = getSharedPreferences("nums", Context.MODE_PRIVATE)

        val lottoNumView = findViewById<TextView>(R.id.lotto_num)
        currentNums = generateRandomLottoNum(6, "-")
        lottoNumView.text = currentNums

        val generateNumberBtn = findViewById<Button>(R.id.gen_num)
        generateNumberBtn.setOnClickListener {
            currentNums = generateRandomLottoNum(6, "-")
            lottoNumView.text = currentNums
        }

        val saveNumberBtn = findViewById<Button>(R.id.save)
        saveNumberBtn.setOnClickListener {
            var lottoNums = pref.getString("lottonums","")
            var numList = if(lottoNums == ""){
                mutableListOf<String>()
            }else{
                lottoNums!!.split(",").toMutableList()
            }

        numList.add(currentNums)
        Log.d("mytag", numList.size.toString())
        Log.d("mytag", numList.toString())

        val editor = pref.edit()
        editor.putString("lottonums", numList.joinToString(","))
        editor.apply()


            findViewById<Button>(R.id.num_List).setOnClickListener {
                val intent = Intent(this,
                LotteryNumListActivity::class.java)
                startActivity(intent)
            }
        }

        findViewById<Button>(R.id.check_num).setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW,
                Uri.parse("https://m.dhlottery.co.kr/gameResult.do?method=byWin&wiselog=M_A_1_8"))
            startActivity(intent)
        }
    }


//        //1.
//        val nums = mutableListOf<Int>()
//        //2.
//        for(i in 1..6) nums.add((1..45).random())
//        val lottoNum = nums.joinToString ("-")
//        /*
//        var lottoNum = ""
//        for(i in 1..5){
//            lottoNum+=(1..45).random().toString()+"="
//        }
//        lottoNum+=(1..45).random().toString()
//         */
//
//        Log.d("mytag", lottoNum.toString())

    fun generateRandomLottoNum(count:Int, sep: String = "-") :String{
        val nums = mutableListOf<Int>()
        for( i in 1..count) nums.add((1..45).random())
        return nums.joinToString("-")
    }

}