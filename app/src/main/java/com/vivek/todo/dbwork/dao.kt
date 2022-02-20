package com.vivek.todo.dbwork

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query

@Dao
interface dao {

    @Insert(onConflict = IGNORE)
    suspend fun insert(todo:ToDoModel):Long

    @Query("select * from todomodel")
    fun getTask():LiveData<List<ToDoModel>>

    @Query("update todomodel set isFinished =1 where id=:uid")
    fun finishTask(uid:Long)

    @Query("delete from todomodel where id=:uid")
    fun deleteTask(uid:Long)


}