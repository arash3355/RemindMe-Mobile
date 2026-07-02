package com.example.remindmeapp.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.example.remindmeapp.model.Task
import com.example.remindmeapp.utils.ReminderCalculator

object AlarmScheduler {

    fun scheduleReminder(

        context: Context,

        task: Task

    ) {

        android.util.Log.d(
            "ALARM",
            "scheduleReminder() dipanggil"
        )

        if (task.reminder == "At time of event") {

            return

        }

        val triggerTime =

            ReminderCalculator.getReminderTime(task)

        android.util.Log.d(

            "ALARM",

            "Trigger = $triggerTime"

        )

        val intent = Intent(

            context,

            ReminderReceiver::class.java

        )

        intent.putExtra(

            "type",

            "REMINDER"

        )

        intent.putExtra(

            "title",

            task.title

        )

        intent.putExtra(

            "description",

            task.description

        )

        val pendingIntent = PendingIntent.getBroadcast(

            context,

            task.id.hashCode(),

            intent,

            PendingIntent.FLAG_UPDATE_CURRENT or
                    PendingIntent.FLAG_IMMUTABLE

        )

        val alarmManager =
            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            triggerTime,
            pendingIntent
        )

//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//
//            alarmManager.setExactAndAllowWhileIdle(
//
//                AlarmManager.RTC_WAKEUP,
//
//                triggerTime,
//
//                pendingIntent
//
//            )
//
//
//        } else {
//
//            alarmManager.setExact(
//
//                AlarmManager.RTC_WAKEUP,
//
//                triggerTime,
//
//                pendingIntent
//
//            )
//
//        }

    }

    fun scheduleDueDate(

        context: Context,

        task: Task

    ) {

        android.util.Log.d(
            "ALARM",
            "scheduleDueDate() dipanggil"
        )

        val triggerTime =

            ReminderCalculator.getDueDateTime(task)

        val intent = Intent(

            context,

            ReminderReceiver::class.java

        )

        intent.putExtra(

            "type",

            "DUE_DATE"

        )

        intent.putExtra(

            "title",

            "⏰ Due Date"

        )

        intent.putExtra(

            "description",

            task.title

        )

        val pendingIntent = PendingIntent.getBroadcast(

            context,

            task.id.hashCode() + 100000,

            intent,

            PendingIntent.FLAG_UPDATE_CURRENT or
                    PendingIntent.FLAG_IMMUTABLE

        )

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//
//            alarmManager.setExactAndAllowWhileIdle(
//
//                AlarmManager.RTC_WAKEUP,
//
//                triggerTime,
//
//                pendingIntent
//
//            )
//
//        } else {
//
//            alarmManager.setExact(
//
//                AlarmManager.RTC_WAKEUP,
//
//                triggerTime,
//
//                pendingIntent
//
//            )
//
//        }

        val alarmManager =
            context.getSystemService(
                Context.ALARM_SERVICE
            ) as AlarmManager

        alarmManager.set(

            AlarmManager.RTC_WAKEUP,

            triggerTime,

            pendingIntent

        )

        android.util.Log.d(

            "ALARM",

            "Due Date dijadwalkan: ${task.title}"

        )

    }

    fun cancelAlarm(

        context: Context,

        task: Task

    ) {

        val reminderIntent = Intent(

            context,

            ReminderReceiver::class.java

        )

        val reminderPendingIntent = PendingIntent.getBroadcast(

            context,

            task.id.hashCode(),

            reminderIntent,

            PendingIntent.FLAG_NO_CREATE or
                    PendingIntent.FLAG_IMMUTABLE

        )



        val dueIntent = Intent(

            context,

            ReminderReceiver::class.java

        )

        val duePendingIntent = PendingIntent.getBroadcast(

            context,

            task.id.hashCode() + 100000,

            dueIntent,

            PendingIntent.FLAG_NO_CREATE or
                    PendingIntent.FLAG_IMMUTABLE

        )

        val alarmManager =

            context.getSystemService(

                Context.ALARM_SERVICE

            ) as AlarmManager

        if (reminderPendingIntent != null) {

            alarmManager.cancel(reminderPendingIntent)

            reminderPendingIntent.cancel()

        }

        if (duePendingIntent != null) {

            alarmManager.cancel(duePendingIntent)

            duePendingIntent.cancel()

        }

        android.util.Log.d(

            "ALARM",

            "Alarm lama dibatalkan"

        )

    }

}