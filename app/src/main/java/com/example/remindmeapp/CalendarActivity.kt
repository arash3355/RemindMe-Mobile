package com.example.remindmeapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CalendarView
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.remindmeapp.adapter.CalendarTaskAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.remindmeapp.model.Task
import com.example.remindmeapp.storage.TaskStorage
import com.example.remindmeapp.utils.DateUtils
import com.example.remindmeapp.R


class CalendarActivity : AppCompatActivity() {

    private lateinit var calendarView: CalendarView

    private lateinit var calendarAdapter: CalendarTaskAdapter

    private val calendarTaskList = mutableListOf<Task>()

    override fun onResume() {

        super.onResume()

        val selectedDate =

            DateUtils.format(
                calendarView.date
            )

        showTasksByDate(selectedDate)
    }

    fun showTasksByDate(date: String) {
        calendarTaskList.clear()

        calendarTaskList.addAll(

            TaskStorage.taskList.filter {

                it.date.trim() == date.trim()

            }
        )

        calendarAdapter.notifyDataSetChanged()

        findViewById<TextView>(R.id.txtMyTask).text =

            if (calendarTaskList.isEmpty())

                "No Tasks"

            else

                "Tasks (${calendarTaskList.size})"

    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_calendar)

        val profile =
            findViewById<ImageView>(
                R.id.imgProfile
            )

        val rvCalendarTask =
            findViewById<RecyclerView>(
                R.id.rvCalendarTask
            )

        val bottomNav =
            findViewById<View>(R.id.bottomNav)

        val btnHome =
            bottomNav.findViewById<Button>(R.id.btnHome)

        val btnAdd =
            bottomNav.findViewById<Button>(R.id.btnAdd)

        calendarAdapter = CalendarTaskAdapter(
            calendarTaskList
        )

        calendarView =
            findViewById(R.id.calendarView)

        rvCalendarTask.layoutManager =
            LinearLayoutManager(this)

        rvCalendarTask.adapter =
            calendarAdapter


        val today = DateUtils.today()

        showTasksByDate(today)

        calendarView.setOnDateChangeListener {

                _,
                year,
                month,
                dayOfMonth ->

            val selectedDate =

                "$dayOfMonth/${month + 1}/$year"

            showTasksByDate(selectedDate)
        }

        profile.setOnClickListener {

            startActivity(

                Intent(
                    this,
                    ProfileActivity::class.java
                )

            )
        }

        btnAdd.setOnClickListener {

            startActivity(

                Intent(
                    this,
                    AddTaskActivity::class.java
                )

            )

        }

        btnHome.setOnClickListener {

            finish()

        }
    }

}