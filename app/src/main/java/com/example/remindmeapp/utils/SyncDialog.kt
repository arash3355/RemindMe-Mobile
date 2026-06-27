package com.example.remindmeapp.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog

object SyncDialog {

    fun show(

        context: Context,

        onSync: () -> Unit,

        onSkip: () -> Unit

    ) {

        AlertDialog.Builder(context)

            .setTitle("Sync Tasks")

            .setMessage(

                "We found local tasks on this device.\n\nWould you like to sync them to your account?"

            )

            .setPositiveButton("Sync") { _, _ ->

                onSync()

            }

            .setNegativeButton("Skip") { _, _ ->

                onSkip()

            }

            .setCancelable(false)

            .show()

    }

}