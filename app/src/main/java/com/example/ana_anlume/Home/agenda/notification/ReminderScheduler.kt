package com.example.ana_anlume.Home.agenda.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent

object ReminderScheduler {

    fun scheduleReminder(

        context: Context,

        triggerTime: Long,

        namaAgenda: String

    ) {

        val intent = Intent(
            context,
            ReminderReceiver::class.java
        )

        intent.putExtra(
            "agenda",
            namaAgenda
        )

        val pendingIntent =
            PendingIntent.getBroadcast(

                context,

                namaAgenda.hashCode(),

                intent,

                PendingIntent.FLAG_UPDATE_CURRENT or
                        PendingIntent.FLAG_IMMUTABLE

            )

        val alarmManager =
            context.getSystemService(
                Context.ALARM_SERVICE
            ) as AlarmManager

        alarmManager.setExactAndAllowWhileIdle(

            AlarmManager.RTC_WAKEUP,

            triggerTime,

            pendingIntent

        )

    }

    fun cancelReminder(

        context: Context,

        namaAgenda: String

    ) {

        val intent =
            Intent(
                context,
                ReminderReceiver::class.java
            )

        val pendingIntent =
            PendingIntent.getBroadcast(

                context,

                namaAgenda.hashCode(),

                intent,

                PendingIntent.FLAG_UPDATE_CURRENT or
                        PendingIntent.FLAG_IMMUTABLE

            )

        val alarmManager =
            context.getSystemService(
                Context.ALARM_SERVICE
            ) as AlarmManager

        alarmManager.cancel(pendingIntent)

    }

}