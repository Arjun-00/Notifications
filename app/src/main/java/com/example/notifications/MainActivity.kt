package com.example.notifications

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput

class MainActivity : AppCompatActivity() {

    private val channelID = "com.example.notifications.channel1"
    private var notificationManager: NotificationManager? = null
    //key for reply text
    private val KEY_REPLY = "key_reply"

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val button: Button = findViewById(R.id.button)
        createNotification(channelID,"DEMO!","THIS IS.")
        button.setOnClickListener {
            displayNotification()
        }
    }

    @SuppressLint("NotificationPermission")
    private fun displayNotification(){
        ///Intent
        val tapResultIntent = Intent(this,SecondActivity::class.java)
        val pendingIntent:PendingIntent = PendingIntent.getActivity(
            this,
            0,
            tapResultIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        //Add Button like (Details and Settings)
        val intent1 = Intent(this,DetailsActivity::class.java)
        val pendingIntent1:PendingIntent =PendingIntent.getActivity(this,
            0,
            intent1,
            PendingIntent.FLAG_UPDATE_CURRENT
            )
        val action1 : NotificationCompat.Action = NotificationCompat.Action.Builder(0,"Details",pendingIntent1).build()

        val intent2 = Intent(this,SettingsActivity::class.java)
        val pendingIntent2:PendingIntent =PendingIntent.getActivity(this,
            0,
            intent2,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val action2 : NotificationCompat.Action = NotificationCompat.Action.Builder(1,"Settings",pendingIntent2).build()

        //renote action for reply input
        val remoteInputs : RemoteInput = RemoteInput.Builder(KEY_REPLY).run {
            setLabel("Insert your name here...!")
            build()
        }
        //remenber to add android x package of REmoteInput
        val replyAction : NotificationCompat.Action = NotificationCompat.Action.Builder(
            0,"REPLEY",pendingIntent2
        ).addRemoteInput(remoteInputs).build()


        val notificationId= 45
        val notification : Notification = NotificationCompat.Builder(this,channelID)
            .setContentTitle("Demo Title")
            .setContentText("This is a Demo notification")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setAutoCancel(true)
            //new -1
          // for replay button working .setContentIntent(pendingIntent)
            //new -2 Button
            .addAction(action1)
            .addAction(action2)
            .addAction(replyAction)
            .setPriority(NotificationCompat.PRIORITY_HIGH).build()
        notificationManager?.notify(notificationId,notification)
    }

    private fun createNotification(id:String,name:String,channelDescription:String){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(id,name,importance).apply {
                description = channelDescription
            }
            notificationManager?.createNotificationChannel(channel)

        }
    }
}

//Tap Action
//Action Button
//Direct Reply Action