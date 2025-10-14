package eu.tutorials.koperasi_simpan_pinjam.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import eu.tutorials.koperasi_simpan_pinjam.R

const val JATUH_TEMPO_CHANNEL_ID = "JatuhTempoChannel"
const val PENGAJUAN_CHANNEL_ID = "PengajuanChannel"

//untuk membuat channel notifikasi
fun createNotificationChannels(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val jatuhTempoChannel = NotificationChannel(
            JATUH_TEMPO_CHANNEL_ID,
            "Notifikasi Jatuh Tempo",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Notifikasi untuk pengingat pembayaran cicilan"
        }

        val pengajuanChannel = NotificationChannel(
            PENGAJUAN_CHANNEL_ID,
            "Status Pengajuan User",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "Notifikasi terkait status pengajuan pinjaman"
        }

        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(jatuhTempoChannel)
        notificationManager.createNotificationChannel(pengajuanChannel)
    }
}

//untuk menampilkan notif
fun showNotification(
    context: Context,
    channelId: String,
    notificationId: Int,
    title: String,
    content: String
) {
    val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    val builder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.drawable.logo_kop)
        .setContentTitle(title)
        .setContentText(content)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setAutoCancel(true)

    notificationManager.notify(notificationId, builder.build())
}