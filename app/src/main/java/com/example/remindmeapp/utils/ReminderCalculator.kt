package com.example.remindmeapp.utils

import com.example.remindmeapp.model.Task
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object ReminderCalculator {

    private val formatter =

        SimpleDateFormat(

            "d/M/yyyy HH:mm",

            Locale.getDefault()

        )

    fun getReminderTime(

        task: Task

    ): Long {

        val dateTime =

            formatter.parse(

                "${task.date} ${task.time}"

            ) ?: return System.currentTimeMillis()

        val calendar = Calendar.getInstance()

        calendar.time = dateTime

        when (task.reminder) {

            "10 minutes before" ->

                calendar.add(Calendar.MINUTE, -10)

            "1 hour before" ->

                calendar.add(Calendar.HOUR_OF_DAY, -1)

            "1 day before" ->

                calendar.add(Calendar.DAY_OF_MONTH, -1)

            "3 day before" ->

                calendar.add(Calendar.DAY_OF_MONTH, -3)

            "At time of event" -> {

                // tidak mengurangi waktu

            }

        }

        return calendar.timeInMillis

    }

    fun getDueDateTime(

        task: Task

    ): Long {

        val dateTime =

            formatter.parse(

                "${task.date} ${task.time}"

            )

                ?: return System.currentTimeMillis()

        return dateTime.time

    }

}