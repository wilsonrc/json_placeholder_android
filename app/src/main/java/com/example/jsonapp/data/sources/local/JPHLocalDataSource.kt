package com.example.jsonapp.data.sources.local

import com.example.jsonapp.data.sources.JPHDataSource
import com.example.jsonapp.data.sources.models.CommentApiModel
import com.example.jsonapp.data.sources.models.PostApiModel
import com.example.jsonapp.data.sources.models.UserApiModel

class JPHLocalDataSource : JPHDataSource {
    override fun getPosts(): List<PostApiModel> {
        TODO("Not yet implemented")
    }

    override fun getComments(): List<CommentApiModel> {
        TODO("Not yet implemented")
    }

    override fun getUser(userId: String): UserApiModel {
        TODO("Not yet implemented")
    }
}
