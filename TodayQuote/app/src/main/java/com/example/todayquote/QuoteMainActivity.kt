package com.example.todayquote

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import org.w3c.dom.Text
import java.util.Random

data class Quote(var idx: Int, var text: String, var from: String? = null){
//    fun method(){}
    companion object{
        fun savePreference(pref : SharedPreferences,
                           idx : Int,
                           text : String,
                           from : String=""): Quote {
            val editor = pref.edit()

            editor.putString("${idx}.text", text)
            editor.putString("${idx}.from", from.trim())

            editor.apply()

            return Quote(idx, text, from)
        }

    fun getQuotesFromPreference(pref: SharedPreferences): MutableList<Quote>{
        val quotes = mutableListOf<Quote>()

        /*for(i in 0 .. 20){//20까지

        }*/

        for(i in 0 until 20){
            val quoteText = pref.getString("${i}.text","")!!
            val quoteFrom = pref.getString("${i}.from","")!!
            if(quoteText.isNotBlank()){
                quotes.add(Quote(i, quoteText,quoteFrom))
            }
        }
        return quotes
    }

        fun removeQuoteFromPreference(
            pref : SharedPreferences, idx : Int){
            //TODO : 내용 작성

            val editor = pref.edit()

            editor.remove("${idx}.text")
            editor.remove("${idx}.from")

            editor.apply()
        }
    }
}

class QuoteMainActivity : AppCompatActivity() {
    // 액티비티는 생성자 호출을 우리가 할 수 없고, 운영체제가 수행하므로
    // 생성자에서 해당 값을 초기화를 못시켜주니까 lateinit으로 해서
    // 나중에 해당 값이 사용 전 반드시 초기화됨을 약속함
    private lateinit var quotes: MutableList<Quote>
    private lateinit var pref : SharedPreferences

    fun initializeQuotes(){
        val initialized = pref.getBoolean("initialized", false)
        if(!initialized) {
            //TODO : 적절한 초기 명언 저장하기
                Quote.savePreference(pref, 0, "명언 1", "출처 1")
                Quote.savePreference(pref, 1, "명언 2", "출처 2")
                Quote.savePreference(pref, 2, "명언 3", "출처 3")

            val editor = pref.edit()
            editor.putBoolean("initialized", true)
                    editor.apply()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quote_main_activity)

        var quoteText = findViewById<TextView>(R.id.quote_text)
        var quoteFrom = findViewById<TextView>(R.id.quote_from)

        pref = getSharedPreferences("quotes",Context.MODE_PRIVATE)
        initializeQuotes()

        quotes = Quote.getQuotesFromPreference(pref)

        if(quotes.isNotEmpty()){
            val idx = Random().nextInt(quotes.size)
            val q = quotes[idx]

        quoteText.text = q.text
        quoteFrom.text = q.from
        }else{
            quoteText.text = "저장된 명언이 없습니다."
            quoteFrom.text = ""
        }

        val listButton = findViewById<Button>(R.id.quote_list)
            listButton.setOnClickListener {
            val intent = Intent(this, QuoteListActivity::class.java)
            startActivity(intent)
        }

        val edit_button = findViewById<Button>(R.id.quote_edit_button)

        edit_button.setOnClickListener {
            val intent = Intent(this, QuoteEditActivity::class.java)
            startActivity(intent)

//        quotes = mutableListOf()
////        var nums = listOf(1, 2, 3)
//
//        var q1 = Quote(1, "명언 1", "출처 1")
//        quotes.add(q1)
//        quotes.add(Quote(2, "명언 2", "출처 2"))
//        quotes.add(Quote(3, "명언 3", "출처 3"))
//
//        // java.util.Random
//        val randomIndex = Random().nextInt(quotes.size)
//        val randomQuote = quotes[randomIndex]
//
//        quoteText.text = randomQuote.text
//        quoteFrom.text = randomQuote.from
        }
    }
}