package com.example.remindmeapp.notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.remindmeapp.HomeActivity
import com.example.remindmeapp.R

class ReminderReceiver : BroadcastReceiver() {

    override fun onReceive(

        context: Context,

        intent: Intent

    ) {
        android.util.Log.d(
            "REMINDER_TEST",
            "Receiver berhasil dipanggil"
        )

        val type =

            intent.getStringExtra("type")

                ?: "REMINDER"

        val title =

            intent.getStringExtra("title")
                ?: "Task Reminder"

        val description =

            intent.getStringExtra("description")
                ?: ""

        val openIntent = Intent(

            context,

            HomeActivity::class.java

        )

        openIntent.flags =

            Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_CLEAR_TOP

        val pendingIntent = PendingIntent.getActivity(

            context,

            0,

            openIntent,

            PendingIntent.FLAG_UPDATE_CURRENT or
                    PendingIntent.FLAG_IMMUTABLE

        )

        val notification =

            NotificationCompat.Builder(

                context,

                NotificationHelper.CHANNEL_ID

            )

                .setSmallIcon(R.mipmap.ic_launcher)

                .setContentTitle(

                    if (type == "REMINDER")

                        "🔔 Reminder"

                    else

                        "⏰ Due Date"

                )

                .setContentText(

                    if (type == "REMINDER")

                        "$title\n$description"

                    else

                        "$description\nDeadline has arrived."

                )

                .setStyle(

                    NotificationCompat.BigTextStyle()

                        .bigText(

                            if (type == "REMINDER")

                                "$title\n$description"

                            else

                                "$description\nDeadline has arrived."

                        )

                )

                .setContentIntent(pendingIntent)

                .setAutoCancel(true)

                .setPriority(

                    NotificationCompat.PRIORITY_HIGH

                )

                .build()

        val manager =

            context.getSystemService(

                Context.NOTIFICATION_SERVICE

            ) as NotificationManager

        manager.notify(

            System.currentTimeMillis().toInt(),

            notification

        )

    }

}