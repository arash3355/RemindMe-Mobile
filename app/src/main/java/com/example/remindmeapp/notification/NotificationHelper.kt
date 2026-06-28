package com.example.remindmeapp.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

object NotificationHelper {

    const val CHANNEL_ID = "reminder_channel"

    fun createChannel(

        context: Context

    ) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(

                CHANNEL_ID,

                "Task Reminder",

                NotificationManager.IMPORTANCE_HIGH

            )

            channel.description =

                "Notification for RemindMe"

            val manager =

                context.getSystemService(

                    NotificationManager::class.java

                )

            manager.createNotificationChannel(channel)

        }

    }

}