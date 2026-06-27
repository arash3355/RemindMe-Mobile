package com.example.remindmeapp

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.remindmeapp.storage.SessionManager
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.remindmeapp.auth.LoginActivity
import com.example.remindmeapp.firebase.FirebaseAuthManager
import com.example.remindmeapp.storage.TaskStorage
import com.example.remindmeapp.R
import com.example.remindmeapp.storage.GuestStorage

class ProfileActivity : AppCompatActivity() {

    private lateinit var txtName: TextView

    private lateinit var txtEmail: TextView

    private lateinit var btnManage: Button

    private fun updateProfile() {

        if (SessionManager.isGuest) {

            txtName.text = "Guest User"

            txtEmail.text = "Continue without account"

            btnManage.text = "Login"

        } else {

            txtName.text =
                SessionManager.userEmail.substringBefore("@")

            txtEmail.text =
                SessionManager.userEmail

            btnManage.text = "Logout"

        }

    }

    override fun onResume() {

        super.onResume()

        SessionManager.refreshSession()

        updateProfile()

    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_profile)

        txtName = findViewById(R.id.txtName)

        txtEmail = findViewById(R.id.txtEmail)

        btnManage = findViewById(R.id.btnManage)

        updateProfile()

        btnManage.setOnClickListener {

            if (SessionManager.isGuest) {

                startActivity(
                    Intent(
                        this,
                        LoginActivity::class.java
                    )
                )

            } else {

                AlertDialog.Builder(this)

                    .setTitle("Logout")

                    .setMessage("Are you sure you want to logout?")

                    .setNegativeButton("Cancel", null)

                    .setPositiveButton("Logout") { _, _ ->

                        FirebaseAuthManager.logout()

                        SessionManager.logout()

                        TaskStorage.taskList.clear()

                        TaskStorage.taskList.addAll(

                            GuestStorage.loadTasks(this)

                        )

                        updateProfile()

                        Toast.makeText(
                            this,
                            "Logged out successfully",
                            Toast.LENGTH_SHORT
                        ).show()

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

                    .show()
            }

        }
    }
}