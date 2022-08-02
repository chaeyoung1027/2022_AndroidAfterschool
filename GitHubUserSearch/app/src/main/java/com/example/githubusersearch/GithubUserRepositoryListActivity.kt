package com.example.githubusersearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GithubUserRepositoryListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.github_page)

        val id = intent.getStringExtra("userid")!!
        Log.d("mytag", id)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofit.create(GitHubAPIService::class.java)
        val apiCallForData = apiService.getRepos(id,
        "token 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")

        if(id.isBlank()){
            Toast.makeText(this@GithubUserRepositoryListActivity,
                "아이디를 입력해주세요.",
                Toast.LENGTH_SHORT).show()
        }else{
            apiCallForData.enqueue(object : Callback<List<GitHubRepo>>{
                override fun onResponse(call: Call<List<GitHubRepo>>, response: Response<List<GitHubRepo>>
                ) {
                    val data = response.body()!!
                    Log.d("mytag",data.toString())

                    val list = findViewById<RecyclerView>(R.id.repo_list)
                    list.layoutManager = LinearLayoutManager(this@GithubUserRepositoryListActivity)
                    list.adapter = GitHubRepoListAdapter(data)
                    list.setHasFixedSize(false)
                }
                override fun onFailure(call: Call<List<GitHubRepo>>, t: Throwable) {

                }
            })
        }


    }
}