package com.example.remindmeapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.Spinner
import android.widget.ArrayAdapter
import android.view.View
import com.example.remindmeapp.model.Task
import com.example.remindmeapp.storage.TaskStorage
import android.widget.Toast
import com.example.remindmeapp.firebase.FirestoreManager
import com.example.remindmeapp.storage.SessionManager
import com.example.remindmeapp.R
import com.example.remindmeapp.storage.GuestStorage
import com.example.remindmeapp.notification.AlarmScheduler
import java.util.UUID

class AddTaskActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_add_task)

        val etTitle =
            findViewById<EditText>(R.id.etTitle)

        val etDescription =
            findViewById<EditText>(R.id.etDescription)

        val etDate =
            findViewById<EditText>(R.id.etDate)
        etDate.setOnClickListener {

            val calendar =
                java.util.Calendar.getInstance()

            DatePickerDialog(

                this,

                { _, year, month, day ->

                    etDate.setText(
                        "$day/${month + 1}/$year"
                    )

                },

                calendar.get(
                    java.util.Calendar.YEAR
                ),

                calendar.get(
                    java.util.Calendar.MONTH
                ),

                calendar.get(
                    java.util.Calendar.DAY_OF_MONTH
                )

            ).show()
        }

        val etTime =
            findViewById<EditText>(R.id.etTime)
        etTime.setOnClickListener {

            val calendar =
                java.util.Calendar.getInstance()

            TimePickerDialog(

                this,

                { _, hour, minute ->

                    etTime.setText(
                        String.format(
                            "%02d:%02d",
                            hour,
                            minute
                        )
                    )

                },

                calendar.get(
                    java.util.Calendar.HOUR_OF_DAY
                ),

                calendar.get(
                    java.util.Calendar.MINUTE
                ),

                true

            ).show()
        }

        val spinner =
            findViewById<Spinner>(
                R.id.spCategory
            )

        val reminderSpinner =
            findViewById<Spinner>(
                R.id.spReminder
            )

        val categories = arrayOf(

            "Study",
            "Work",
            "Personal",
            "Health",
            "Finance"

        )

        val reminders = arrayOf(

            "At time of event",

            "10 minutes before",

            "1 hour before",

            "1 day before",

            "3 days before"

        )

        spinner.adapter =
            ArrayAdapter(

                this,

                android.R.layout.simple_spinner_dropdown_item,

                categories

            )

        reminderSpinner.adapter =
            ArrayAdapter(

                this,

                android.R.layout.simple_spinner_dropdown_item,

                reminders

            )

        val btnSave =
            findViewById<Button>(R.id.btnSave)

        val btnDelete =
            findViewById<Button>(
                R.id.btnDelete
            )

        val position =
            intent.getIntExtra(
                "position",
                -1
            )

        if (position == -1) {
            btnDelete.visibility = View.GONE
        }

        etTitle.setText(
            intent.getStringExtra("title")
        )

        etDescription.setText(
            intent.getStringExtra("description")
        )

        etDate.setText(
            intent.getStringExtra("date")
        )

        etTime.setText(
            intent.getStringExtra("time")
        )

        val oldCategory =
            intent.getStringExtra("category")

        if (oldCategory != null) {

            val index =
                categories.indexOf(oldCategory)

            if (index >= 0) {
                spinner.setSelection(index)
            }
        }

        val oldReminder =
            intent.getStringExtra("reminder")

        if (oldReminder != null) {

            val index = reminders.indexOf(oldReminder)

            if (index >= 0) {

                reminderSpinner.setSelection(index)

            }

        }

        btnDelete.setOnClickListener {

            if (position == -1) {

                finish()

                return@setOnClickListener

            }

            if (SessionManager.isGuest) {

                TaskStorage.taskList.removeAt(position)

                GuestStorage.saveTasks(

                    this,

                    TaskStorage.taskList

                )

                val intent = Intent(

                    this,

                    HomeActivity::class.java

                )

                intent.addFlags(

                    Intent.FLAG_ACTIVITY_CLEAR_TOP or
                            Intent.FLAG_ACTIVITY_SINGLE_TOP

                )

                startActivity(intent)

                finish()

            } else {

                FirestoreManager.deleteTask(

                    TaskStorage.taskList[position].id,

                    {

                        FirestoreManager.getTasks(

                            { tasks ->

                                TaskStorage.taskList.clear()

                                TaskStorage.taskList.addAll(tasks)

                                val intent = Intent(

                                    this,

                                    HomeActivity::class.java

                                )

                                intent.addFlags(

                                    Intent.FLAG_ACTIVITY_CLEAR_TOP or
                                            Intent.FLAG_ACTIVITY_SINGLE_TOP

                                )

                                startActivity(intent)

                                finish()

                            },

                            { error ->

                                Toast.makeText(

                                    this,

                                    error,

                                    Toast.LENGTH_LONG

                                ).show()

                            }

                        )

                    },

                    { error ->

                        Toast.makeText(

                            this,

                            error,

                            Toast.LENGTH_LONG

                        ).show()

                    }

                )

            }

        }

        btnSave.setOnClickListener {

            val task = Task(

                id = if (position == -1)

                    UUID.randomUUID().toString()

                else

                    TaskStorage.taskList[position].id,

                title = etTitle.text.toString(),

                description = etDescription.text.toString(),

                date = etDate.text.toString(),

                time = etTime.text.toString(),

                category = spinner.selectedItem.toString(),

                reminder =
                    reminderSpinner.selectedItem.toString(),

                isDone = if (position == -1)
                    false
                else
                    TaskStorage.taskList[position].isDone

            )

            if (SessionManager.isGuest) {

                if (position == -1) {

                    TaskStorage.taskList.add(task)

                } else {

                    TaskStorage.taskList[position] = task

                }

                GuestStorage.saveTasks(

                    this,

                    TaskStorage.taskList

                )

                AlarmScheduler.scheduleReminder(



                    this,

                    task

                )
//
                AlarmScheduler.scheduleDueDate(

                    this,

                    task

                )

                val intent = Intent(
                    this,
                    HomeActivity::class.java
                )

                intent.addFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TOP or
                            Intent.FLAG_ACTIVITY_SINGLE_TOP
                )

                startActivity(intent)

                finish()

            } else {

                if (position == -1) {

                    FirestoreManager.addTask(

                        task,

                        {

                            FirestoreManager.getTasks(

                                { tasks ->

                                    TaskStorage.taskList.clear()

                                    TaskStorage.taskList.addAll(tasks)

                                    Toast.makeText(
                                        this,
                                        "Task saved",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    val intent = Intent(
                                        this,
                                        HomeActivity::class.java
                                    )

                                    intent.addFlags(

                                        Intent.FLAG_ACTIVITY_CLEAR_TOP or
                                                Intent.FLAG_ACTIVITY_SINGLE_TOP

                                    )

                                    startActivity(intent)

                                    finish()

                                },

                                { error ->

                                    Toast.makeText(

                                        this,

                                        error,

                                        Toast.LENGTH_LONG

                                    ).show()

                                }

                            )

                        },

                        { error ->

                            Toast.makeText(

                                this,

                                error,

                                Toast.LENGTH_LONG

                            ).show()

                        }

                    )

                    AlarmScheduler.scheduleReminder(

                        this,

                        task

                    )
//
                    AlarmScheduler.scheduleDueDate(

                        this,

                        task

                    )

                } else {

                    FirestoreManager.updateTask(

                        task,

                        {

                            FirestoreManager.getTasks(

                                { tasks ->

                                    TaskStorage.taskList.clear()

                                    TaskStorage.taskList.addAll(tasks)

                                    val intent = Intent(

                                        this,

                                        HomeActivity::class.java

                                    )

                                    intent.addFlags(

                                        Intent.FLAG_ACTIVITY_CLEAR_TOP or
                                                Intent.FLAG_ACTIVITY_SINGLE_TOP

                                    )

                                    AlarmScheduler.scheduleReminder(

                                        this,

                                        task

                                    )
//
                                    AlarmScheduler.scheduleDueDate(

                                        this,

                                        task

                                    )

                                    startActivity(intent)

                                    finish()

                                },

                                { error ->

                                    Toast.makeText(

                                        this,

                                        error,

                                        Toast.LENGTH_LONG

                                    ).show()

                                }

                            )

                        },

                        { error ->

                            Toast.makeText(

                                this,

                                error,

                                Toast.LENGTH_LONG

                            ).show()

                        }

                    )

                }

            }

        }
    }
}