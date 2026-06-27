package com.example.remindmeapp.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.remindmeapp.R
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.remindmeapp.firebase.FirebaseAuthManager

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_register)

        val etName = findViewById<EditText>(R.id.etName)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val etConfirm = findViewById<EditText>(R.id.etConfirmPassword)

        val btnRegister =
            findViewById<Button>(R.id.btnRegister)

        btnRegister.setOnClickListener {

            val name = etName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString()
            val confirm = etConfirm.text.toString()

            if (
                name.isEmpty() ||
                email.isEmpty() ||
                password.isEmpty() ||
                confirm.isEmpty()
            ) {

                Toast.makeText(
                    this,
                    "Semua field harus diisi.",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            if (password != confirm) {

                Toast.makeText(
                    this,
                    "Konfirmasi password tidak cocok.",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            FirebaseAuthManager.register(

                email,

                password,

                {

                    Toast.makeText(
                        this,
                        "Registrasi berhasil.",
                        Toast.LENGTH_SHORT
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

        findViewById<TextView>(R.id.txtLogin)

            .setOnClickListener {

                finish()

            }

    }

}