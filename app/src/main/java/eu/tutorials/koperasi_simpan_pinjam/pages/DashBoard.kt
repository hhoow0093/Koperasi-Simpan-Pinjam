package eu.tutorials.koperasi_simpan_pinjam.pages

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch

data class DrawerItem(val title: String, val icon: @Composable () -> Unit, val route: String)

data class BottomBarItem(val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector, val route: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashBoard(navController: NavHostController, modifier: Modifier = Modifier) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val drawerItems = listOf(
        DrawerItem("Dashboard", { Icon(Icons.Default.Home, contentDescription = null) }, "dashboard"),
        DrawerItem("Simpanan", { Icon(Icons.Default.AccountBalance, contentDescription = null) }, "simpanan"),
        DrawerItem("Pinjaman", { Icon(Icons.Default.CreditCard, contentDescription = null) }, "pinjaman"),
        DrawerItem("Laporan", { Icon(Icons.Default.FileCopy, contentDescription = null) }, "laporan"),
        DrawerItem("Pengaturan", { Icon(Icons.Default.Settings, contentDescription = null) }, "pengaturan")
    )

    val bottomItems = listOf(
        BottomBarItem("Home", Icons.Default.Home, "dashboard"),
        BottomBarItem("Simpanan", Icons.Default.AccountBalance, "simpanan"),
        BottomBarItem("Pinjaman", Icons.Default.CreditCard, "pinjaman"),
        BottomBarItem("Laporan", Icons.Default.FileCopy, "laporan"),
        BottomBarItem("Settings", Icons.Default.Settings, "pengaturan")
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
                            navController.navigate("dummy/${item.title}") {
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
                                navController.navigate("dummy/${item.label}") {
                                    launchSingleTop = true
                                }
                            }
                        )
                    }
                }
            }
        ) { innerPadding ->
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            )
        }
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
