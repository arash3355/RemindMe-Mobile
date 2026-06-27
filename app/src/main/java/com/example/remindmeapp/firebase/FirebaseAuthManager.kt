package com.example.remindmeapp.firebase

import com.google.firebase.auth.FirebaseAuth

object FirebaseAuthManager {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun getAuth(): FirebaseAuth {

        return auth

    }

    fun isLoggedIn(): Boolean {

        return auth.currentUser != null

    }

    fun getCurrentUserEmail(): String? {

        return auth.currentUser?.email

    }

    fun logout() {

        auth.signOut()

    }

    fun login(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {

        auth.signInWithEmailAndPassword(email, password)

            .addOnSuccessListener {

                onSuccess()

            }

            .addOnFailureListener {

                onFailure(
                    it.localizedMessage ?: "Login failed."
                )

            }

    }

    fun register(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {

        auth.createUserWithEmailAndPassword(
            email,
            password
        )
            .addOnSuccessListener {

                onSuccess()

            }
            .addOnFailureListener {

                onFailure(
                    it.localizedMessage ?: "Registration failed."
                )

            }

    }

}