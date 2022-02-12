package com.vivek.todo

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Insert
    fun insertAll(user: User)

    @Delete
    fun delete(user: User)

    @Query("select * from User")
    fun getAllUser(): LiveData<List<User>>

    @Query("select * from User where age >= :age")
    fun getAge(age: Int): List<User>

}