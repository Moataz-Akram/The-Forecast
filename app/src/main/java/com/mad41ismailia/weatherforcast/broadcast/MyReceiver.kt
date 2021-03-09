package com.mad41ismailia.weatherforcast.broadcast

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.mad41ismailia.weatherforcast.R
import com.mad41ismailia.weatherforcast.entity.DatabaseClasses.AlarmData
import com.mad41ismailia.weatherforcast.repo.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MyReceiver : BroadcastReceiver() {
private lateinit var alarm: AlarmData
    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        val id = intent.getStringExtra("ID")
        Toast.makeText(context, "coming id $id", Toast.LENGTH_LONG).show()

        val builder : NotificationCompat.Builder = NotificationCompat.Builder(context, "ALARM_CHANNEL")
                                                        .setSmallIcon(R.drawable.alarm_icon)
                                                        .setContentTitle("Notification Title")
                                                        .setContentText("Notification Text")
                                                        .setPriority(NotificationCompat.PRIORITY_HIGH)

        val notificationManagerCompat:NotificationManagerCompat = NotificationManagerCompat.from(
            context
        )
        notificationManagerCompat.notify(33, builder.build())

        val repo = Repository.createObject(Application())
        val repository = Repository.getRepoObject()

        CoroutineScope(Dispatchers.Default).let {
            alarm = repository.getAlarm(id)
        }

        var mMediaPlayer = MediaPlayer()
        mMediaPlayer = MediaPlayer.create(context, R.raw.notification_up)
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        mMediaPlayer.start()
        Thread.sleep(5000)
        Toast.makeText(context, "coming id $alarm", Toast.LENGTH_LONG).show()
        mMediaPlayer.stop()

    }

}