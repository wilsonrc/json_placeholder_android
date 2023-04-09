package com.example.jsonapp.data.sources.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface JPHService {

    @GET("posts")
    fun getPosts(): List<PostApiModel>

    @GET("posts/{id}/comments")
    fun getComments(@Path("id") postId: String): List<CommentApiModel>

    @GET("users/{id}")
    fun getUser(userId: String): UserApiModel

    companion object {
        private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

        fun create(): JPHService {
            val logger =
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(JPHService::class.java)
        }
    }
}
