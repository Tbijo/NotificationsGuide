package com.plcoding.notificationsguide

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

// REGISTER APP class

class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()
        // create a channel as soon as app is created (starts for the first time)
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        // notif channels are needed from version O
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                // every channel needs a ID
                CounterNotificationService.COUNTER_CHANNEL_ID,
                // name of channel, main category in device Settings
                "Counter",
                // importance of notifications
                // some notifications display only if you longClick on app icon
                // and most of them show in the status bar
                NotificationManager.IMPORTANCE_DEFAULT
            )
            // displayed in app settings and clicks on this channel
            channel.description = "Used for the increment counter notifications"

            // To create a channel using NotificationManager
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}