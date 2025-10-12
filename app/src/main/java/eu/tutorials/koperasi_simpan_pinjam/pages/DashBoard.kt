package eu.tutorials.koperasi_simpan_pinjam.pages
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import eu.tutorials.koperasi_simpan_pinjam.navigation.AppNavHost
import eu.tutorials.koperasi_simpan_pinjam.ui.theme.KoperasiSimpanPinjamTheme
import kotlinx.coroutines.launch

// Data class untuk item di drawer
data class DrawerItem(val title: String, val icon: @Composable () -> Unit, val route: String)
// Data class untuk item di bottom bar
data class BottomBarItem(val label: String, val icon: ImageVector, val route: String)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun DashBoard(navController: NavHostController, modifier: Modifier = Modifier) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Daftar item untuk menu samping (drawer)
    val drawerItems = listOf(
        DrawerItem("Dashboard", { Icon(Icons.Default.Home, contentDescription = null) }, "dashboard"),
        DrawerItem("Simpanan", { Icon(Icons.Default.AccountBalance, contentDescription = null) }, "dummy/simpanan"),
        DrawerItem("Pinjaman", { Icon(Icons.Default.CreditCard, contentDescription = null) }, "dummy/pinjaman"),
        DrawerItem("Laporan", { Icon(Icons.Default.FileCopy, contentDescription = null) }, "dummy/laporan"),
        DrawerItem("Pengaturan", { Icon(Icons.Default.Settings, contentDescription = null) }, "dummy/pengaturan")
    )

    // Daftar item untuk navigasi bawah
    val bottomItems = listOf(
        BottomBarItem("Home", Icons.Default.Home, "dashboard"),
        BottomBarItem("Simpanan", Icons.Default.AccountBalance, "dummy/simpanan"),
        BottomBarItem("Pinjaman", Icons.Default.CreditCard, "dummy/pinjaman"),
        BottomBarItem("Laporan", Icons.Default.FileCopy, "dummy/laporan"),
        BottomBarItem("Settings", Icons.Default.Settings, "dummy/pengaturan")
    )

    var selectedBottomIndex by remember { mutableStateOf(0) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    text = "Koperasi Simpan Pinjam",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(16.dp)
                )

                drawerItems.forEach { item ->
                    NavigationDrawerItem(
                        label = { Text(item.title) },
                        icon = item.icon,
                        selected = false,
                        onClick = {
                            scope.launch { drawerState.close() }
                            // Navigasi bisa disesuaikan nanti
                            navController.navigate(item.route) {
                                launchSingleTop = true
                            }
                        }
                    )
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Dashboard") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            },
            bottomBar = {
                NavigationBar {
                    bottomItems.forEachIndexed { index, item ->
                        NavigationBarItem(
                            icon = { Icon(item.icon, contentDescription = item.label) },
                            label = { Text(item.label) },
                            selected = selectedBottomIndex == index,
                            onClick = {
                                selectedBottomIndex = index
                                // Navigasi bisa disesuaikan nanti
                                navController.navigate(item.route) {
                                    launchSingleTop = true
                                }
                            }
                        )
                    }
                }
            }
        ) { innerPadding ->
            // KONTEN UTAMA DENGAN TOP TAB BAR DIMULAI DARI SINI
            Column(modifier = modifier.padding(innerPadding)) {
                // 1. Definisikan daftar judul untuk setiap tab
                val tabs = listOf("Simpan", "Pinjam", "Catatan", "Keseluruhan")

                // 2. Buat PagerState untuk mengontrol halaman
                val pagerState = rememberPagerState(pageCount = { tabs.size })
                val coroutineScope = rememberCoroutineScope()

                // 3. Buat TabRow (Baris Tab di Atas)
                TabRow(selectedTabIndex = pagerState.currentPage) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = pagerState.currentPage == index,
                            onClick = {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            },
                            text = { Text(text = title) }
                        )
                    }
                }

                // 4. Buat HorizontalPager (Konten yang Bisa Digeser)
                HorizontalPager(state = pagerState) { page ->
                    when (page) {
                        0 -> SimpananContent()
                        1 -> PinjamanContent()
                        2 -> CatatanContent()
                        3 -> KeseluruhanContent()
                    }
                }
            }
        }
    }
}
@Composable
fun SimpananContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Konten untuk Halaman Simpanan")
    }
}

@Composable
fun PinjamanContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Konten untuk Halaman Pinjaman")
    }
}

@Composable
fun CatatanContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Konten untuk Halaman Catatan")
    }
}

@Composable
fun KeseluruhanContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Konten untuk Halaman Keseluruhan")
    }
}

@Composable
fun DummyPage(title: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "This is a dummy page for: $title", style = MaterialTheme.typography.titleMedium)
    }
}

@Preview(showBackground = true, name = "Register page", showSystemUi = true, device = Devices.PIXEL_5)
@Composable
fun DashboardPreview() {
    KoperasiSimpanPinjamTheme {
        val navController = rememberNavController()
        AppNavHost(navController)
    }
}
