package eu.tutorials.koperasi_simpan_pinjam.pages

import android.Manifest
import androidx.compose.foundation.Image
import coil.compose.AsyncImage
import androidx.compose.ui.draw.clip
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.OpenableColumns
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCard
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import eu.tutorials.koperasi_simpan_pinjam.ui.theme.KoperasiSimpanPinjamTheme
import kotlinx.coroutines.launch
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.core.content.FileProvider
import java.io.File

// Data class untuk item di drawer
data class DrawerItem(val title: String, val icon: @Composable () -> Unit, val route: String)
// Data class untuk item di bottom bar
data class BottomBarItem(val label: String, val icon: ImageVector, val route: String)

///HALAMAN HOMEPAGE KONTEN NASABAH - Theo & John
@Composable
fun HomePage() {
    //data contoh, sebelum masuk DB
    val pinjamanNasabah = PinjamanAktif(
        pokok = 5000000.0,
        bunga = 50000.0,
        totalCicilanPerBulan = 550000.0,
        sisaAngsuran = 9,
        totalAngsuran = 10
    )
    //supaya bisa scroll pakai lazycolumn
    LazyColumn (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        item {
            Text(
                text = "Selamat Datang, Nasabah!",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        item {
            // Bagian untuk menampilkan saldo simpanan nasabah (fitur tambahan)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = "Saldo Simpanan",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF6F6F6))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Simpanan Pokok: Rp 1.000.000")
                        Text("Simpanan Wajib: Rp 500.000")
                        Text("Simpanan Sukarela: Rp 750.000")
                        Divider(modifier = Modifier.padding(vertical = 8.dp))
                        Text(
                            "Total Saldo: Rp 2.250.000",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        item {
            //panggil composablekhusus untuk menampilkan kartu pinjaman
            KartuPinjamanAktif(dataPinjaman = pinjamanNasabah)
        }
        item {
            Spacer(
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "Menu Cepat",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ){
                Button(onClick = {/*TODO: Navigasi ke halaman pinjaman buat bayar*/}, modifier = Modifier.weight(1f)) {
                    Icon(Icons.Default.Payment, contentDescription = null, modifier = Modifier.padding(8.dp))
                    Text("Bayar Cicilan")
                }
                OutlinedButton(onClick = {/*TODO: navigasi ke halaman pinjam*/}, modifier = Modifier.weight(1f)) {
                    Icon(Icons.Default.AddCard, contentDescription = null, modifier = Modifier.padding(end=8.dp))
                    Text("Ajukan Pinjaman")
                }
            }
        }
    }
}

///HALAMAN SIMPANAN KONTEN NASABAH - Theo & John
@Composable
fun SimpananPage() {
    //hanya data contoh sebelum masok ke DB
    val saldoSaatIni = 1500000.0
    val daftarTransaksi = listOf(
        TransaksiSimpanan("S001", "05 Okt 2025", "Simpanan Wajib", 100000.0, TipeTransaksi.KREDIT),
        TransaksiSimpanan("S002", "01 Okt 2025", "Tarik Tunai", 250000.0, TipeTransaksi.DEBIT),
        TransaksiSimpanan("S003", "15 Sep 2025", "Simpanan Sukarela", 50000.0, TipeTransaksi.KREDIT),
        TransaksiSimpanan("S004", "05 Sep 2025", "Simpanan Wajib", 100000.0, TipeTransaksi.KREDIT),
        TransaksiSimpanan("S005", "20 Ags 2025", "Biaya Administrasi", 5000.0, TipeTransaksi.DEBIT),
        TransaksiSimpanan("S006", "05 Ags 2025", "Simpanan Wajib", 100000.0, TipeTransaksi.KREDIT)
    )

    //saldo simpanan pokok, wajib, sukarela
    val saldoPokok = 1000000.0
    val saldoWajib = 300000.0
    val saldoSukarela = 200000.0

    LazyColumn (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
        item{
            Text(
                text = "Bagian Theo disini",
                style = MaterialTheme.typography.bodyLarge
            )
        }

        // kartu 3 jenis simpanan
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                CardSimpananDetail("Simpanan Pokok", saldoPokok)
                CardSimpananDetail("Simpanan Wajib", saldoWajib)
                CardSimpananDetail("Simpanan Sukarela", saldoSukarela)
            }
        }

        //bagian kartu saldo total
        item {
            KartuSaldoTotal(saldo = saldoSaatIni)
        }

        //bagian judul untuk daftar mutasi
        item {
            Text(
                text = "Mutasi Rekening",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top=8.dp)
            )
        }
        //bagian daftar transaksi
        items(daftarTransaksi){transaksi ->
            ItemTransaksi(transaksi=transaksi)
        }
    }
}

// Tambahan komponen baru untuk fitur 3
@Composable
fun CardSimpananDetail(jenis: String, saldo: Double) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color.LightGray)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(jenis, fontWeight = FontWeight.SemiBold)
            Text("Rp ${saldo.toFormattedString()}", fontWeight = FontWeight.Bold)
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

    //launcher buat minta izin penyimpanan
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if(isGranted){
            //klo ijin dikasih, langsung buka galeri
            imagePickerLauncher.launch("image/*")
        } else{
            //kalau ditolak, tampilkan toast
            Toast.makeText(context, "Izin akses media ditolak", Toast.LENGTH_SHORT).show()
        }
    }

    //untuk modal
    if (isSheetOpen) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = { isSheetOpen = false }
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Pilih Sumber Gambar", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(bottom = 16.dp))
                //opsi kamera
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            //cek izin kamera
                            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                                tempUri?.let { uri ->
                                    cameraLauncher.launch(uri)
                                } ?: Toast.makeText(context, "File sementara belum siap. Coba lagi nanti.", Toast.LENGTH_SHORT).show()
                            } else {
                                cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                            }
                        }
                        .padding(vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.PhotoCamera, contentDescription = "Kamera")
                    Spacer(modifier = Modifier.width(16.dp))
                    Text("Ambil Foto dari Kamera", fontSize = 18.sp)
                }
                //opsi galeri
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            val permissionToRequest = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) Manifest.permission.READ_MEDIA_IMAGES else Manifest.permission.READ_EXTERNAL_STORAGE
                            //cek izin galeri
                            if (ContextCompat.checkSelfPermission(context, permissionToRequest) == PackageManager.PERMISSION_GRANTED) {
                                imagePickerLauncher.launch("image/*")
                            } else {
                                galleryPermissionLauncher.launch(permissionToRequest)
                            }
                        }
                        .padding(vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.PhotoLibrary, contentDescription = "Galeri")
                    Spacer(modifier = Modifier.width(16.dp))
                    Text("Pilih dari Galeri", fontSize = 18.sp)
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
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                OutlinedTextField(
                    value = jumlahPinjamanBaru,
                    onValueChange = { jumlahPinjamanBaru = it },
                    label = { Text("Jumlah Pinjaman (Rp)") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text("Tenor: ${tenorPinjaman.toInt()} bulan")

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
                fontWeight = FontWeight.Bold
            )
        }

        items(daftarPengajuan) { pengajuan ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                border = BorderStroke(1.dp, Color.LightGray),
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("Tanggal: ${pengajuan.tanggal}", color = Color.Gray)
                        Text("Jumlah: Rp ${pengajuan.jumlah.toFormattedString()}")
                        Text("Tenor: ${pengajuan.tenor} bulan")
                    }
                    Text(
                        pengajuan.status,
                        color = when (pengajuan.status) {
                            "Disetujui" -> Color(0xFF2E7D32)
                            "Ditolak" -> Color.Red
                            else -> Color(0xFFFFA000)
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
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp) //jarak antar item
    ){
        //laporan bulanan
        item {
            LaporanBulananSection(
                totalSimpanan = 1500000.0,
                totalPinjaman = 5000000.0,
                totalAngsuranBulanIni = 500000.0
            )
        }

        //bagian daftar histori pembayaran
        item {
            Text(
                text = "Histori Pembayaran Angsuran",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
        //contoh data aja
        val daftarAngsuran = listOf(
            Angsuran("A001", "10 Okt 2025", 500000.0, "Angsuran 3", "Lunas"),
            Angsuran("A002", "11 Sep 2025", 500000.0, "Angsuran 2", "Lunas"),
            Angsuran("A003", "14 Ags 2025", 500000.0, "Angsuran 1", "Lunas"),
            Angsuran("A004", "11 Jul 2025", 250000.0, "Bayar Denda", "Lunas"),
        )
        //nampilin tiap item histori dengan items()
        items(daftarAngsuran){  angsuran->
            HistoriAngsuranItem(angsuran=angsuran)
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
fun ProfilPage() {
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6F6F6))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // foto profil
        Card(
            shape = CircleShape,
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .border(2.dp, Color.Gray, CircleShape)
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
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = "Ubah Foto Profil")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // detail profil
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Detail Pengguna",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                ProfilInfoRow(label = "Nama Lengkap", value = dataProfil.nama)
                ProfilInfoRow(label = "ID Anggota", value = dataProfil.idAnggota)
                ProfilInfoRow(label = "Tanggal Gabung", value = dataProfil.tanggalGabung)
                ProfilInfoRow(label = "Status Keanggotaan", value = dataProfil.statusKeanggotaan)
                ProfilInfoRow(label = "Total Poin", value = "${dataProfil.poin} poin")
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
            color = Color.DarkGray
        )
        Text(
            text = value,
            fontSize = 16.sp,
            color = Color.Black
        )
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashBoard(navController: NavHostController, modifier: Modifier = Modifier) {
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
    var currentTitle by remember { mutableStateOf("Home") } // Nilai awal "Home"

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
                title = { Text(currentTitle) },
                navigationIcon = {
                    IconButton(onClick = { scope.launch { drawerState.open() } }) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }
                }
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
            composable("home") { HomePage() }
            composable("simpanan") { SimpananPage() }
            composable("pinjaman") { PinjamanPage() }
            composable("histori") { HistoriPage() }
            composable("profil") { ProfilPage() }
        }
    }
}

///SEMUA BANTUAN DAN KOMPONEN-KOMPONEN TIAP HALAMAN NASABAH:
//UNTUK BAGIAN TAB HOME
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
//composable kartu saldo total
@Composable
fun KartuSaldoTotal(saldo: Double){
    Card (
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Total Saldo Simpanan Anda",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
            Text(
                text = "Rp ${saldo.toFormattedString()}",
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
//composable tiap transaksi
@Composable
fun ItemTransaksi(transaksi: TransaksiSimpanan){
    val (icon, color) = when (transaksi.tipe) {
        TipeTransaksi.KREDIT -> Icons.Default.ArrowUpward to Color(0xFF008000) //hijau kalo uang masuk
        TipeTransaksi.DEBIT -> Icons.Default.ArrowDownward to Color.Red //merah kalo uang keluar
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
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = transaksi.tanggal,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
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
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
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
                    color = Color.Red,
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
                                MaterialTheme.colorScheme.primary,
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
                            overflow = TextOverflow.Ellipsis
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
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, Color.LightGray)
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
data class Angsuran(
    val id: String,
    val tanggal: String,
    val jumlah: Double,
    val keterangan: String,
    val status: String  //"Lunas" atau "JatuhTempo"
)
//composable bagian laporan bulanan
@Composable
fun LaporanBulananSection(
    totalSimpanan: Double,
    totalPinjaman: Double,
    totalAngsuranBulanIni: Double
){
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp)
    ){
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ){
            Text(
                text = "Laporan Bulan Ini",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            InfoRow(label = "Total Simpanan", value = "Rp ${totalSimpanan.toFormattedString()}")
            InfoRow(label = "Sisa Pinjaman Aktif", value = "Rp ${totalPinjaman.toFormattedString()}")
            InfoRow(label = "Angsuran Dibayar", value = "Rp ${totalAngsuranBulanIni.toFormattedString()}")
        }
    }
}
//composable tiap item di daftar histori
@Composable
fun HistoriAngsuranItem(angsuran: Angsuran){
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, Color.LightGray)
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Column(
                modifier = Modifier.weight(1f)
            ){
                Text(
                    text = angsuran.keterangan,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = "Tanggal: ${angsuran.tanggal}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
            Column(horizontalAlignment = Alignment.End){
                Text(
                    text = "Rp ${angsuran.jumlah.toFormattedString()}",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 16.sp
                )
                Text(
                    text = angsuran.status,
                    style = MaterialTheme.typography.bodySmall,
                    color = if (angsuran.status == "Lunas") Color(0xFF008000) else Color.Red,
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(
                            if (angsuran.status == "Lunas") Color(0xFFE8F5E9) else Color(
                                0xFFFFEBEE
                            )
                        )
                        .padding(horizontal = 6.dp, vertical = 2.dp)
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
        Text(text = label, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
        Text(text = value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
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
