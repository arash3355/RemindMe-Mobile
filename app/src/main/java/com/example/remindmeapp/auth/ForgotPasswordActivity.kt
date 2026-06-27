package com.example.remindmeapp.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.remindmeapp.R
import android.widget.TextView

class ForgotPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_forgot_password)

        findViewById<TextView>(R.id.txtBackLogin)

            .setOnClickListener {

                finish()

            }

    }

}