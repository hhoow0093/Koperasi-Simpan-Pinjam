package eu.tutorials.koperasi_simpan_pinjam.pages

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
import androidx.compose.ui.Alignment
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
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Konten Halaman Histori")
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

@Preview(showBackground = true, name = "Dashboard Page", showSystemUi = true, device = Devices.PIXEL_5)
@Composable
fun DashboardPreview() {
    KoperasiSimpanPinjamTheme {
        DashBoard(navController = rememberNavController())
    }
}
