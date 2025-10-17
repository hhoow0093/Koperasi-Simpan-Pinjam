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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import eu.tutorials.koperasi_simpan_pinjam.data.API.RetrofitClient
import eu.tutorials.koperasi_simpan_pinjam.data.repository.UserRepository
import eu.tutorials.koperasi_simpan_pinjam.data.viewmodel.DashBoardAdminViewModel
import eu.tutorials.koperasi_simpan_pinjam.data.viewmodel.DashboardViewModelFactory
import eu.tutorials.koperasi_simpan_pinjam.fragments.admin.AdminSearch
import eu.tutorials.koperasi_simpan_pinjam.ui.theme.DeepBlue
import eu.tutorials.koperasi_simpan_pinjam.ui.theme.KoperasiSimpanPinjamTheme
import eu.tutorials.koperasi_simpan_pinjam.ui.theme.white
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import eu.tutorials.koperasi_simpan_pinjam.ui.theme.SoftLavender
import kotlin.String


@Composable
fun ExpandableCardTransaction(
    modifier: Modifier = Modifier,
    header: String,
    subheader: String,
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
                Text(text = header, fontSize = 30.sp)
            }

            // Main Info
            Column(
                modifier = modifier.padding(horizontal = 24.dp, vertical = 14.dp)
            ) {
                if(subheader == "Active"){
                    Text(
                        text = subheader,
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
                        text = subheader,
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
                            Text("Loans")
                        }
                        Button(
                            onClick = { navController.navigate(route = routeNameSaving) },
                        ) {
                            Text("Savings")
                        }
                    }

                }
            }
        }
    }
}



@Composable
fun AdminTransactionPage(navController: NavHostController) {
    val searchEmail = remember { mutableStateOf("") }
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp, vertical = 15.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(top = 80.dp)
        ) {
            repeat(2) {
                ExpandableCardTransaction(
                    modifier = Modifier,
                    header = "Howard",
                    subheader = "Active",
                    description = "View Member finance",
                    navController = navController,
                    routeNameSaving = "AdminUserSaving",
                    routeNameLoans = "AdminUserLoans"
                )
            }
            repeat(3) {
                ExpandableCardTransaction(
                    modifier = Modifier,
                    header = "Jastin Afrian",
                    subheader = "Inactive",
                    description = "View Member finance",
                    navController = navController,
                    routeNameSaving = "AdminUserSaving",
                    routeNameLoans = "AdminUserLoans"
                )
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
        AdminTransactionPage(navController = navController)
    }
}