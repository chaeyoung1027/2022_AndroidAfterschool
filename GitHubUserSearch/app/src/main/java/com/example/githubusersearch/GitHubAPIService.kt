package com.example.githubusersearch

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import java.lang.reflect.Type

interface GitHubAPIService {
    @GET("/users/{user}")
    fun getUser(
        @Path("user") id: String,
        @Header("Authorization") pat : String
    ) : Call<GitHubUser>
}

data class GitHubUser(
    val id: Int,
    val login: String,
    val name : String?,
    @SerializedName("avatar_url")
    val avatarUrl: String,
    val followers : Int,
    val following : Int
)

/*
class GitHubUserDeserializer : JsonDeserializer<GitHubUser> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): GitHubUser {
        val node = json?.getAsJsonObject()
        val id = node?.getAsJsonPrimitive("id")?.asInt
        val login = node?.getAsJsonPrimitive("login")?.asString
        val name = node?.getAsJsonPrimitive("name")?.asString

        return GitHubUser(id!!, login!!, name)
    }

}*/