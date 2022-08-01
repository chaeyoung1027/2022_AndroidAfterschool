package com.example.githubusersearch

import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val userIdInput = findViewById<EditText>(R.id.user_id_input)
        val content = findViewById<TextView>(R.id.content)
        val image = findViewById<ImageView>(R.id.Profile_image)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(
                GsonConverterFactory.create(
//                    GsonBuilder().registerTypeAdapter(
//                        GitHubUser::class.java,
//                        GitHubUserDeserializer()
//                    ).create()
                )
            ).build()

        val apiService = retrofit.create(GitHubAPIService::class.java)

        findViewById<Button>(R.id.search_btn).setOnClickListener {
            val id = userIdInput.text.toString()
            val apiCallForData = apiService.getUser(id,"token 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")
            apiCallForData.enqueue(object : Callback<GitHubUser>{
                override fun onResponse(call: Call<GitHubUser>, response: Response<GitHubUser>) {
                    val code : Int = response.code()
                    Log.d("mytag", response.code().toString())
                    if(code.toString().startsWith("4")) {
                        Toast.makeText(this@MainActivity,
                            "유저를 찾을 수 없습니다.",
                            Toast.LENGTH_SHORT).show()
                    }else{
                        val data = response.body()!!
                        Log.d("mytag", data.toString())
                        content.text = "login: ${data.login}\nid:${data.id}\nname:${data.name}\nfollowers:${data.followers}\nfollowing:${data.following}"
                        Glide.with(this@MainActivity).load(data.avatarUrl).into(image);
                    }
                }

                override fun onFailure(call: Call<GitHubUser>, t: Throwable) {}

            })
        }
    }
}
