package com.vivek.todo.dbwork

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.vivek.todo.DB_NAME

@Database(entities = [ToDoModel::class], version = 1)
abstract class AppDb : RoomDatabase() {

    companion object {

        @Volatile
        private var INSTANCE: AppDb? = null

        fun getDb(con: Context): AppDb? {
            val temInstance = INSTANCE
            if (temInstance != null) {
                return temInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    con.applicationContext, AppDb::class.java,
                    DB_NAME
                ).build()
                INSTANCE = instance
                return INSTANCE
            }
        }
    }

    abstract fun dao(): dao

}