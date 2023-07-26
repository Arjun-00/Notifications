package com.example.notifications

import android.app.NotificationManager
import android.app.RemoteInput
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.app.NotificationCompat

class SettingsActivity : AppCompatActivity() {
    private val KEY_REPLY = "key_reply"
    lateinit var textView : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        textView = findViewById<TextView>(R.id.textView2)
        receiveInput()
    }

    private fun receiveInput(){
        val channelID = "com.example.notifications.channel1"
        val notificationId= 45
        val intent = this.intent
        val remoteInput = RemoteInput.getResultsFromIntent(intent)
        if(remoteInput!=null){
            val inputString = remoteInput.getCharSequence(KEY_REPLY).toString()
            textView.text = inputString.toString()


            val replyNotification= NotificationCompat.Builder(this,channelID).setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentText("Your reply received").build()
            val notificationManager : NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(notificationId,replyNotification)

        }
    }
}