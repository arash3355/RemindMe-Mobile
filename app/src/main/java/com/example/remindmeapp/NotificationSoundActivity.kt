package com.example.remindmeapp

import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.remindmeapp.storage.NotificationPreference

class NotificationSoundActivity : AppCompatActivity() {

    private var mediaPlayer: MediaPlayer? = null

    data class ReminderTone(

        val title: String,

        val fileName: String,

        val rawRes: Int,

        val layoutId: Int,

        val checkId: Int

    )

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_notification_sound)

        findViewById<TextView>(R.id.txtBack).setOnClickListener {

            finish()

        }

        val tones = listOf(

            ReminderTone(

                "None",

                "none",

                0,

                R.id.soundNone,

                R.id.checkNone

            ),

            ReminderTone(

                "Beep Alarm",

                "alert1",

                R.raw.alert1,

                R.id.soundAlert1,

                R.id.checkAlert1

            ),

            ReminderTone(

                "Film Spesial Effect (1)",

                "alert2",

                R.raw.alert2,

                R.id.soundAlert2,

                R.id.checkAlert2

            ),

            ReminderTone(

                "Film Spesial Effect (2)",

                "alert3",

                R.raw.alert3,

                R.id.soundAlert3,

                R.id.checkAlert3

            ),

            ReminderTone(

                "Warning Sirine",

                "alert4",

                R.raw.alert4,

                R.id.soundAlert4,

                R.id.checkAlert4

            ),

            ReminderTone(

                "Alarm Going-Off",

                "alert5",

                R.raw.alert5,

                R.id.soundAlert5,

                R.id.checkAlert5

            ),

            ReminderTone(

                "Technology",

                "alert6",

                R.raw.alert6,

                R.id.soundAlert6,

                R.id.checkAlert6

            ),

            ReminderTone(

                " ",

                "alert7",

                R.raw.alert7,

                R.id.soundAlert7,

                R.id.checkAlert7

            ),

            ReminderTone(

                "Flutie",

                "alert8",

                R.raw.alert8,

                R.id.soundAlert8,

                R.id.checkAlert8

            )

        )

        val current =

            NotificationPreference.getReminderSound(this)

        tones.forEach {

            findViewById<TextView>(it.checkId)

                .visibility = View.GONE

        }
        tones.firstOrNull {

            it.fileName == current

        }?.let {

            findViewById<TextView>(it.checkId)

                .visibility = View.VISIBLE

        }

        tones.forEach { tone ->

            findViewById<LinearLayout>(tone.layoutId)

                .setOnClickListener {

                    mediaPlayer?.release()
                    mediaPlayer = null

                    NotificationPreference.setReminderSound(

                        this,

                        tone.fileName

                    )

                    if (tone.rawRes == 0) {

                        finish()

                        return@setOnClickListener

                    }

                    mediaPlayer = MediaPlayer.create(

                        this,

                        tone.rawRes

                    )

                    mediaPlayer?.start()

                    mediaPlayer?.setOnCompletionListener {

                        finish()

                    }

                }

        }

    }

    override fun onDestroy() {

        super.onDestroy()

        mediaPlayer?.release()

        mediaPlayer = null

    }

}