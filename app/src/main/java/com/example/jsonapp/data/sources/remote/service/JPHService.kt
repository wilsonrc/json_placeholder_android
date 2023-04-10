package com.example.jsonapp.data.sources.remote.service

import com.example.jsonapp.data.sources.remote.models.CommentApiModel
import com.example.jsonapp.data.sources.remote.models.PostApiModel
import com.example.jsonapp.data.sources.remote.models.UserApiModel
import retrofit2.http.GET
import retrofit2.http.Path

interface JPHService {

    @GET("posts")
    suspend fun getPosts(): List<PostApiModel>

    @GET("posts/{id}")
    suspend fun getPost(@Path("id") id: String): PostApiModel

    @GET("posts/{id}/comments")
    suspend fun getComments(@Path("id") postId: String): List<CommentApiModel>

    @GET("users/{id}")
    suspend fun getUser(userId: String): UserApiModel

    @GET("users")
    suspend fun getUsers(): List<UserApiModel>

}
