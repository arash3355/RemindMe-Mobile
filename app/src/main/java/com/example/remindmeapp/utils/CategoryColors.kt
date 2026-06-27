package com.example.remindmeapp.utils

import android.graphics.Color

object CategoryColor {

    fun get(category: String): Int {

        return when (category) {

            "Study" ->
                Color.parseColor("#D6EFFF")

            "Work" ->
                Color.parseColor("#D9F7D9")

            "Health" ->
                Color.parseColor("#FFE0E0")

            "Personal" ->
                Color.parseColor("#EEE3FF")

            "Finance" ->
                Color.parseColor("#FFF3CD")

            else ->
                Color.WHITE

        }

    }

}