package com.vivek.todo

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.vivek.todo.databinding.ActivityMainBinding
import kotlinx.coroutines.*

/*
* ROOM is jetpack components ,
* testing in room and sqlite db
*
* //todo
* 1. Make User class with table
* 2. Make UserDao as interface for making different queries for one table
* 3. make RoomDatabase class , here AppDatabase
* 4. declare db by lazy
*
* */
class MainActivity : AppCompatActivity() {

    val db by lazy {
        Room.databaseBuilder(this, AppDatabase::class.java, "user.db").fallbackToDestructiveMigration().build()
    }

    lateinit var binding: ActivityMainBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    fun saveBtn(view: View) {
        GlobalScope.launch(Dispatchers.IO) {
            db.userDao().insert(User("vivek", "5", "Amd", 32))
        }
    }

    fun fetchBtn(view: View) {
        runBlocking {
            val list = GlobalScope.async(Dispatchers.IO) { db.userDao().getAllUser() }
            if (list.await().isNotEmpty()) {
                with(list.await()[0]) {
                    val str: String = "$name $age $number $address"
                    binding.tvData.text = str
                }
            }
        }
    }
}