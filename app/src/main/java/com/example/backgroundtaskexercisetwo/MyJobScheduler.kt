package com.example.backgroundtaskexercisetwo

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Handler
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.util.*
import java.util.concurrent.Executors

class MyJobScheduler: JobService() {
    val rightNow: Calendar = Calendar.getInstance()
    val currenthour: Int = rightNow.get(Calendar.HOUR)
    val currentmin: Int = rightNow.get(Calendar.MINUTE)

    private val TAG = "MyJobScheduler"



    override fun onStopJob(params: JobParameters?): Boolean {
        Log.d(TAG,"Job Stopped")
        Toast.makeText(applicationContext, "Job Stopped", Toast.LENGTH_SHORT).show()
        return true
    }



    override fun onStartJob(params: JobParameters?): Boolean {
        createNotificationChannel1()
        for (i in 1..10) {


        Executors.newSingleThreadExecutor().execute {

            doWork()

        }

    }



            if (currenthour % 2 == 0) {
                createNotificationChannel1()
                val handler = Handler()
                handler.postDelayed({
                    createNotificationChannel2()
                }, 1000)
            } else {
                createNotificationChannel2()
                val handler = Handler()
                handler.postDelayed({
                    createNotificationChannel1()
                }, 1000)
            }




        jobFinished(params,true)
        Toast.makeText(applicationContext, "Job Finished", Toast.LENGTH_SHORT).show()
        return true

    }

    private fun doWork() {


        if (currentmin%2 == 0){


            createNotificationChannel1()

        }
        else{

            createNotificationChannel2()
        }

    }


    private fun createNotificationChannel1() {
        val channelId= "channelID"
        val channelName = "channel1"
        val notification_id = 1

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId, channelName,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                lightColor = Color.GREEN
                enableLights(true)
            }

            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }


        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = TaskStackBuilder.create(this).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)

        }


        val notification = NotificationCompat.Builder(this,channelId)
            .setContentTitle("Notification")
            .setContentText("next Notification")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setVibrate(longArrayOf(1000, 1000))
            .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
            .setAutoCancel(true)
            .build()

        val notificationManager = NotificationManagerCompat.from(this)

        notificationManager.notify(notification_id,notification)

    }



    private fun createNotificationChannel2() {
        val channelId = "channelID2"
        val channelName = "channel2"
        val notification_id = 2

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId, channelName,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                lightColor = Color.GREEN
                enableLights(true)
            }
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }


        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = TaskStackBuilder.create(this).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT)

        }


        val notification = NotificationCompat.Builder(this,channelId)
            .setContentTitle("Notification")
            .setContentText("Got a Notification")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        val notificationManager = NotificationManagerCompat.from(this)

        notificationManager.notify(notification_id,notification)

    }
}
