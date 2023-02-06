package com.plcoding.notificationsguide

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

// REGISTER RECEIVER IN MANIFEST
// Will receive a intent and increment the counter value

class CounterNotificationReceiver: BroadcastReceiver() {

    // function triggered when this receiver receives a pending intent
    override fun onReceive(context: Context, intent: Intent?) {
        // here we want to increment our counter
        // and update our notification

        // create instance of our notification service
        // thats what we use to show and update our notification
        val service = CounterNotificationService(context)

        // showing and updating existing notification
        service.showNotification(++Counter.value)

        // the id of the notification needs to be the same as the one we want to update
        // set in notificationManager.notify( 1, notification) to be recognized by Android

        // get data from intent
        intent?.let {
            val dataStr = it.getStringExtra("key")
            Log.d("myData", dataStr!!)
        }
    }
}