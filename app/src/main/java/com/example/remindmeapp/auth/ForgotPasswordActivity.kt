package com.example.remindmeapp.auth

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.remindmeapp.R
import com.example.remindmeapp.firebase.FirebaseAuthManager

class ForgotPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_forgot_password)

        val etEmail =
            findViewById<EditText>(R.id.etEmail)

        val btnReset =
            findViewById<Button>(R.id.btnReset)

        val txtBack =
            findViewById<TextView>(R.id.txtBackLogin)

        btnReset.setOnClickListener {

            val email = etEmail.text.toString().trim()

            if (email.isEmpty()) {

                Toast.makeText(

                    this,

                    "Please enter your email.",

                    Toast.LENGTH_SHORT

                ).show()

                return@setOnClickListener

            }

            FirebaseAuthManager.sendPasswordReset(

                email,

                {

                    Toast.makeText(

                        this,

                        "Password reset email has been sent.",

                        Toast.LENGTH_LONG

                    ).show()

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

        }

        txtBack.setOnClickListener {

            finish()

        }

    }

}