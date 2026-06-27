package com.example.remindmeapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.remindmeapp.storage.SessionManager
import com.example.remindmeapp.storage.TaskStorage
import com.example.remindmeapp.storage.GuestStorage
import com.example.remindmeapp.firebase.FirestoreManager
import com.example.remindmeapp.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

        val btnStart =
            findViewById<Button>(R.id.btnStart)

        btnStart.setOnClickListener {

            SessionManager.refreshSession()

            if (SessionManager.isGuest) {

                TaskStorage.taskList.clear()

                TaskStorage.taskList.addAll(

                    GuestStorage.loadTasks(this)

                )

            } else {

                FirestoreManager.getTasks(

                    { tasks ->

                        TaskStorage.taskList.clear()

                        TaskStorage.taskList.addAll(tasks)

                        startActivity(

                            Intent(
                                this,
                                HomeActivity::class.java
                            )

                        )

                        finish()

                    },

                    {

                        startActivity(

                            Intent(
                                this,
                                HomeActivity::class.java
                            )

                        )

                        finish()

                    }

                )

                return@setOnClickListener

            }

            startActivity(

                Intent(
                    this,
                    HomeActivity::class.java
                )

            )

            finish()

        }
    }
}