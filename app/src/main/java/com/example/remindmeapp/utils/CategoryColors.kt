package com.example.remindmeapp.utils

import android.graphics.Color

object CategoryColor {

    fun get(category: String): Int {

        return when (category) {

            "Study" ->
                Color.parseColor("#3B82F6")

            "Work" ->
                Color.parseColor("#10B981")

            "Health" ->
                Color.parseColor("#EF4444")

            "Personal" ->
                Color.parseColor("#8B5CF6")

            "Finance" ->
                Color.parseColor("#F59E0B")

            else ->
                Color.parseColor("#94A3B8")

        }

    }

}