package com.example.remindmeapp.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.remindmeapp.R
import android.content.Intent
import android.widget.TextView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.remindmeapp.HomeActivity
import com.example.remindmeapp.firebase.FirebaseAuthManager
import com.example.remindmeapp.storage.SessionManager
import com.example.remindmeapp.storage.TaskStorage
import com.example.remindmeapp.utils.SyncDialog
import com.example.remindmeapp.firebase.FirestoreManager

class   LoginActivity : AppCompatActivity() {

    private fun openHome() {

        val intent = Intent(

            this,

            HomeActivity::class.java

        )

        intent.addFlags(

            Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_CLEAR_TASK

        )

        startActivity(intent)

        finish()

    }

    private fun loadCloudTasks() {

        TaskStorage.taskList.clear()

        FirestoreManager.getTasks(

            { tasks ->

                TaskStorage.taskList.addAll(tasks)

                openHome()

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

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        val etEmail =
            findViewById<EditText>(R.id.etEmail)

        val etPassword =
            findViewById<EditText>(R.id.etPassword)

        val btnLogin =
            findViewById<Button>(R.id.btnLogin)

        btnLogin.setOnClickListener {

            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {

                Toast.makeText(
                    this,
                    "Email dan Password wajib diisi.",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            FirebaseAuthManager.login(

                email,

                password,

                {

                    SessionManager.refreshSession()

                    if (TaskStorage.taskList.isNotEmpty()) {

                        SyncDialog.show(

                            this,

                            {

                                FirestoreManager.uploadTasks(

                                    TaskStorage.taskList,

                                    {

                                        Toast.makeText(

                                            this,

                                            "Tasks synchronized successfully",

                                            Toast.LENGTH_SHORT

                                        ).show()

                                        loadCloudTasks()

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

                            {

                                loadCloudTasks()

                            }

                        )

                    } else {

                        loadCloudTasks()

                    }

                },

                { message ->

                    Toast.makeText(

                        this,

                        message,

                        Toast.LENGTH_LONG

                    ).show()

                }

            )

        }

        findViewById<TextView>(R.id.txtRegister)

            .setOnClickListener {

                startActivity(

                    Intent(

                        this,

                        RegisterActivity::class.java

                    )

                )
            }

        findViewById<TextView>(R.id.txtForgotPassword)

            .setOnClickListener {

                startActivity(

                    Intent(

                        this,

                        ForgotPasswordActivity::class.java

                    )

                )

            }

    }

}