package eu.tutorials.koperasi_simpan_pinjam.fragments.admin

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Help
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import eu.tutorials.koperasi_simpan_pinjam.pages.admin.InterestSlider
import eu.tutorials.koperasi_simpan_pinjam.ui.theme.DeepBlue
import eu.tutorials.koperasi_simpan_pinjam.ui.theme.KoperasiSimpanPinjamTheme
import kotlinx.coroutines.launch
import eu.tutorials.koperasi_simpan_pinjam.ui.theme.SoftLavender
import eu.tutorials.koperasi_simpan_pinjam.ui.theme.white


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerNavigationAdmin(
    navController: NavHostController,
    content: @Composable (PaddingValues) -> Unit
) {
    val navItemColors = NavigationDrawerItemDefaults.colors(
        unselectedTextColor = SoftLavender,
        unselectedIconColor = SoftLavender
    )

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val showNotification = remember { mutableStateOf(false) }

    if(showNotification.value){
        ModalBottomSheet(
            onDismissRequest = { showNotification.value = false },
            sheetState = sheetState,
            containerColor = white,
            dragHandle = { BottomSheetDefaults.DragHandle() }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text("Announcement", fontSize = 22.sp, color = DeepBlue)
                Spacer(Modifier.height(16.dp))
                var message by remember { mutableStateOf("") }
                var title by remember { mutableStateOf("") }

                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle(color = DeepBlue, fontSize = 16.sp),
                )

                OutlinedTextField(
                    value = message,
                    onValueChange = { message = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth().height(250.dp),
                    textStyle = TextStyle(color = DeepBlue, fontSize = 16.sp),
                )

                Spacer(Modifier.height(20.dp))

                Button(
                    onClick = {
                        // send rejection notice to user
                        showNotification.value = false
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = DeepBlue)
                ) {
                    Text("Send Announcement", color = white)
                }

                TextButton(onClick = { showNotification.value = false }) {
                    Icon(Icons.Default.Close, contentDescription = null)
                    Text("Close")
                }

            }

        }

    }

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = MaterialTheme.colorScheme.primary,
                drawerContentColor = MaterialTheme.colorScheme.onSurface
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Koperasi Simpan Pinjam",
                        modifier = Modifier.padding(vertical = 16.dp),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    HorizontalDivider()

                    Text(
                        "Users",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    NavigationDrawerItem(
                        label = { Text("Manage Users") },
                        selected = false,
                        icon = { Icon(Icons.Default.People, contentDescription = "Manage Users") },
                        onClick = {
                            navController.navigate(route = "manageuser")
                            scope.launch { drawerState.close() }
                        },
                        colors = navItemColors
                    )

                    NavigationDrawerItem(
                        label = { Text("Finance") },
                        selected = false,
                        icon = { Icon(Icons.Default.AttachMoney, contentDescription = "Finance") },
                        onClick = {
                            navController.navigate(route = "AdminTransaction")
                            scope.launch { drawerState.close() }
                        },
                        colors = navItemColors
                    )

                    NavigationDrawerItem(
                        label = { Text("Send Notification") },
                        selected = false,
                        icon = { Icon(Icons.AutoMirrored.Outlined.Help, contentDescription = "Send Notification") },
                        onClick = {
                            showNotification.value = true
                            scope.launch { drawerState.close() }
                        },
                        colors = navItemColors
                    )

                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                    Text(
                        "Cooperate",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    NavigationDrawerItem(
                        label = { Text("Dashboard") },
                        selected = false,
                        icon = { Icon(Icons.Default.Dashboard, contentDescription = null) },
                        onClick = {

                            scope.launch { drawerState.close() }
                                  },
                        colors = navItemColors
                    )
                    NavigationDrawerItem(
                        label = { Text("Report") },
                        selected = false,
                        icon = { Icon(Icons.Default.Description, contentDescription = null) },
                        onClick = {
                            navController.navigate(route = "AdminReport")
                            scope.launch { drawerState.close() }
                                  },
                        colors = navItemColors
                    )
                    NavigationDrawerItem(
                        label = { Text("Finace management") },
                        selected = false,
                        icon = { Icon(Icons.Default.Money, contentDescription = null) },
                        onClick = {
                            navController.navigate(route = "AdminManageMoney")
                            scope.launch { drawerState.close() }
                        },
                        colors = navItemColors
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                    NavigationDrawerItem(
                        label = { Text("Logout") },
                        selected = false,
                        icon = { Icon(Icons.Default.ExitToApp, contentDescription = null) },
                        onClick = { navController.navigate(route = "authentication") },
                        colors = navItemColors
                    )
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Admin Dashboard") },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                if (drawerState.isClosed) drawerState.open()
                                else drawerState.close()
                            }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }
        ) { innerPadding ->
            // Pass innerPadding to the content lambda
            Box(modifier = Modifier.padding(innerPadding)) {
                content(innerPadding)
            }
        }
    }
}


//@Preview(showBackground = true, name = "Dashboard admin", showSystemUi = true, device = Devices.PIXEL_5)
//@Composable
//
//fun AuthenticationPreview(){
//    KoperasiSimpanPinjamTheme {
//        val navController = rememberNavController()
//        DrawerNavigationAdmin (modifier = Modifier) { innerPadding ->
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(innerPadding),
//
//                ) {
//                Card(
//                    modifier = Modifier
//                        .padding(16.dp)
//                        .fillMaxWidth(),
//                    elevation = CardDefaults.cardElevation(4.dp),
//                    border = BorderStroke(1.dp, Color.Black),
//                ) {
//                    Column {
//                        // Top Row: Text + Button Icon
//                        Row(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(
//                                    start = 24.dp,
//                                    end = 24.dp,
//                                    top = 24.dp,
//                                    bottom = 5.dp
//                                ),
//                            horizontalArrangement = Arrangement.SpaceBetween
//                        ) {
//                            Text(
//                                text = "Manage Users",
//                                fontSize = 30.sp
//                            )
//
//                            // Button Icon (Arrow)
//                            IconButton(onClick = {  }) {
//                                Icon(
//                                    imageVector = Icons.Default.ArrowForward,
//                                    contentDescription = "Go to Manage Users"
//                                )
//                            }
//                        }
//
//                        // Bottom Section
//                        Column(
//                            modifier = Modifier.padding(
//                                start = 24.dp,
//                                end = 24.dp,
//                                top = 5.dp,
//                                bottom = 14.dp
//                            )
//                        ) {
//                            Text(text = "Amount of Users", style = MaterialTheme.typography.titleLarge)
//                            Text(
//                                modifier = Modifier.padding(vertical = 10.dp),
//                                text = "0",
//                                fontSize = 16.sp
//                            )
//                        }
//                    }
//                }
//            }
//        }
//    }
//}