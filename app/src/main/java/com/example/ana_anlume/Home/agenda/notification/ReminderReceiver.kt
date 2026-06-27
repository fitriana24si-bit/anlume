package com.example.ana_anlume.Home.agenda.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class ReminderReceiver : BroadcastReceiver() {

    override fun onReceive(
        context: Context,
        intent: Intent
    ) {

        val namaAgenda =
            intent.getStringExtra("agenda")
                ?: "Agenda Desa"

        NotificationHelper.showNotification(

            context,

            "Pengingat Agenda Desa",

            "$namaAgenda akan segera dimulai.\n\nTap untuk membuka Agenda Desa."

        )

    }

}