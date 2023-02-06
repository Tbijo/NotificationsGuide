package com.plcoding.notificationsguide

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat

// Write function to display a specific notification

// In a real project
// Write a abstraction for this like a interface that contains a function ( fun showNotification() )

class CounterNotificationService(
    // to access notification manager
    private val context: Context
) {
    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    // show notification with a counter
    fun showNotification(counter: Int) {
        // normal intent
        val activityIntent = Intent(context, MainActivity::class.java)

        // our normal intent wrapped in a pending intent for notification
        // if you want to send the intent to a service use getService()
        // if you want to send the intent to a foregroundService use getForegroundService()
        // We want to use activity so get activity
        val activityPendingIntent = PendingIntent.getActivity(
            context,
            1,
            activityIntent,
            // Flags define what should be done with the pending intent in the end
            // FLAG_UPDATE_CURRENT - a newly sent pending intent with same request code will be updated
            // FLAG_CANCEL_CURRENT - existing pend. intent with same request code will be canceled
            // Necessary to use FLAG_IMMUTABLE or MUTABLE
            // so whatever receives this intent it cannot or can directly manipulate it
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
        )

        // A pending intent that we send to component of our app
        // and the component will increment our counter
        // EZ way with BroadcastReceiver
        // sending a broadcast/send some kind of event
        // which the receiver will receive and trigger some app code
        val incrementIntent = PendingIntent.getBroadcast(
            context,
            2,
            Intent(context, CounterNotificationReceiver::class.java).apply {
                // you can attach data to send to the BroadcastReceiver
                putExtra("key", "data")
            },
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
        )

        // creating a notification
        val notification = NotificationCompat.Builder(
            context,
            // to know to which channel this notification belongs
            COUNTER_CHANNEL_ID
        )
            .setSmallIcon(R.drawable.ic_baseline_baby_changing_station_24)
            .setContentTitle("Increment counter")
            .setContentText("The count is $counter")

            // Specify the view of notification
            // example music has control buttons, background image
            // dragging down to see a preview
            //.setStyle()

            // the intent that will be sent when user clicks on the notification
            // needs to be a pending Intent
            // (-an intent that allows outside app or program to execute our app code)
            .setContentIntent(activityPendingIntent)

            // Action when we click on the notification
            // increment counter
            .addAction(
                R.drawable.ic_baseline_baby_changing_station_24,
                "Increment",
                incrementIntent
            )

            // user unable to swipe the notif away
            //.setOngoing()

            .build()

        // show notification
        // To show multiple notifications the id needs to be different
        // otherwise an existing notification will be updated
        notificationManager.notify(1, notification)
    }

    companion object {
        // notif channel ID
        const val COUNTER_CHANNEL_ID = "counter_channel"
    }
}