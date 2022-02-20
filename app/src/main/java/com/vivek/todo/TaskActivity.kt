package com.vivek.todo

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.vivek.todo.databinding.ActivityTaskBinding
import com.vivek.todo.dbwork.AppDb
import com.vivek.todo.dbwork.ToDoModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

const val DB_NAME = "myTodo.db"

class TaskActivity : AppCompatActivity() {

    lateinit var myCalender: Calendar
    lateinit var dateListener: DatePickerDialog.OnDateSetListener
    lateinit var timeListener: TimePickerDialog.OnTimeSetListener

    lateinit var binding: ActivityTaskBinding

    private val labales = arrayListOf("Business", "Insurance", "Shopping", "Cooking")
    lateinit var myBusiness: String

    val db by lazy {
        AppDb.getDb(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_task)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_task)
        myCalender = Calendar.getInstance()

        binding.datePick.setOnClickListener {
            dateListener =
                DatePickerDialog.OnDateSetListener { _: DatePicker, year: Int, month: Int, day: Int ->
                    myCalender.set(Calendar.YEAR, year)
                    myCalender.set(Calendar.MONTH, month)
                    myCalender.set(Calendar.DAY_OF_MONTH, day)
                    updateDate()
                }

            val datePickDialog = DatePickerDialog(
                this,
                dateListener,
                myCalender.get(Calendar.YEAR),
                myCalender.get(Calendar.MONTH),
                myCalender.get(Calendar.DAY_OF_MONTH)
            )
            datePickDialog.datePicker.minDate = System.currentTimeMillis()
            datePickDialog.show()
        }

        binding.timePick.setOnClickListener {
            timeListener =
                TimePickerDialog.OnTimeSetListener { _: TimePicker, hourOfDay: Int, min: Int ->
                    myCalender.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    myCalender.set(Calendar.MINUTE, min)
                    updateTime();
                }

            val timePickDialog = TimePickerDialog(
                this,
                timeListener,
                myCalender.get(Calendar.HOUR_OF_DAY),
                myCalender.get(Calendar.MINUTE), false
            )
            timePickDialog.show()
        }

        setUpSpinner()
    }

    private fun setUpSpinner() {
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, labales)
        labales.sort()
        binding.spinner.adapter = adapter
        binding.spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                myBusiness = labales[position]
            }

        }
    }

    private fun updateTime() {
        val myFormat = "h:m a"
        val sdf = SimpleDateFormat(myFormat, Locale.ENGLISH)
        binding.timePick.text = sdf.format(myCalender.time)
    }

    private fun updateDate() {
        val myFormat = "EEE, d MMM yyy"
        val sdf = SimpleDateFormat(myFormat, Locale.ENGLISH)
        binding.datePick.text = sdf.format(myCalender.time)
        binding.timePick.visibility = View.VISIBLE
    }

    /*
    * save using coroutine
    * */
    fun saveTask(view: View) {
        var title = binding.textInputEditText.text.toString()
        var desc = binding.textInputEditText2.text.toString()
        var date = binding.datePick.text.toString()
        var time = binding.timePick.text.toString()

        GlobalScope.launch(IO) {
            db?.dao()?.insert(ToDoModel(title, desc, myBusiness, date = date, time = time))
        }
        finish()
    }
}