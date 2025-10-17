package eu.tutorials.koperasi_simpan_pinjam.pages.admin


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import eu.tutorials.koperasi_simpan_pinjam.data.API.RetrofitClient
import eu.tutorials.koperasi_simpan_pinjam.data.repository.UserRepository
import eu.tutorials.koperasi_simpan_pinjam.data.viewmodel.DashBoardAdminViewModel
import eu.tutorials.koperasi_simpan_pinjam.data.viewmodel.DashboardViewModelFactory
import eu.tutorials.koperasi_simpan_pinjam.fragments.admin.DrawerNavigationAdmin
import eu.tutorials.koperasi_simpan_pinjam.ui.theme.DeepBlue
import eu.tutorials.koperasi_simpan_pinjam.ui.theme.KoperasiSimpanPinjamTheme
import eu.tutorials.koperasi_simpan_pinjam.ui.theme.SoftLavender
import ir.ehsannarmani.compose_charts.PieChart
import ir.ehsannarmani.compose_charts.models.Pie


@Composable
fun manageUserCard(modifier: Modifier, userCount : String, errorMessage: String?, navController: NavHostController){
    Card(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp),
        border = BorderStroke(1.dp, Color.Black),
        colors = CardDefaults.cardColors(
            containerColor = SoftLavender,
            contentColor = DeepBlue
        ),
    ) {
        Column {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Manage Users", fontSize = 30.sp)
                IconButton(onClick = { navController.navigate("manageuser") }) {
                    Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "Manage Users")
                }
            }

            Column(modifier = modifier.padding(horizontal = 24.dp, vertical = 14.dp)) {
                Text(text = "Amount of Users", style = MaterialTheme.typography.titleLarge)
                if (!errorMessage.isNullOrEmpty())
                    Text(text = "Error: $errorMessage", color = Color.Red)
                else
                    Text(text = userCount, fontSize = 20.sp)
            }
        }
    }
}

@Composable
fun pieChart(){
    val colors = listOf(
        Color(0xFF0044FC),
        Color(0xFF2563EB),
        Color(0xFF431291),
        Color(0xFF0045FF),
        Color(0xFF00CDFF),
        Color(0xFF5F00FF),
        Color(0xFF75112D),
        Color(0xFFD51A4E)
    )
    var data by remember {
        mutableStateOf(
            listOf(
                Pie(label = "Net Profit", data = 20.0, color = colors[0], selectedColor = colors[3]),
                Pie(label = "Gross Profit", data = 45.0, color = colors[1], selectedColor = colors[4]),
                Pie(label = "Corporate Finance", data = 65.0, color = colors[2], selectedColor = colors[5]),
                Pie(label = "Loss", data = 15.0, color = colors[6], selectedColor = colors[7])

                ),
            )
    }

    // Track selected pie
    var selectedPie by remember { mutableStateOf<Pie?>(null) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 35.dp)
    ) {
        PieChart(
            modifier = Modifier.size(200.dp),
            data = data,
            onPieClick = {
                println("${it.label} Clicked")
                val pieIndex = data.indexOf(it)
                data = data.mapIndexed { mapIndex, pie ->
                    pie.copy(selected = pieIndex == mapIndex)
                }
                selectedPie = it // Update selected pie
            },
            selectedScale = 1.1f,
            spaceDegree = 0f,
            selectedPaddingDegree = 0f,
            style = Pie.Style.Stroke(width = 25.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Display selected pie info
        selectedPie?.let { pie ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = pie.color
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = pie.label.toString(),
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White
                    )
                    Text(
                        text = "$${pie.data}",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White
                    )
                }
            }
        } ?: run {
            // Show placeholder when nothing is selected
            Text(
                text = "Tap to see details",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
    }
}
@Composable
fun myCustomCard(
    modifier: Modifier,
    navController: NavHostController,
    routeName: String,
    Header: String,
    Subheader: String,
    description: String
) {
    Card(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp),
        border = BorderStroke(1.dp, Color.Black),
        colors = CardDefaults.cardColors(
            containerColor = SoftLavender,
            contentColor = DeepBlue
        ),
    ) {
        Column {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = Header, fontSize = 30.sp)
                IconButton(onClick = { navController.navigate(route = routeName) }) {
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = Header
                    )
                }
            }

            Column(modifier = modifier.padding(horizontal = 24.dp, vertical = 14.dp)) {
                Text(text = Subheader, style = MaterialTheme.typography.titleLarge)
                Text(text = description)
            }
        }
    }
}
@Composable
fun ContentDashboard(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: DashBoardAdminViewModel
) {
    val userCount by viewModel.userCount.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    // Load count when this composable starts
    LaunchedEffect(Unit) {
        viewModel.loadUserCount()
    }
    val scrollState = rememberScrollState()
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column (modifier = modifier.fillMaxWidth().verticalScroll(scrollState), horizontalAlignment = Alignment.CenterHorizontally){
            pieChart()

            manageUserCard(
                modifier = modifier,
                userCount = userCount.toString(),
                errorMessage = errorMessage,
                navController = navController
            )
            myCustomCard(
                modifier = modifier ,
                navController = navController,
                routeName = "AdminTransaction",
                Header = "User Money Report",
                Subheader= "Transaction",
                description= "Saving / Loans"
            )

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardAdminn(navController: NavHostController, viewModel: DashBoardAdminViewModel) {
    DrawerNavigationAdmin(navController = navController) { innerPadding ->
        ContentDashboard(
            modifier = Modifier,
            navController = navController,
            viewModel = viewModel
        )
    }
}

@Preview(showBackground = true, name = "Manage user", showSystemUi = true, device = Devices.PIXEL_5)
@Composable
fun DashboardAdminPreview() {
    KoperasiSimpanPinjamTheme {
        val repository = UserRepository(RetrofitClient.instance)
        val viewModel: DashBoardAdminViewModel = viewModel(
            factory = DashboardViewModelFactory(repository)
        )
        val navController = rememberNavController()
        DashboardAdminn(navController = navController, viewModel = viewModel)
    }
}



