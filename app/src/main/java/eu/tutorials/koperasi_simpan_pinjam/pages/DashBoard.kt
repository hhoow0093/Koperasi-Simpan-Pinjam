package eu.tutorials.koperasi_simpan_pinjam.pages

import androidx.collection.objectListOf
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
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Data class untuk item di drawer
data class DrawerItem(val title: String, val icon: @Composable () -> Unit, val route: String)
// Data class untuk item di bottom bar
data class BottomBarItem(val label: String, val icon: ImageVector, val route: String)

// --- HALAMAN KONTEN BARU UNTUK SETIAP ITEM BOTTOM NAVBAR ---

@Composable
fun HomePage() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Konten Halaman Home")
    }
}

@Composable
fun SimpananPage() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Konten Halaman Simpanan")
    }
}

@Composable
fun PinjamanPage() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Konten Halaman Pinjaman")
    }
}

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
                        .background(if (angsuran.status == "Lunas") Color(0xFFE8F5E9) else Color(0xFFFFEBEE))
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

@Preview(showBackground = true, name = "Dashboard Page", showSystemUi = true, device = Devices.PIXEL_5)
@Composable
fun DashboardPreview() {
    KoperasiSimpanPinjamTheme {
        DashBoard(navController = rememberNavController())
    }
}
