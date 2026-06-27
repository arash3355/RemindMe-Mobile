package com.example.remindmeapp.storage

import android.content.Context
import com.example.remindmeapp.model.Task
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object GuestStorage {

    private const val PREF_NAME = "guest_storage"

    private const val KEY_TASK = "guest_task"

    fun saveTasks(

        context: Context,

        taskList: List<Task>

    ) {

        val pref =

            context.getSharedPreferences(
                PREF_NAME,
                Context.MODE_PRIVATE
            )

        val json = Gson().toJson(taskList)

        pref.edit()

            .putString(
                KEY_TASK,
                json
            )

            .apply()

    }

    fun loadTasks(

        context: Context

    ): MutableList<Task> {

        val pref =

            context.getSharedPreferences(
                PREF_NAME,
                Context.MODE_PRIVATE
            )

        val json =

            pref.getString(
                KEY_TASK,
                null
            )

        if (json == null)

            return mutableListOf()

        val type =

            object : TypeToken<MutableList<Task>>() {}.type

        return Gson().fromJson(
            json,
            type
        )

    }

    fun clearTasks(

        context: Context

    ) {

        context.getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )

            .edit()

            .remove(KEY_TASK)

            .apply()

    }

}