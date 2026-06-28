package com.example.remindmeapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.remindmeapp.model.Task
import com.example.remindmeapp.adapter.TaskAdapter
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import com.example.remindmeapp.storage.TaskStorage
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.remindmeapp.utils.Constant
import com.example.remindmeapp.R
import com.example.remindmeapp.firebase.FirestoreManager
import com.example.remindmeapp.storage.GuestStorage
import com.example.remindmeapp.storage.SessionManager


class HomeActivity : AppCompatActivity() {

    private lateinit var rvTask: RecyclerView

    private lateinit var rvCompleted: RecyclerView


    private lateinit var activeAdapter: TaskAdapter

    private lateinit var completedAdapter: TaskAdapter

    private lateinit var txtEmpty: TextView

    private lateinit var etSearch: EditText

    private val activeTaskList = mutableListOf<Task>()

    private val completedTaskList = mutableListOf<Task>()

    private val taskList
        get() = TaskStorage.taskList

    private fun updateTaskCount() {

        val count =

            taskList.count {

                !it.isDone

            }

        findViewById<TextView>(R.id.txtCount)

            .text = "$count Tasks Today"

    }

    private fun checkEmptyState() {

        txtEmpty.visibility =

            if (taskList.isEmpty())
                View.VISIBLE
            else
                View.GONE

        findViewById<TextView>(R.id.txtCompleted)

            .visibility =

            if (

                taskList.any {

                    it.isDone

                }

            )

                View.VISIBLE

            else

                View.GONE
    }

    private fun refreshRecycler() {

        activeTaskList.clear()

        completedTaskList.clear()

        taskList.forEach {

            if (it.isDone)

                completedTaskList.add(it)

            else

                activeTaskList.add(it)

        }

        activeAdapter.notifyDataSetChanged()

        completedAdapter.notifyDataSetChanged()

        updateTaskCount()

        checkEmptyState()

    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_home)

        rvTask =
            findViewById(R.id.rvTask)

        rvCompleted =
            findViewById(R.id.rvCompleted)

        txtEmpty =
            findViewById(R.id.txtEmpty)

        etSearch =
            findViewById(R.id.etSearch)

        val imgProfile =
            findViewById<ImageView>(
                R.id.imgProfile
            )

        val bottomNav =
            findViewById<View>(R.id.bottomNav)

        val btnAdd =
            bottomNav.findViewById<Button>(R.id.btnAdd)

        val btnCalendar =

            bottomNav.findViewById<Button>(R.id.btnCalendar)

        activeAdapter = TaskAdapter(

            activeTaskList,

            { task ->

                if (SessionManager.isGuest) {

                    GuestStorage.saveTasks(
                        this,
                        TaskStorage.taskList
                    )

                    refreshRecycler()

                } else {

                    FirestoreManager.updateTask(

                        task,

                        {

                            refreshRecycler()

                        },

                        {

                            refreshRecycler()

                        }

                    )

                }

            }

        ) { position ->

            editTask(

                TaskStorage.taskList.indexOf(
                    activeTaskList[position]
                )

            )

        }

        completedAdapter = TaskAdapter(

            completedTaskList,

            { task ->

                if (SessionManager.isGuest) {

                    GuestStorage.saveTasks(
                        this,
                        TaskStorage.taskList
                    )

                    refreshRecycler()

                } else {

                    FirestoreManager.updateTask(

                        task,

                        {

                            refreshRecycler()

                        },

                        {

                            refreshRecycler()

                        }

                    )

                }

            }

        ) { position ->

            editTask(

                TaskStorage.taskList.indexOf(
                    completedTaskList[position]
                )

            )

        }

        rvTask.layoutManager =
            LinearLayoutManager(this)

        rvCompleted.layoutManager =
            LinearLayoutManager(this)

        rvTask.adapter =
            activeAdapter

        rvCompleted.adapter =
            completedAdapter

        refreshRecycler()

        btnAdd.setOnClickListener {

            val intent =
                Intent(
                    this,
                    AddTaskActivity::class.java
                )

            startActivityForResult(
                intent,
                Constant.REQUEST_ADD_TASK
            )
        }

        imgProfile.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    ProfileActivity::class.java
                )
            )
        }

        btnCalendar.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    CalendarActivity::class.java
                )
            )
        }

        setupSearch()


    }

    private fun setupSearch() {

        etSearch.addTextChangedListener(

            object : TextWatcher {

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {}

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {

                    filterTask(
                        s.toString()
                    )

                }

                override fun afterTextChanged(
                    s: Editable?
                ) {}

            }

        )

    }

    private fun filterTask(keyword: String) {

        activeTaskList.clear()

        completedTaskList.clear()

        taskList.forEach { task ->

            val match =

                task.title.contains(keyword, true) ||

                        task.description.contains(keyword, true) ||

                        task.category.contains(keyword, true)

            if (match) {

                if (task.isDone)

                    completedTaskList.add(task)

                else

                    activeTaskList.add(task)

            }

        }

        activeAdapter.notifyDataSetChanged()

        completedAdapter.notifyDataSetChanged()

    }

    private fun editTask(position: Int) {

        val task = taskList[position]


        val intent =
            Intent(
                this,
                AddTaskActivity::class.java
            )

        intent.putExtra(
            Constant.EXTRA_TITLE,
            task.title
        )

        intent.putExtra(
            Constant.EXTRA_DESCRIPTION,
            task.description
        )

        intent.putExtra(
            Constant.EXTRA_DATE,
            task.date
        )

        intent.putExtra(
            Constant.EXTRA_TIME,
            task.time
        )

        intent.putExtra(
            Constant.EXTRA_CATEGORY,
            task.category
        )

        intent.putExtra(
            Constant.EXTRA_POSITION,
            position
        )

        intent.putExtra(
            Constant.EXTRA_REMINDER,
            task.reminder

        )

        startActivityForResult(
            intent,
            Constant.REQUEST_EDIT_TASK
        )
    }

    override fun onResume() {

        super.onResume()

        refreshRecycler()

    }
}

