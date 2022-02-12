package com.vivek.todo

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {

    @Insert
    suspend fun insert(user: User)

    @Insert
    fun insertAll(user: User)

    @Delete
    fun delete(user: User)

    @Query("select * from User")
    suspend fun getAllUser(): List<User>

    @Query("select * from User where age >= :age")
    fun getAge(age: Int): List<User>

}