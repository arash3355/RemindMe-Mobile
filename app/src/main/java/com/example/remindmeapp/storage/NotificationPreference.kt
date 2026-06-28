package com.example.remindmeapp.storage

import android.content.Context

object NotificationPreference {

    private const val PREF_NAME = "notification_setting"

    private const val KEY_NOTIFICATION = "notification"

    private const val KEY_REMINDER_SOUND = "reminder_sound"

    private const val KEY_DUE_SOUND = "due_sound"

    private const val KEY_REMINDER_URI = "reminder_uri"

    private const val KEY_DUE_URI = "due_uri"


    fun isNotificationEnabled(

        context: Context

    ): Boolean {

        return context

            .getSharedPreferences(

                PREF_NAME,

                Context.MODE_PRIVATE

            )

            .getBoolean(

                KEY_NOTIFICATION,

                true

            )

    }

    fun setNotificationEnabled(

        context: Context,

        enabled: Boolean

    ) {

        context

            .getSharedPreferences(

                PREF_NAME,

                Context.MODE_PRIVATE

            )

            .edit()

            .putBoolean(

                KEY_NOTIFICATION,

                enabled

            )

            .apply()

    }

    fun getReminderSound(

        context: Context

    ): String {

        return context

            .getSharedPreferences(

                PREF_NAME,

                Context.MODE_PRIVATE

            )

            .getString(

                KEY_REMINDER_SOUND,

                "alert1"

            )!!

    }

    fun setReminderSound(

        context: Context,

        sound: String

    ) {

        context

            .getSharedPreferences(

                PREF_NAME,

                Context.MODE_PRIVATE

            )

            .edit()

            .putString(

                KEY_REMINDER_SOUND,

                sound

            )

            .apply()

    }

    fun getDueDateSound(

        context: Context

    ): String {

        return context

            .getSharedPreferences(

                PREF_NAME,

                Context.MODE_PRIVATE

            )

            .getString(

                KEY_DUE_SOUND,

                "alert5"

            )!!

    }

    fun setDueDateSound(

        context: Context,

        sound: String

    ) {

        context

            .getSharedPreferences(

                PREF_NAME,

                Context.MODE_PRIVATE

            )

            .edit()

            .putString(

                KEY_DUE_SOUND,

                sound

            )

            .apply()

    }

    fun getReminderToneUri(
        context: Context
    ): String? {

        return context
            .getSharedPreferences(
                PREF_NAME,
                Context.MODE_PRIVATE
            )
            .getString(
                KEY_REMINDER_URI,
                null
            )

    }

    fun setReminderToneUri(
        context: Context,
        uri: String
    ) {

        context
            .getSharedPreferences(
                PREF_NAME,
                Context.MODE_PRIVATE
            )
            .edit()
            .putString(
                KEY_REMINDER_URI,
                uri
            )
            .apply()

    }

    fun getDueToneUri(
        context: Context
    ): String? {

        return context
            .getSharedPreferences(
                PREF_NAME,
                Context.MODE_PRIVATE
            )
            .getString(
                KEY_DUE_URI,
                null
            )

    }

    fun setDueToneUri(
        context: Context,
        uri: String
    ) {

        context
            .getSharedPreferences(
                PREF_NAME,
                Context.MODE_PRIVATE
            )
            .edit()
            .putString(
                KEY_DUE_URI,
                uri
            )
            .apply()

    }

}