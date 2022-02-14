package com.vivek.todo.dbwork

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ToDoModel::class], version = 1)
abstract class AppDb:RoomDatabase() {

    abstract fun dao():dao

}