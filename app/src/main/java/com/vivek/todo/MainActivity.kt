package com.vivek.todo

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
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

        db.userDao().getAllUser().observe(this, Observer { list ->
            if (list.isNotEmpty()) {
                with(list[list.size-1]) {
                    val str = "$name $age $number $address"
                    binding.tvData.text = str
                }
            }
        })

    }

    /*
    * check fetch button on old commit
    * */
    var age:Int=20
    fun saveBtn(view: View) {
        age++
        GlobalScope.launch(Dispatchers.IO) {
            db.userDao().insert(User(1,"viveks", "5", "Amd", age))
        }
    }
}