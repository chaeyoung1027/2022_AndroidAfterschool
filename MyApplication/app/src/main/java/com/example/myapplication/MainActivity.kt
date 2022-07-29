package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_study2)

        // Q) 체크박스 뷰 가져오기
        val checkBox = findViewById<CheckBox>(R.id.checkbox)
        checkBox.setOnCheckedChangeListener {
                compoundButton, b ->
            if(b){
                //체크박스가 체크되었을 때 로직
                Log.d("my_tag", "checked")
            }
            else{
                //아닌 경우 로직
                Log.d("my_tag", "unchecked")
            }
        }

        val group = findViewById<RadioGroup>(R.id.radio_group)
        group.setOnCheckedChangeListener {
                radioGroup, id ->
            //when - case
            when(id){
                R.id.radio_button1 -> {
                    Log.d("my_tag", "버튼 1 선택")
                }
                R.id.radio_button2 -> {
                    Log.d("my_tag", "버튼 2 선택")
                }
            }
        }
        val spinner = findViewById<Spinner>(R.id.my_spinner)
        val adapter = ArrayAdapter.createFromResource(
            this, // context: 쓰는 거 아님~!
            R.array.my_str_array,
            android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }
}

/*
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_layout)

        var id=R.id.my_button

        //식별자를 이용한 뷰 접근
        //findViewById<>(R.id.my_button)
        var myButton : Button = findViewById(R.id.my_button)
        var myEditText = findViewById(R.id.my_edittext) as EditText
        // var myTextView = findViewById<TextView>(R.id.my_textview)

       /* myButton.setOnClickListener(
            object: View.OnClickListener{
                override fun onClick(p0:View?){
                    Toast.makeText(this@MainActivity,
                    "클릭!", Toast.LENGTH_SHORT).show()
                }
            }
        )*/

      /*  myButton.setOnClickListener{
            v->
            Toast.makeText(this@MainActivity, "클릭!", Toast.LENGTH_SHORT).show()
        }*/

        //myButton.setOnClickListener {
        //    Toast.makeText(this@MainActivity, "클릭!", Toast.LENGTH_SHORT).show()
        //}

        myButton.setOnClickListener{
            Log.d("my_tag", "Hello")
        }

    }
}
*/