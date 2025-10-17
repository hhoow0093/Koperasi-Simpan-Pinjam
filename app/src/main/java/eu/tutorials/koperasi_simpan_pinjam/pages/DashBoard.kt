// File: pages/Pages.kt (atau sesuaikan nama filenya)
package eu.tutorials.koperasi_simpan_pinjam.pages

import android.Manifest
import android.R
import android.content.Context
import android.content.pm.PackageManager
import android.icu.text.NumberFormat
import android.net.Uri
import android.os.Build
import android.provider.OpenableColumns
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import eu.tutorials.koperasi_simpan_pinjam.ui.theme.DeepBlue
import eu.tutorials.koperasi_simpan_pinjam.ui.theme.KoperasiSimpanPinjamTheme
import eu.tutorials.koperasi_simpan_pinjam.ui.theme.Pink80
import eu.tutorials.koperasi_simpan_pinjam.ui.theme.PurpleGrey80
import eu.tutorials.koperasi_simpan_pinjam.utils.PENGAJUAN_CHANNEL_ID
import eu.tutorials.koperasi_simpan_pinjam.utils.showNotification
import kotlinx.coroutines.launch
import java.io.File
import java.text.DecimalFormat
import kotlin.math.absoluteValue
import kotlin.text.format

// Data class untuk item di bottom bar
data class BottomBarItem(val label: String, val icon: ImageVector, val route: String)

///HALAMAN HOMEPAGE KONTEN NASABAH - Theo & John
@Composable
fun HomePage(navController: NavHostController) {
    val namaNasabah = "Nama" //nanti ambil dr database
    val totalSaldoSimpanan = 2250000.0 //nanti ambil dari database
    val pinjamanNasabah = PinjamanAktif(
        pokok = 5000000.0,
        bunga = 50000.0,
        totalCicilanPerBulan = 550000.0,
        sisaAngsuran = 9,
        totalAngsuran = 10
    )

    //pakai lazycolumn
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f)),
        contentPadding = PaddingValues(16.dp), //padding smua konten
        verticalArrangement = Arrangement.spacedBy(20.dp) //jarak antar itemnya
    ){
        //kartu saldo utama
        item {
            KartuSaldoUtama(
                namaNasabah = namaNasabah,
                totalSaldo = totalSaldoSimpanan
            )
        }
        //rincian pinjaman aktif (kalo ada)
        item {
            Text(
                text = "Pinjaman Aktif",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            KartuPinjamanAktif(dataPinjaman = pinjamanNasabah)
        }
        //menu cepat
        item {
            Text(
                text = "Menu Cepat",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                //tombol Bayar Cicilan
                Button(
                    onClick = {
                        navController.navigate("pinjaman") {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                ) {
                    Icon(Icons.Default.Payment, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Bayar")
                }
                OutlinedButton(
                    onClick = {
                        navController.navigate("pinjaman") {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                ) {
                    Icon(Icons.Default.AddCard, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Ajukan")
                }
            }
        }
    }
}

///HALAMAN SIMPANAN KONTEN NASABAH - Theo & John
@Composable
fun SimpananPage() {
    //hanya data contoh sebelum masok ke DB
    val daftarTransaksi = listOf(
        TransaksiSimpanan("S001", "05 Okt 2025", "Simpanan Wajib", 100000.0, TipeTransaksi.KREDIT),
        TransaksiSimpanan("S002", "01 Okt 2025", "Tarik Tunai", 250000.0, TipeTransaksi.DEBIT),
        TransaksiSimpanan("S003", "15 Sep 2025", "Simpanan Sukarela", 50000.0, TipeTransaksi.KREDIT),
        TransaksiSimpanan("S004", "05 Sep 2025", "Simpanan Wajib", 100000.0, TipeTransaksi.KREDIT),
        TransaksiSimpanan("S005", "20 Ags 2025", "Biaya Administrasi", 5000.0, TipeTransaksi.DEBIT),
        TransaksiSimpanan("S006", "05 Ags 2025", "Simpanan Wajib", 100000.0, TipeTransaksi.KREDIT)
    )

    val daftarJenisSimpanan = remember {
        listOf(
            JenisSimpanan("Simpanan Pokok", 1000000.0, Icons.Filled.Shield, Color(0xFF1D336A)),
            JenisSimpanan("Simpanan Wajib", 500000.0, Icons.Filled.Event, Color(0xFF625b71)),
            JenisSimpanan("Simpanan Sukarela", 750000.0, Icons.Filled.Favorite, Color(0xFF7D5260))
        )
    }
    val totalSaldo = daftarJenisSimpanan.sumOf{it.saldo}
    val pagerState = rememberPagerState(pageCount = {daftarJenisSimpanan.size})
    LazyColumn (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
        //judul halaman
        item {
            Text(
                text = "Rincian Simpanan",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp)
            )
        }

        //carousel 3 jenis simpanan
        item{
            HorizontalPager(
                state = pagerState,
                contentPadding = PaddingValues(horizontal = 32.dp), //kasih padding biar kartunya bisa keliatan
                pageSpacing = 16.dp
            ) { page ->
                //carousel 3 jenis simpanan
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.7f)
                        .graphicsLayer { //animasi saat digeser
                            val pageOffset = (
                                    (pagerState.currentPage - page) + pagerState
                                        .currentPageOffsetFraction
                                    ).absoluteValue
                            alpha = lerp(
                                start = 0.5f,
                                stop = 1f,
                                fraction = 1f - pageOffset.coerceIn(0f, 1f)
                            )
                            scaleY = lerp(
                                start = 0.8f,
                                stop = 1f,
                                fraction = 1f - pageOffset.coerceIn(0f, 1f)
                            )
                        },
                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.cardElevation(8.dp)
                )
                {
                    val simpanan = daftarJenisSimpanan[page]
                    //box gradasi
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        simpanan.warna,
                                        simpanan.warna.copy(alpha = 0.7f)
                                    )
                                )
                            )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(20.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    simpanan.ikon,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(32.dp)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = simpanan.nama,
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.White
                                )
                            }
                            Column(
                                horizontalAlignment = Alignment.End,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    "Saldo",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = Color.White.copy(alpha = 0.8f)
                                )
                                Text(
                                    text = "Rp ${simpanan.saldo.toFormattedString()}",
                                    style = MaterialTheme.typography.headlineMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }

        //bagian judul untuk daftar mutasi
        item {
            Text(
                text = "Mutasi Rekening",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }

        //mutasi
        items(daftarTransaksi) { transaksi ->
            Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                ItemTransaksi(transaksi = transaksi)
            }
        }
        //kasih spacer dibwh
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

////HALAMAN PINJAMAN KONTEN NASABAH - Theo & John
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PinjamanPage() {
    val context = LocalContext.current


    var namaFileDipilih by remember { mutableStateOf<String?>(null) }
    var uriDipilih by remember { mutableStateOf<Uri?>(null) }
    val sheetState = rememberModalBottomSheetState()
    var isSheetOpen by remember{mutableStateOf(false)}
    var tempUri by remember { mutableStateOf<Uri?>(null) }

    LaunchedEffect(Unit) {
        try {
            val tempFile = File.createTempFile("camera_", ".jpg", context.cacheDir).apply {
                createNewFile()
            }
            tempUri = FileProvider.getUriForFile(
                context.applicationContext,
                "${context.packageName}.provider",
                tempFile
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "Gagal membuat file sementara untuk kamera.", Toast.LENGTH_LONG).show()
        }
    }

    //ngatur visibility sheets
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            isSheetOpen = false
            uriDipilih = uri
            namaFileDipilih = getFileName(context, uri)
        }
    }

    //launcher kamera
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { isSuccess: Boolean ->
        if(isSuccess && tempUri != null){
            isSheetOpen = false
            uriDipilih = tempUri
            namaFileDipilih = "photo_${System.currentTimeMillis()}.jpg"
        } else if (!isSuccess) {
            //klo foot ga diambil
            Toast.makeText(context, "Foto tidak diambil.", Toast.LENGTH_SHORT).show()
        } else {
            //tempUri belum siap
            Toast.makeText(context, "File sementara belum siap. Coba lagi nanti.", Toast.LENGTH_SHORT).show()
        }
    }

    //launcher untuk ijin
    val galleryPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if(isGranted){
            imagePickerLauncher.launch("image/*")
        } else{
            Toast.makeText(context, "Izin akses galeri ditolak", Toast.LENGTH_SHORT).show()
        }
    }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if(isGranted){
            //pastiin tempUri sudah siap
            tempUri?.let { uri ->
                cameraLauncher.launch(uri)
            } ?: Toast.makeText(context, "File sementara belum siap. Coba lagi nanti.", Toast.LENGTH_SHORT).show()
        } else{
            Toast.makeText(context, "Izin akses kamera ditolak", Toast.LENGTH_SHORT).show()
        }
    }

    val permissionToRequest = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
        Manifest.permission.READ_MEDIA_IMAGES
    } else{
        Manifest.permission.READ_EXTERNAL_STORAGE
    }

    //untuk modal
    if (isSheetOpen) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = { isSheetOpen = false }
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    "Pilih Sumber Gambar",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 16.dp),
                    color = MaterialTheme.colorScheme.onSurface
                )

                //opsi kamera
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            //cek izin kamera
                            if (ContextCompat.checkSelfPermission(
                                    context,
                                    Manifest.permission.CAMERA
                                ) == PackageManager.PERMISSION_GRANTED
                            ) {
                                tempUri?.let { uri ->
                                    cameraLauncher.launch(uri)
                                } ?: Toast.makeText(
                                    context,
                                    "File sementara belum siap. Coba lagi nanti.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                            }
                        }
                        .padding(vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.PhotoCamera,
                        contentDescription = "Kamera",
                        tint = MaterialTheme.colorScheme.primary // Gunakan warna primary dari theme
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        "Ambil Foto dari Kamera",
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                //opsi galeri
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            val permissionToRequest =
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) Manifest.permission.READ_MEDIA_IMAGES else Manifest.permission.READ_EXTERNAL_STORAGE
                            //cek izin galeri
                            if (ContextCompat.checkSelfPermission(
                                    context,
                                    permissionToRequest
                                ) == PackageManager.PERMISSION_GRANTED
                            ) {
                                imagePickerLauncher.launch("image/*")
                            } else {
                                galleryPermissionLauncher.launch(permissionToRequest)
                            }
                        }
                        .padding(vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.PhotoLibrary,
                        contentDescription = "Galeri",
                        tint = MaterialTheme.colorScheme.primary // Gunakan warna primary dari theme
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        "Pilih dari Galeri",
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }

    //data contoh aja sebelum masuk DB
    val tagihanSaatIni = DetailTagihan(
        nomorTagihan = "INV-2025-10-123",
        jatuhTempo = "25 Okt 2025",
        jumlahTagihan = 550000.0,
        angsuranKe = 4,
        totalAngsuran = 10
    )
    val detailPinjaman = RincianPinjaman(
        totalPinjaman = 5000000.0,
        tenor = 10,
        cicilanPerBulan = 550000.0
    )

    //pengajuan pinjaman bar + status pinjaman
    var jumlahPinjamanBaru by remember { mutableStateOf("") }
    var tenorPinjaman by remember { mutableStateOf(6f) }
    val daftarPengajuan = remember {
        mutableStateListOf(
            PengajuanPinjaman("PNJ001", "10 Okt 2025", 3000000.0, 6, "Disetujui"),
            PengajuanPinjaman("PNJ002", "02 Okt 2025", 7000000.0, 12, "Proses"),
            PengajuanPinjaman("PNJ003", "25 Sep 2025", 10000000.0, 24, "Ditolak")
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        //kart tagihan
        item {
            KartuTagihan(
                tagihan = tagihanSaatIni,
                namaFile = namaFileDipilih,
                onUnggahBuktiClick = {
                    isSheetOpen = true
                },
                onKirimBuktiClick = {
                    uriDipilih?.let { uri ->
                        println("Mulai mengunggah file: ${getFileName(context, uri)}")
                    }
                },
                onBatalPilihFileClick = {
                    uriDipilih = null
                    namaFileDipilih = null
                }
            )
        }
        //rincian pinjaman
        item {
            KartuDetailPinjaman(detail = detailPinjaman)
        }
        //pengajuan pinjaman baru
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = "Ajukan Pinjaman Baru",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp),
                    color = MaterialTheme.colorScheme.onBackground
                )
                OutlinedTextField(
                    value = jumlahPinjamanBaru,
                    onValueChange = { jumlahPinjamanBaru = it },
                    label = { Text("Jumlah Pinjaman (Rp)") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    "Tenor: ${tenorPinjaman.toInt()} bulan",
                    color = MaterialTheme.colorScheme.onSurface
                )
                Slider(
                    value = tenorPinjaman,
                    onValueChange = { tenorPinjaman = it },
                    valueRange = 3f..24f,
                    steps = 7
                )
                Button(
                    onClick = {
                        if (jumlahPinjamanBaru.isNotEmpty()) {
                            daftarPengajuan.add(
                                PengajuanPinjaman(
                                    id = "PNJ" + (daftarPengajuan.size + 1)
                                        .toString().padStart(3, '0'),
                                    tanggal = "13 Okt 2025",
                                    jumlah = jumlahPinjamanBaru.toDouble(),
                                    tenor = tenorPinjaman.toInt(),
                                    status = "Proses"
                                )
                            )
                            val formattedJumlah = NumberFormat.getCurrencyInstance(
                                java.util.Locale(
                                    "in",
                                    "ID"
                                )
                            ).format(jumlahPinjamanBaru.toDouble())
                            showNotification(
                                context = context,
                                channelId = PENGAJUAN_CHANNEL_ID,
                                notificationId = System.currentTimeMillis().toInt(), // ID unik agar notifikasi tidak menimpa satu sama lain
                                title = "Pengajuan Pinjaman Terkirim",
                                content = "Pengajuan Anda sebesar $formattedJumlah sedang dalam proses review oleh tim kami."
                            )

                            jumlahPinjamanBaru = ""
                            tenorPinjaman = 6f
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Add, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Ajukan Sekarang")
                }
            }
        }
        //daftar status pinjaman
        item {
            Text(
                text = "Status Pengajuan Pinjaman",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        items(daftarPengajuan) { pengajuan ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline), // Gunakan outline dari theme
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface // Gunakan warna dari theme
                )
            ) {
                Row(
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            "Tanggal: ${pengajuan.tanggal}",
                            color = MaterialTheme.colorScheme.onSurfaceVariant // Gunakan warna yang lebih pudar
                        )
                        Text(
                            "Jumlah: Rp ${pengajuan.jumlah.toFormattedString()}",
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            "Tenor: ${pengajuan.tenor} bulan",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Text(
                        pengajuan.status,
                        color = when (pengajuan.status) {
                            "Disetujui" -> Color(0xFF2E7D32) // Hijau khusus untuk disetujui
                            "Ditolak" -> MaterialTheme.colorScheme.error // Gunakan warna error untuk ditolak
                            else -> Color(0xFFFFA000) // Warna khusus untuk proses
                        },
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

//data class tambahan pengajuan
data class PengajuanPinjaman(
    val id: String,
    val tanggal: String,
    val jumlah: Double,
    val tenor: Int,
    val status: String
)

///HALAMAN HISTORI KONTEN NASABAH - John
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoriPage() {
    // contoh sebelum msk db
    val historiSimpanan = remember {
        listOf(
            ItemHistori("S01", "05 Okt 2025", 100000.0, "Simpanan Wajib", TipeHistori.SIMPANAN_MASUK),
            ItemHistori("S02", "01 Okt 2025", 250000.0, "Tarik Tunai", TipeHistori.SIMPANAN_KELUAR),
            ItemHistori("S03", "15 Sep 2025", 50000.0, "Simpanan Sukarela", TipeHistori.SIMPANAN_MASUK),
        )
    }
    val historiPinjaman = remember {
        listOf(
            ItemHistori("P01", "10 Okt 2025", 500000.0, "Angsuran ke-3", TipeHistori.BAYAR_ANGSURAN),
            ItemHistori("P02", "11 Sep 2025", 500000.0, "Angsuran ke-2", TipeHistori.BAYAR_ANGSURAN),
            ItemHistori("D01", "11 Jul 2025", 25000.0, "Denda Keterlambatan", TipeHistori.BAYAR_DENDA)
        )
    }

    //kontrol tab aktif
    var tabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Semua", "Simpanan", "Pinjaman")

    Column(modifier = Modifier.fillMaxSize()) {

        //judul
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "Histori Transaksi",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))

            TabRow(
                selectedTabIndex = tabIndex,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = tabIndex == index,
                        onClick = { tabIndex = index },
                        text = { Text(text = title) }
                    )
                }
            }
        }

        //menentukan daftar mana yg tampil
        val daftarTampil = when (tabIndex) {
            1 -> historiSimpanan
            2 -> historiPinjaman
            //gabungkan kedua list dan urutkan berdasarkan tanggal
            else -> (historiSimpanan + historiPinjaman).sortedByDescending { it.tanggal }
        }
        if (daftarTampil.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Tidak ada histori untuk ditampilkan.")
            }
        } else {
            // Jika ada data, tampilkan dalam LazyColumn format timeline.
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp)
            ) {
                itemsIndexed(daftarTampil) { index, item ->
                    HistoriTimelineItem(
                        item = item,
                        isLastItem = index == daftarTampil.lastIndex
                    )
                }
            }
        }
    }
}

///HALAMAN PROFIL KONTEN NASABAH - Theo
data class ProfilNasabah(
    val nama: String,
    val idAnggota: String,
    val tanggalGabung: String,
    val statusKeanggotaan: String,
    val poin: Int
)

//profil page
@Composable
fun ProfilPage(navController: NavHostController) {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? -> imageUri = uri }
    val dataProfil = remember {
        ProfilNasabah(
            nama = "Theo Aditya",
            idAnggota = "AGT-2301",
            tanggalGabung = "14 Oktober 2023",
            statusKeanggotaan = "Aktif",
            poin = 120
        )
    }

    //kontrol dialog konfirmasi logout
    var showLogoutDialog by remember { mutableStateOf(false) }

    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = {
                Text(text = "Konfirmasi Logout")
            },
            text = {
                Text(text = "Apakah Anda yakin ingin keluar dari akun Anda?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showLogoutDialog = false
                        navController.navigate("register") {
                            popUpTo(0) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                        println("Proses Logout dan navigasi ke halaman Register...")
                    }
                ) {
                    Text("Ya, Keluar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showLogoutDialog = false
                    }
                ) {
                    Text("Batal")
                }
            }
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Card(
                    shape = CircleShape,
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .border(2.dp, MaterialTheme.colorScheme.outline, CircleShape)
                ) {
                    AsyncImage(
                        model = imageUri ?: "https://via.placeholder.com/150",
                        contentDescription = "Foto Profil",
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { launcher.launch("image/*") }) {
                    Icon(Icons.Default.Upload, contentDescription = "Upload")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Ubah Foto Profil")
                }
            }
        }
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Detail Pengguna",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    ProfilInfoRow(label = "Nama Lengkap", value = dataProfil.nama)
                    ProfilInfoRow(label = "ID Anggota", value = dataProfil.idAnggota)
                    ProfilInfoRow(label = "Tanggal Gabung", value = dataProfil.tanggalGabung)
                    ProfilInfoRow(label = "Status Keanggotaan", value = dataProfil.statusKeanggotaan)
                    ProfilInfoRow(label = "Total Poin", value = "${dataProfil.poin} poin")
                }
            }
        }
        //tombol logout
        item {
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedButton(
                onClick = {
                    showLogoutDialog = true
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                ),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.error.copy(alpha = 0.5f))
            ) {
                Icon(Icons.Default.Logout, contentDescription = "Logout")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Logout")
            }
        }
    }
}

// komponen buat profilpage
@Composable
fun ProfilInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant // Gunakan warna yang lebih pudar
        )
        Text(
            text = value,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashBoard(navController: NavHostController, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Toast.makeText(context, "Izin notifikasi diberikan.", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Anda tidak akan menerima notifikasi penting.", Toast.LENGTH_LONG).show()
        }
    }

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    val dashboardNavController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val bottomItems = listOf(
        BottomBarItem("Home", Icons.Default.Home, "home"),
        BottomBarItem("Simpanan", Icons.Default.AccountBalanceWallet, "simpanan"),
        BottomBarItem("Pinjaman", Icons.Default.CreditCard, "pinjaman"),
        BottomBarItem("Histori", Icons.Default.History, "histori"),
        BottomBarItem("Profil", Icons.Default.Person, "profil")
    )
    // untuk simpan title
    var currentTitle by remember { mutableStateOf("Home") }
    // navigasi backstack
    val navBackStackEntry by dashboardNavController.currentBackStackEntryAsState()
    // update title
    LaunchedEffect(navBackStackEntry) {
        val currentRoute = navBackStackEntry?.destination?.route
        val newTitle = bottomItems.find { it.route == currentRoute }?.label ?: "Dashboard"
        currentTitle = newTitle
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(currentTitle, color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = DeepBlue,
                    titleContentColor = Color.White
                )
            )
        },
        bottomBar = {
            NavigationBar {
                val currentDestination = navBackStackEntry?.destination
                bottomItems.forEach { item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) },
                        selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                        onClick = {
                            dashboardNavController.navigate(item.route) {
                                popUpTo(dashboardNavController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = dashboardNavController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") { HomePage(navController = dashboardNavController) }
            composable("simpanan") { SimpananPage() }
            composable("pinjaman") { PinjamanPage() }
            composable("histori") { HistoriPage() }
            composable("profil") { ProfilPage(navController = navController) }
        }
    }
}

///SEMUA BANTUAN DAN KOMPONEN-KOMPONEN TIAP HALAMAN NASABAH:
//UNTUK BAGIAN TAB HOME
//untuk kartu saldo total
@Composable
fun KartuSaldoUtama(
    namaNasabah: String,
    totalSaldo: Double,
    modifier: Modifier = Modifier
) {
    var saldoTerlihat by remember { mutableStateOf(true) }

    val saldoAnimasi by animateFloatAsState(
        targetValue = if (saldoTerlihat) totalSaldo.toFloat() else 0f,
        animationSpec = tween(durationMillis = 1000),
        label = "SaldoAnimation"
    )
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp),//kartu lebih tinggi
        shape = RoundedCornerShape(24.dp),//sudut lebih melengkung
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
                        )
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                //sapaan
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Halo, $namaNasabah!",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )
                    IconButton(onClick = { saldoTerlihat = !saldoTerlihat }) {
                        Icon(
                            imageVector = if (saldoTerlihat) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                            contentDescription = "Sembunyikan/Tampilkan Saldo",
                            tint = Color.White.copy(alpha = 0.8f)
                        )
                    }
                }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Total Saldo Simpanan",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.8f)
                    )

                    if (saldoTerlihat) {
                        //format angka ribuan
                        val formatter = DecimalFormat("#,###")
                        Text(
                            text = "Rp ${
                                formatter.format(saldoAnimasi.toLong()).replace(',', '.')
                            }",
                            style = MaterialTheme.typography.displaySmall,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            maxLines = 1
                        )
                    } else {
                        //placeholder kalau saldo disembunyikan
                        Text(
                            text = "Rp ••••••••",
                            style = MaterialTheme.typography.displaySmall,
                            fontWeight = FontWeight.Bold,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }
                }
            }
        }
    }
}

//data class untuk Pinjaman aktif
data class PinjamanAktif(
    val pokok: Double,
    val bunga: Double,
    val totalCicilanPerBulan: Double,
    val sisaAngsuran: Int, //dalam bulan
    val totalAngsuran: Int
)

//composable kartupinjaman aktif
@Composable
fun KartuPinjamanAktif(dataPinjaman: PinjamanAktif){
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ){
            Text(
                text = "Rincian Pinjaman Aktif",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Divider(color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f))
            //biar consistent
            InfoPinjamanRow(label = "Sisa Pokok Pinjaman", value = "Rp ${dataPinjaman.pokok.toFormattedString()}")
            InfoPinjamanRow(label = "Bunga per Bulan", value = "Rp ${dataPinjaman.bunga.toFormattedString()}")
            Divider(color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.2f), thickness = 0.5.dp)
            InfoPinjamanRow(
                label = "Total Cicilan per Bulan",
                value = "Rp ${dataPinjaman.totalCicilanPerBulan.toFormattedString()}",
                isHighlight = true //biar lebih nonjol
            )
            //bagian sisa angsuran
            Column(modifier = Modifier.padding(top = 8.dp)) {
                Text(
                    text = "Sisa Angsuran",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                )
                Text(
                    text = "${dataPinjaman.sisaAngsuran} dari ${dataPinjaman.totalAngsuran} bulan",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                //progress bar visualisasi sisa angsuran (masih error deh ini huhu)
                LinearProgressIndicator(
                    progress = { (dataPinjaman.totalAngsuran - dataPinjaman.sisaAngsuran).toFloat() / dataPinjaman.totalAngsuran.toFloat() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp)
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                    strokeCap = ProgressIndicatorDefaults.LinearStrokeCap,
                )
            }
        }
    }
}

//composable untuk consistency
@Composable
fun InfoPinjamanRow(label: String, value: String, isHighlight: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = if (isHighlight) MaterialTheme.typography.bodyLarge else MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
        )
        Text(
            text = value,
            style = if (isHighlight) MaterialTheme.typography.titleMedium else MaterialTheme.typography.bodyLarge,
            fontWeight = if (isHighlight) FontWeight.Bold else FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

//UNTUK BAGIAN TAB SIMPANAN
enum class TipeTransaksi{
    KREDIT, DEBIT
}

data class TransaksiSimpanan(
    val id: String,
    val tanggal: String,
    val keterangan: String,
    val jumlah: Double,
    val tipe: TipeTransaksi //kredit(masuk) atau debit(keluar)
)

data class JenisSimpanan(
    val nama: String,
    val saldo: Double,
    val ikon: ImageVector,
    val warna: Color
)

//composable tiap transaksi
@Composable
fun ItemTransaksi(transaksi: TransaksiSimpanan){
    val (icon, color) = when (transaksi.tipe) {
        TipeTransaksi.KREDIT -> Icons.Default.ArrowUpward to MaterialTheme.colorScheme.primary // Gunakan warna primary untuk kredit
        TipeTransaksi.DEBIT -> Icons.Default.ArrowDownward to MaterialTheme.colorScheme.error // Gunakan warna error untuk debit
    }
    val prefix = if (transaksi.tipe == TipeTransaksi.KREDIT) "+ " else "- "
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(
            imageVector = icon,
            contentDescription = transaksi.tipe.name,
            tint = color,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(color.copy(alpha = 0.1f))
                .padding(8.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = transaksi.keterangan,
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = transaksi.tanggal,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant // Gunakan warna yang lebih pudar
            )
        }
        Text(
            text = "$prefix Rp ${transaksi.jumlah.toFormattedString()}",
            fontWeight = FontWeight.Bold,
            color = color,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

//UNTUK BAGIAN TAB PINJAMAN
//contoh data class tagihan dan detail pinjaman
data class DetailTagihan(
    val nomorTagihan: String,
    val jatuhTempo: String,
    val jumlahTagihan: Double,
    val angsuranKe: Int,
    val totalAngsuran: Int
)

data class RincianPinjaman(
    val totalPinjaman: Double,
    val tenor: Int, //dalam bulan
    val cicilanPerBulan: Double
)

//composable kartu tagihan harus dibayar
@Composable
fun KartuTagihan(
    tagihan: DetailTagihan,
    namaFile: String?,
    onUnggahBuktiClick: () -> Unit,
    onKirimBuktiClick: () -> Unit,
    onBatalPilihFileClick: () -> Unit
){
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer) // Gunakan warna dari theme
    ){
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ){
            //header kartu
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = "Tagihan Bulan Ini",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = "Angsuran ${tagihan.angsuranKe}/${tagihan.totalAngsuran}",
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier
                        .background(
                            MaterialTheme.colorScheme.primary,
                            RoundedCornerShape(50)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
            //jumlah tagihan
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Jumlah Tagihan",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                )
                Text(
                    text = "Rp ${tagihan.jumlahTagihan.toFormattedString()}",
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = "Jatuh tempo: ${tagihan.jatuhTempo}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error, // Gunakan warna error untuk jatuh tempo
                    fontWeight = FontWeight.SemiBold
                )
            }
            //tampilkan gambar setelah gambar dipilih
            AnimatedVisibility(visible=namaFile != null) {
                Column(
                    modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                ){
                    Text(
                        "File Terpilih:",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                1.dp,
                                MaterialTheme.colorScheme.primary, // Gunakan warna primary untuk border
                                RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        Text(
                            text = namaFile ?: "Tidak ada file",
                            modifier = Modifier.weight(1f),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        //icon tombol X untuk batal
                        IconButton(onClick = onBatalPilihFileClick, modifier = Modifier.size(24.dp)) {
                            Icon(Icons.Default.Close, contentDescription = "Batal Pilih File")
                        }
                    }
                }
            }
            //tombol aksi tagihan
            //klo belum ada file, tampilin tombol "unggah bukti"
            //klo sudah ada file, tampilin tombol "kirim bukti"
            Button(
                onClick = {
                    if (namaFile == null) {
                        onUnggahBuktiClick()
                    } else {
                        onKirimBuktiClick()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ) {
                val icon = if(namaFile == null) Icons.Default.Upload else Icons.Default.Send
                val text = if(namaFile == null) "Unggah Bukti Pembayaran" else "Kirim Bukti Sekarang"
                Icon(icon, contentDescription = null, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

//composable untuk dapatkan nama file dari uri
fun getFileName(context: Context, uri: Uri): String?{
    var fileName: String? = null
    //Query ke contentresolver untuk dapatkan nama
    context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
        if(cursor.moveToFirst()){
            val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if(nameIndex != -1){
                fileName = cursor.getString(nameIndex)
            }
        }
    }
    return fileName
}

//composable kartu detail keseluruhan pinjaman
@Composable
fun KartuDetailPinjaman(detail: RincianPinjaman){
    Column {
        Text(
            text = "Rincian Pinjaman Anda",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp),
            color = MaterialTheme.colorScheme.onBackground
        )
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline), // Gunakan outline dari theme
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface // Gunakan warna dari theme
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                //pakai template InfoRow yang ada di Histori
                InfoRow(label = "Total Pinjaman", value = "Rp ${detail.totalPinjaman.toFormattedString()}")
                InfoRow(label = "Tenor Pinjaman", value = "${detail.tenor} Bulan")
                InfoRow(label = "Cicilan per Bulan", value = "Rp ${detail.cicilanPerBulan.toFormattedString()}")
            }
        }
    }
}

//UNTUK BAGIAN TAB HISTORI
//data class untuk menampung data
enum class TipeHistori{
    SIMPANAN_MASUK,
    SIMPANAN_KELUAR,
    BAYAR_ANGSURAN,
    BAYAR_DENDA
}
data class ItemHistori(
    val id: String,
    val tanggal: String,
    val jumlah: Double,
    val keterangan: String,
    val tipe: TipeHistori
)

//buat timeline histori
@Composable
fun HistoriTimelineItem(
    item: ItemHistori,
    isLastItem: Boolean
){
    val (ikon, warna) = when(item.tipe){
        TipeHistori.SIMPANAN_MASUK -> Icons.Filled.AccountBalanceWallet to MaterialTheme.colorScheme.primary
        TipeHistori.SIMPANAN_KELUAR -> Icons.Filled.Upload to MaterialTheme.colorScheme.tertiary
        TipeHistori.BAYAR_ANGSURAN -> Icons.Filled.ReceiptLong to Color(0xFF2E7D32)
        TipeHistori.BAYAR_DENDA -> Icons.Filled.Warning to MaterialTheme.colorScheme.error
    }
    val prefix = if(item.tipe == TipeHistori.SIMPANAN_KELUAR) "- " else "+ "
    Row(modifier = Modifier.fillMaxWidth()){
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            //ikon
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(warna.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ){
                Icon(
                    imageVector = ikon,
                    contentDescription = item.keterangan,
                    tint = warna,
                    modifier = Modifier.size(24.dp)
                )
            }
            //garis
            if(!isLastItem){
                Canvas(modifier = Modifier
                    .weight(1f)
                    .width(2.dp)){
                    drawLine(
                        color = Color.LightGray,
                        start = Offset(size.width / 2, 0f),
                        end = Offset(size.width / 2, size.height),
                        strokeWidth = 2.dp.toPx()
                    )
                }
            }
        }
        Card(
            modifier = Modifier
                .padding(bottom = 16.dp, end = 8.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme
                .colorScheme.surface),
            elevation = CardDefaults.cardElevation(2.dp)
        ){
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = item.keterangan,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = item.tanggal,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Text(
                    text= "$prefix${item.jumlah.toFormattedString()}",
                    fontWeight = FontWeight.SemiBold,
                    color = warna,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}

//buat helper di Composable dan Formatting
@Composable
fun InfoRow(label: String, value: String){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant // Gunakan warna yang lebih pudar
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

fun Double.toFormattedString(): String{
    return "%,.0f".format(this).replace(',', '.')
}

//UNTUK BAGIAN TAB PROFIL
@Preview(showBackground = true, name = "Dashboard Page", showSystemUi = true, device = Devices.PIXEL_5)
@Composable
fun DashboardPreview() {
    KoperasiSimpanPinjamTheme {
        DashBoard(navController = rememberNavController())
    }
}