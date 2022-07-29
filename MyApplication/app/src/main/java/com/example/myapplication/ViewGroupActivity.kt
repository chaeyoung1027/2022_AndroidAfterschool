package com.example.myapplication

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ToggleButton
import androidx.core.widget.addTextChangedListener

class ViewGroupActivity : AppCompatActivity() {
    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_study1)

        findViewById<Button>(R.id.button2)
        val button = findViewById<Button>(R.id.button2)
        button.setOnClickListener {
            Log.d("my_tag", "Onclick")
        }
        button.setOnLongClickListener {
            Log.d("my_tag", "OnLongclick")
            true
        }
        //true는 내가 일처리를 잘했다는 것을 알려줌! false는 잘못끝났다구 알려줌

        val toggle = findViewById<ToggleButton>(R.id.my_toggle_button)
        toggle.setOnCheckedChangeListener{
            compoundButton, isChecked ->
            Log.d("my_tag", "checked :${isChecked}")
        }
        //매개변수의 개수가 중요함!


        val editText = findViewById<EditText>(R.id.edittext2)
//        val e : Button = editText as Button
        editText.addTextChangedListener {
            Log.d("my_tag", it.toString())

        }
        editText.setOnFocusChangeListener{view, b ->
            Log.d("my_tag", "focused : ${b}")
            if(b) view.setBackgroundColor(Color.YELLOW)
            else view.setBackgroundColor(Color.WHITE)
        }

    }
}