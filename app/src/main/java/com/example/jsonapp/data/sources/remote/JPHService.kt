package com.example.jsonapp.data.sources.remote

import retrofit2.http.GET
import retrofit2.http.Path

interface JPHService {

    @GET("posts")
    suspend fun getPosts(): List<PostApiModel>

    @GET("posts/{id}/comments")
    suspend fun getComments(@Path("id") postId: String): List<CommentApiModel>

    @GET("users/{id}")
    suspend fun getUser(userId: String): UserApiModel

    @GET("users")
    suspend fun getUsers(): List<UserApiModel>

}
