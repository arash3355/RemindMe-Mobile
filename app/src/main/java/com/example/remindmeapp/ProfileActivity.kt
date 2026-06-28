package com.example.remindmeapp

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.remindmeapp.storage.SessionManager
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.remindmeapp.auth.LoginActivity
import com.example.remindmeapp.firebase.FirebaseAuthManager
import com.example.remindmeapp.storage.TaskStorage
import com.example.remindmeapp.R
import com.example.remindmeapp.storage.GuestStorage
import android.widget.Switch
import com.example.remindmeapp.storage.NotificationPreference
import com.example.remindmeapp.utils.ToneMapper

import android.media.RingtoneManager
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import com.example.remindmeapp.notification.ReminderReceiver

class ProfileActivity : AppCompatActivity() {

    private lateinit var txtName: TextView

    private lateinit var txtEmail: TextView

    private lateinit var btnManage: Button

    private val reminderToneLauncher = registerForActivityResult(

        ActivityResultContracts.StartActivityForResult()

    ) { result ->

        if (result.resultCode == RESULT_OK) {

            val uri = result.data?.getParcelableExtra<Uri>(
                RingtoneManager.EXTRA_RINGTONE_PICKED_URI
            )

            if (uri != null) {

                NotificationPreference.setReminderToneUri(
                    this,
                    uri.toString()
                )

                loadNotificationSetting()

            }

        }

    }

    private val dueToneLauncher = registerForActivityResult(

        ActivityResultContracts.StartActivityForResult()

    ) { result ->

        if (result.resultCode == RESULT_OK) {

            val uri = result.data?.getParcelableExtra<Uri>(
                RingtoneManager.EXTRA_RINGTONE_PICKED_URI
            )

            if (uri != null) {

                NotificationPreference.setDueToneUri(

                    this,

                    uri.toString()

                )

                loadNotificationSetting()

            }

        }

    }

    private fun updateProfile() {

        if (SessionManager.isGuest) {

            txtName.text = "Guest User"

            txtEmail.text = "Continue without account"

            btnManage.text = "Login"

        } else {

            txtName.text =
                SessionManager.userEmail.substringBefore("@")

            txtEmail.text =
                SessionManager.userEmail

            btnManage.text = "Logout"

        }

    }

    private fun loadNotificationSetting() {

        val switchNotification =
            findViewById<Switch>(R.id.switchNotification)

        val txtReminderSound =
            findViewById<TextView>(R.id.txtReminderSound)

        val txtDueDateSound =
            findViewById<TextView>(R.id.txtDueDateSound)

        switchNotification.isChecked =
            NotificationPreference.isNotificationEnabled(this)

        val reminderUri = NotificationPreference.getReminderToneUri(this)

        if (reminderUri != null) {

            val ringtone = RingtoneManager.getRingtone(

                this,

                Uri.parse(reminderUri)

            )

            txtReminderSound.text = ringtone.getTitle(this) + " >"

        } else {

            txtReminderSound.text = "Default >"

        }

        val dueUri = NotificationPreference.getDueToneUri(this)

        if (dueUri != null) {

            val ringtone = RingtoneManager.getRingtone(

                this,

                Uri.parse(dueUri)

            )

            txtDueDateSound.text = ringtone.getTitle(this) + " >"

        } else {

            txtDueDateSound.text = "Default >"

        }
    }

    override fun onResume() {

        super.onResume()

        SessionManager.refreshSession()

        updateProfile()

        loadNotificationSetting()

    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_profile)

        val txtBack =
            findViewById<TextView>(R.id.txtBack)

        val switchNotification =
            findViewById<Switch>(R.id.switchNotification)

        val txtReminderSound =
            findViewById<TextView>(R.id.txtReminderSound)

        val txtDueDateSound =
            findViewById<TextView>(R.id.txtDueDateSound)


        loadNotificationSetting()


        txtName = findViewById(R.id.txtName)

        txtEmail = findViewById(R.id.txtEmail)

        btnManage = findViewById(R.id.btnManage)


        updateProfile()

        txtBack.setOnClickListener {

            finish()

        }

        switchNotification.setOnCheckedChangeListener { _, isChecked ->

            NotificationPreference.setNotificationEnabled(

                this,

                isChecked

            )

        }

        txtReminderSound.setOnClickListener {

            val intent = Intent(
                RingtoneManager.ACTION_RINGTONE_PICKER
            )

            intent.putExtra(
                RingtoneManager.EXTRA_RINGTONE_TYPE,
                RingtoneManager.TYPE_NOTIFICATION
            )

            reminderToneLauncher.launch(intent)

        }

        txtDueDateSound.setOnClickListener {

            val intent = Intent(
                RingtoneManager.ACTION_RINGTONE_PICKER
            )

            intent.putExtra(
                RingtoneManager.EXTRA_RINGTONE_TYPE,
                RingtoneManager.TYPE_ALARM
            )

            dueToneLauncher.launch(intent)

        }

        btnManage.setOnClickListener {

            if (SessionManager.isGuest) {

                startActivity(
                    Intent(
                        this,
                        LoginActivity::class.java
                    )
                )

            } else {

                AlertDialog.Builder(this)

                    .setTitle("Logout")

                    .setMessage("Are you sure you want to logout?")

                    .setNegativeButton("Cancel", null)

                    .setPositiveButton("Logout") { _, _ ->

                        FirebaseAuthManager.logout()

                        SessionManager.logout()

                        TaskStorage.taskList.clear()

                        TaskStorage.taskList.addAll(

                            GuestStorage.loadTasks(this)

                        )

                        updateProfile()

                        Toast.makeText(
                            this,
                            "Logged out successfully",
                            Toast.LENGTH_SHORT
                        ).show()

                        val intent = Intent(
                            this,
                            HomeActivity::class.java
                        )

                        intent.addFlags(
                            Intent.FLAG_ACTIVITY_NEW_TASK or
                                    Intent.FLAG_ACTIVITY_CLEAR_TASK
                        )

                        startActivity(intent)

                        finish()

                    }

                    .show()
            }

        }


//        val btnTest =
//            findViewById<Button>(R.id.btnTestNotification)
//
//        btnTest.setOnClickListener {
//
//            val intent = Intent(
//                this,
//                ReminderReceiver::class.java
//            )
//
//            intent.putExtra(
//                "title",
//                "Test Reminder"
//            )
//
//            intent.putExtra(
//                "description",
//                "Notification from ReminderReceiver."
//            )
//
//            sendBroadcast(intent)
//
//        }
    }
}