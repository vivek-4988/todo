package com.vivek.todo

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.vivek.todo.databinding.ActivityMainBinding
import com.vivek.todo.dbwork.AppDb

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    val db by lazy {
        AppDb.getDb(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.toolbar)


        db?.dao()?.getTask()?.observe(this, androidx.lifecycle.Observer { list ->
            if (list.isNotEmpty()) {
                var adapter = ToDoAdapter(list)
                binding.recycle.adapter = adapter
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.history -> {
                startActivity(Intent(this, HistoryActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun clickNewTask(view: View) {
        startActivity(Intent(this, TaskActivity::class.java))
    }

}