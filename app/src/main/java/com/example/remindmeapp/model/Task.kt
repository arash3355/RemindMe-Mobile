package com.example.remindmeapp.model

data class Task(

    var id: String = "",

    var title: String = "",

    var description: String = "",

    var date: String = "",

    var time: String = "",

    var reminder: String = "At time of event",

    var category: String = "",

    var isDone: Boolean = false

)