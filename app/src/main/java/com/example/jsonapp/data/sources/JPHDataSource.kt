package com.example.jsonapp.data.sources

import com.example.jsonapp.data.sources.models.CommentApiModel
import com.example.jsonapp.data.sources.models.PostApiModel
import com.example.jsonapp.data.sources.models.UserApiModel

interface JPHDataSource {
    fun getPosts() : List<PostApiModel>
    fun getComments() : List<CommentApiModel>
    fun getUser(userId : String) : UserApiModel
}