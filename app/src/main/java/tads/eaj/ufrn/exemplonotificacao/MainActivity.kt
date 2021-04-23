package tads.eaj.ufrn.exemplonotificacao

import android.app.Notification
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.app.PendingIntent
import androidx.core.app.NotificationCompat
import android.app.NotificationManager
import android.app.NotificationChannel
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.View
import androidx.core.app.NotificationManagerCompat
import android.content.Intent


class MainActivity : AppCompatActivity() {

    private val CHANNEL_ID = "Aula"
    lateinit var mBuilder: NotificationCompat.Builder
    var pendingIntent: PendingIntent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createNotificationChannel()

        val intent = Intent(this, AlertDetails::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val description = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            channel.description = description
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun callHeadsUpNotification(v: View) {

        // Cria a notificação
        mBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setDefaults(Notification.DEFAULT_ALL)
            .setSmallIcon(android.R.drawable.btn_star)
            .setContentTitle("Título")
            .setContentText("Conteúdo de texto")
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setColor(Color.RED) // No Android 5.0
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setFullScreenIntent(pendingIntent, false) // Heads-up notification
            .setVisibility(NotificationCompat.VISIBILITY_PRIVATE)   // Privada se estiver na tela de bloqueio

        val notificationManager = NotificationManagerCompat.from(this)
        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(77, mBuilder.build())

    }

    fun callBigTextNotification(v: View) {

        mBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Título")
            .setContentText("Conteúdo de texto")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("Much longer text that cannot fit one line...Much longer text that cannot fit one line...Much longer text that cannot fit one line...Much longer text that cannot fit one line...")
            )
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        val notificationManager = NotificationManagerCompat.from(this)
        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(77, mBuilder.build())
    }

    fun callActionNotification(v: View) {

        mBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Título")
            .setContentText("Conteúdo de texto")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .addAction(R.drawable.ic_acao_pause, "Pause", pendingIntent)
            .addAction(R.drawable.ic_acao_play, "Play", pendingIntent)

        val notificationManager = NotificationManagerCompat.from(this)
        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(77, mBuilder.build())
    }

    fun cancel(context: Context, id: Int) {
        val nm = NotificationManagerCompat.from(context)
        nm.cancel(id)
    }

    fun cancelAll(context: Context) {
        val nm = NotificationManagerCompat.from(context)
        nm.cancelAll()
    }
}
