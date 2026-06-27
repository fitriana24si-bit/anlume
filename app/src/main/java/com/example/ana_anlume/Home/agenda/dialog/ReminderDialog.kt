package com.example.ana_anlume.Home.agenda.dialog

import android.app.AlertDialog
import android.content.Context

object ReminderDialog {

    fun show(

        context: Context,

        onSelected: (Int) -> Unit

    ) {

        val pilihan = arrayOf(

            "5 Menit",

            "10 Menit",

            "30 Menit",

            "60 Menit"

        )

        val menit = intArrayOf(

            5,

            10,

            30,

            60

        )

        AlertDialog.Builder(context)

            .setTitle("Pilih Reminder")

            .setItems(pilihan) { _, which ->

                onSelected(
                    menit[which]
                )

            }

            .setNegativeButton(
                "Batal",
                null
            )

            .show()

    }

}