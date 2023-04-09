package com.example.jsonapp.data.sources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UsersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveUser(user: UserDbModel)

    @Query("SELECT * FROM users WHERE id = :id")
    fun getUser(id: String): UserDbModel

}