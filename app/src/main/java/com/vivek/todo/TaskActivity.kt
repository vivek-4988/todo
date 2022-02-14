package com.vivek.todo

import android.app.DatePickerDialog
import android.os.Bundle
import android.provider.Settings
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.room.Room
import com.vivek.todo.databinding.ActivityTaskBinding
import com.vivek.todo.dbwork.AppDb
import java.text.SimpleDateFormat
import java.util.*
import java.util.logging.SimpleFormatter

const val DB_NAME = "myTodo.db"

class TaskActivity : AppCompatActivity() {

    lateinit var myCalender: Calendar
    lateinit var dateListener: DatePickerDialog.OnDateSetListener

    lateinit var binding: ActivityTaskBinding

    val db by lazy {
        Room.databaseBuilder(this, AppDb::class.java, DB_NAME)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_task)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_task)
        myCalender = Calendar.getInstance()

        binding.datePick.setOnClickListener {
            dateListener = DatePickerDialog.OnDateSetListener { _: DatePicker, year: Int, month: Int, day: Int ->
                myCalender.set(Calendar.YEAR, year)
                myCalender.set(Calendar.MONTH, month)
                myCalender.set(Calendar.DAY_OF_MONTH, day)
                updateDate()
            }

            val datePickDialog = DatePickerDialog(this,dateListener,myCalender.get(Calendar.YEAR),myCalender.get(Calendar.MONTH),myCalender.get(Calendar.DAY_OF_MONTH))
            datePickDialog.datePicker.minDate = System.currentTimeMillis()
            datePickDialog.show()
        }
    }

    private fun updateDate() {
        val myFormat="EEE, d MMM yyy"
        val sdf = SimpleDateFormat(myFormat, Locale.ENGLISH)
        binding.datePick.text = sdf.format(myCalender.time)
    }


}