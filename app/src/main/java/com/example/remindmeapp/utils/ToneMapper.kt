package com.example.remindmeapp.utils

object ToneMapper {

    fun getDisplayName(file: String): String {

        return when(file){

            "none" -> "None"

            "alert1" -> "Beep Alarm"

            "alert2" -> "Film Special Effect (1)"

            "alert3" -> "Film Special Effect (2)"

            "alert4" -> "Warning Siren"

            "alert5" -> "Alarm Going-Off"

            "alert6" -> "Technology"

            "alert7" -> "Classic Bell"

            "alert8" -> "Flutie"

            else -> "Beep Alarm"

        }

    }

}