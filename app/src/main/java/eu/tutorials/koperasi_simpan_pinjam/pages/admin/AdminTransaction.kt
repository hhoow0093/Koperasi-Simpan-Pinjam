package eu.tutorials.koperasi_simpan_pinjam.pages.admin

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import eu.tutorials.koperasi_simpan_pinjam.ui.theme.DeepBlue
import eu.tutorials.koperasi_simpan_pinjam.ui.theme.KoperasiSimpanPinjamTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import eu.tutorials.koperasi_simpan_pinjam.data.API.RetrofitClient
import eu.tutorials.koperasi_simpan_pinjam.data.repository.admin.UserRepository
import eu.tutorials.koperasi_simpan_pinjam.data.viewmodel.admin.DashBoardAdminViewModel
import eu.tutorials.koperasi_simpan_pinjam.data.viewmodel.admin.DashboardViewModelFactory
import eu.tutorials.koperasi_simpan_pinjam.data.viewmodel.admin.ManageUserAdminViewModel
import eu.tutorials.koperasi_simpan_pinjam.data.viewmodel.admin.ManageUserAdminViewModelFactory
import eu.tutorials.koperasi_simpan_pinjam.fragments.admin.UserList
import eu.tutorials.koperasi_simpan_pinjam.ui.theme.SoftLavender
import kotlin.String


@Composable
fun ExpandableCardTransaction(
    modifier: Modifier = Modifier,
    header: String?,
    subheader: Boolean?,
    description: String,
    navController: NavHostController,
    routeNameSaving: String,
    routeNameLoans: String
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .padding(vertical = 16.dp)
            .fillMaxWidth()
            .animateContentSize() // Smooth height animation
            .clickable { expanded = !expanded }, // Toggle expand on click
        elevation = CardDefaults.cardElevation(4.dp),
        border = BorderStroke(1.dp, Color.Black),
        colors = CardDefaults.cardColors(
            containerColor = SoftLavender,
            contentColor = DeepBlue
        ),
    ) {
        Column {
            // Header Row
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (header != null) {
                    Text(text = header, fontSize = 30.sp)
                }
            }

            // Main Info
            Column(
                modifier = modifier.padding(horizontal = 24.dp, vertical = 14.dp)
            ) {
                if(subheader == true){
                    Text(
                        text = "Aktif",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White,
                        modifier = Modifier
                            .background(
                                color = Color.Green,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )

                }else{
                    Text(
                        text = "Tidak aktif",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White,
                        modifier = Modifier
                            .background(
                                color = Color.Red,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )

                }
                Text(modifier = Modifier.padding(top = 15.dp), text = description)
            }

            // Animated expansion
            AnimatedVisibility(visible = expanded) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(space=8.dp)) {
                        Button(
                            onClick = { navController.navigate(route = routeNameLoans) },
                        ) {
                            Text("Pinjaman")
                        }
                        Button(
                            onClick = { navController.navigate(route = routeNameSaving) },
                        ) {
                            Text("Simpanan")
                        }
                    }

                }
            }
        }
    }
}



@Composable
fun AdminTransactionPage(navController: NavHostController, viewModel: ManageUserAdminViewModel) {
    val searchEmail = remember { mutableStateOf("") }
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        viewModel.fetchAllUsers()
    }

    val users by viewModel.userList.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp, vertical = 15.dp)
    ) {
        when {
            isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            errorMessage != null -> {
                Text("Error: $errorMessage")
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 64.dp)
                ) {
                    items(
                        items = users,
                        key = { user -> user._id!! }
                    ) { user ->
                        ExpandableCardTransaction(
                            modifier = Modifier,
                            header = user.name,
                            subheader = user.member_status,
                            description = "Lihat Keuangan Anggota",
                            navController = navController,
                            routeNameSaving = "AdminUserSaving/${user._id}",
                            routeNameLoans = "AdminUserLoans/${user._id}/${user.name}"
                        )
                    }
                }
            }
        }

        TopBar(
            modifier = Modifier
                .fillMaxWidth()
                .zIndex(3f)
                .background(MaterialTheme.colorScheme.surface),
            searchEmail = searchEmail,
            navController = navController
        )
    }
}

@Preview(showBackground = true, name = "Manage user", showSystemUi = true, device = Devices.PIXEL_5)
@Composable
fun AdminTransactionPreview() {
    KoperasiSimpanPinjamTheme {
        val navController = rememberNavController()
        val repository = UserRepository(RetrofitClient.instance)
        val viewModel: ManageUserAdminViewModel = viewModel(
            factory = ManageUserAdminViewModelFactory(repository)
        )
        AdminTransactionPage(navController = navController, viewModel= viewModel)
    }
}