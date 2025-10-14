package eu.tutorials.koperasi_simpan_pinjam.workers

//Ini yang nantinya menampilkan notif di HP
import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import eu.tutorials.koperasi_simpan_pinjam.utils.JATUH_TEMPO_CHANNEL_ID
import eu.tutorials.koperasi_simpan_pinjam.utils.showNotification

class JatuhTempoWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    override fun doWork(): Result {
        // Ambil data yang dikirimkan saat menjadwalkan
        val jumlahTagihan = inputData.getString("JUMLAH_TAGIHAN") ?: "tagihan Anda"
        val tanggalJatuhTempo = inputData.getString("TANGGAL_JATUH_TEMPO") ?: ""

        // Tampilkan notifikasi
        showNotification(
            applicationContext,
            channelId = JATUH_TEMPO_CHANNEL_ID,
            notificationId = 1, // Beri ID unik untuk setiap notifikasi
            title = "Pengingat Jatuh Tempo",
            content = "Cicilan sebesar $jumlahTagihan akan jatuh tempo pada $tanggalJatuhTempo."
        )

        // Tandai pekerjaan berhasil
        return Result.success()
    }
}