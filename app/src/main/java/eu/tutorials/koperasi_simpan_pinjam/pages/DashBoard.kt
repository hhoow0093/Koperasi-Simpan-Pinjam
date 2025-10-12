package eu.tutorials.koperasi_simpan_pinjam.pages

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.collection.objectListOf
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import eu.tutorials.koperasi_simpan_pinjam.ui.theme.KoperasiSimpanPinjamTheme
import kotlinx.coroutines.launch
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
            Text(
                text = "Bagian Theo disini",
                style = MaterialTheme.typography.bodyLarge
            )
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

///HALAMAN PINJAMAN KONTEN NASABAH - Theo & John
@Composable
fun PinjamanPage() {
    //bagian theo disini
    //untuk akses content resolver butuh context
    val context = LocalContext.current

    //state untuk variable informasi file yang dipilih
    var namaFileDipilih by remember { mutableStateOf<String?>(null) }
    var uriDipilih by remember { mutableStateOf<Uri?>(null) }

    //eksekusi utk dapatkan gambar, disediakan lapaknya
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            // Simpan URI yang dipilih ke dalam state
            uriDipilih = uri
            // Dapatkan nama file dari URI untuk ditampilkan di UI
            namaFileDipilih = getFileName(context, uri)
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
    LazyColumn (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ){
        //bagian kartu tagihan saat ini
        item{
            KartuTagihan(
                tagihan = tagihanSaatIni,
                namaFile = namaFileDipilih,//nama file dikirim ke composable card
                onUnggahBuktiClick = {
                    imagePickerLauncher.launch("image/*")
                },
                onKirimBuktiClick = {
                    // TODO: Panggil fungsi ViewModel/Repository untuk upload
                    uriDipilih?.let { uri ->
                        // uploadFileKeServer(context, uri)
                        println("Mulai mengunggah file: ${getFileName(context, uri)}")
                    }
                },
                onBatalPilihFileClick = {
                    // Reset state jika pengguna membatalkan
                    uriDipilih = null
                    namaFileDipilih = null
                }
            )
        }

        //bagian kartu detail keseluruhan pinjaman
        item{
            KartuDetailPinjaman(detail = detailPinjaman)
        }
    }
}

///HALAMAN HISTORI KONTEN NASABAH - John
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
@Composable
fun ProfilPage() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Konten Halaman Profil")
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
