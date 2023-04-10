package com.example.jsonapp.data.sources.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.jsonapp.data.sources.local.models.UserDbModel

@Dao
interface UsersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveUser(user: UserDbModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveUsers(users: List<UserDbModel>)

    @Query("SELECT * FROM users WHERE id = :id")
    fun getUser(id: String): UserDbModel

    @Query("SELECT * FROM users")
    fun getUsers(): List<UserDbModel>
}