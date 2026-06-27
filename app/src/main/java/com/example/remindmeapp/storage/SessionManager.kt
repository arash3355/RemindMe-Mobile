package com.example.remindmeapp.storage

import com.example.remindmeapp.firebase.FirebaseAuthManager
import com.example.remindmeapp.firebase.FirestoreManager.db
import com.example.remindmeapp.model.Task

object SessionManager {

    var isGuest = true

    var isLoggedIn = false

    var userEmail = ""

    fun login(email: String) {

        isGuest = false

        isLoggedIn = true

        userEmail = email

    }

    fun loginGuest() {

        isGuest = true

        isLoggedIn = false

        userEmail = ""

    }

    fun logout() {

        isGuest = true

        isLoggedIn = false

        userEmail = ""

    }

    fun refreshSession() {

        isLoggedIn =
            FirebaseAuthManager.isLoggedIn()

        isGuest =
            !isLoggedIn

        userEmail =
            FirebaseAuthManager.getCurrentUserEmail() ?: ""

    }

}