package eu.tutorials.koperasi_simpan_pinjam.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch

// data item sidebar
data class DrawerItem(val title: String, val icon: @Composable () -> Unit, val route: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashBoard(navController: NavHostController, modifier: Modifier = Modifier) {
    // state drawer
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // daftar menu sidebar
    val items = listOf(
        DrawerItem("Dashboard", { Icon(Icons.Default.Home, contentDescription = null) }, "dashboard"),
        DrawerItem("Simpanan", { Icon(Icons.Default.AccountBalance, contentDescription = null) }, "simpanan"),
        DrawerItem("Pinjaman", { Icon(Icons.Default.CreditCard, contentDescription = null) }, "pinjaman"),
        DrawerItem("Laporan", { Icon(Icons.Default.FileCopy, contentDescription = null) }, "laporan"),
        DrawerItem("Pengaturan", { Icon(Icons.Default.Settings, contentDescription = null) }, "pengaturan")
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                // pakai fully-qualified untuk Text agar pasti memanggil Compose Material3 Text
                androidx.compose.material3.Text(
                    text = "Koperasi Simpan Pinjam",
                    style = androidx.compose.material3.MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(16.dp)
                )

                items.forEach { item ->
                    NavigationDrawerItem(
                        label = { androidx.compose.material3.Text(item.title) },
                        icon = item.icon,
                        selected = false,
                        onClick = {
                            // tutup drawer
                            scope.launch { drawerState.close() }
                            // navigasi ke route sesuai
                            navController.navigate(item.route) {
                                popUpTo("dashboard") { inclusive = false }
                                launchSingleTop = true
                            }
                        }
                    )
                }
            }
        }
    ) {
        // isi layar utama dashboard
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { androidx.compose.material3.Text("Dashboard") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            }
        ) { innerPadding ->
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                androidx.compose.material3.Text("Ini Dashboard Koperasi Simpan Pinjam")
            }
        }
    }
}
