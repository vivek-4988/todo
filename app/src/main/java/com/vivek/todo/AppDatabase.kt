package com.vivek.todo

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(version = 1, entities = [User::class])
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao() : UserDao
}