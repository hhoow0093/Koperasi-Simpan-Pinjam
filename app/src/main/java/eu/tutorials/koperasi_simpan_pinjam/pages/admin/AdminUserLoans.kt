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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import eu.tutorials.koperasi_simpan_pinjam.data.API.RetrofitClient
import eu.tutorials.koperasi_simpan_pinjam.data.repository.admin.UserRepositoryPeminjaman
import eu.tutorials.koperasi_simpan_pinjam.data.viewmodel.admin.PeminjamanUserViewModel
import eu.tutorials.koperasi_simpan_pinjam.data.viewmodel.admin.PeminjamanUserViewModelFactory
import eu.tutorials.koperasi_simpan_pinjam.ui.theme.DeepBlue
import eu.tutorials.koperasi_simpan_pinjam.ui.theme.KoperasiSimpanPinjamTheme
import eu.tutorials.koperasi_simpan_pinjam.ui.theme.SoftLavender
import eu.tutorials.koperasi_simpan_pinjam.ui.theme.white
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ExpandableCardLoansOnProgress(
    modifier: Modifier = Modifier,
    header: String,
    subheader: String,
    description: String,
    navController: NavHostController,
    routeLoansViewing: String,
    pinjaman: Double,
    onRejectClick: () -> Unit,
    onAcceptClick: () -> Unit
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = header, fontSize = 30.sp)
            }

            // Main Info
            Column(
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 14.dp)
            ) {
                when (subheader) {
                    "Proses" -> {
                        Text(
                            text = subheader,
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.White,
                            modifier = Modifier
                                .background(
                                    color = Color(0xFFe05600),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                    "Disetujui" -> {
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
                    }
                    "Lunas" -> {
                        Text(
                            text = subheader,
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.White,
                            modifier = Modifier
                                .background(
                                    color = Color.DarkGray,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                    else -> {
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
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(space = 8.dp)
                    ) {
                        when (subheader) {
                            "Proses" -> {
                                Button(onClick = { onAcceptClick() }) {
                                    Text("Accept")
                                }
                                Button(onClick = { onRejectClick() }) {
                                    Text("Reject")
                                }
                            }
                            "Disetujui", "Lunas" -> {
                                Button(onClick = { navController.navigate(route = routeLoansViewing) }) {
                                    Text("View history")
                                }
                            }
                        }
                    }
                    Text(
                        color = DeepBlue,
                        text = "Rp ${pinjaman}",
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }
            }
        }
    }
}


@Composable
fun InterestSlider(
    onSetClick: (Int, Int) -> Unit
) {
    var interest by remember { mutableStateOf(5f) }
    var fine by remember { mutableStateOf(3f) }

    Column(Modifier.padding(16.dp)) {

        Text("Suku Bunga: ${interest.toInt()}%", color = DeepBlue)
        Slider(
            value = interest,
            onValueChange = { interest = it },
            valueRange = 0f..10f,
            steps = 9
        )

        Text("Denda: ${fine.toInt()}%", color = DeepBlue)
        Slider(
            value = fine,
            onValueChange = { fine = it },
            valueRange = 0f..10f,
            steps = 9
        )

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                onSetClick(interest.toInt(), fine.toInt())
            }
        ) {
            Text("Aktifkan Peminjaman")
        }
    }
}


@Composable
fun ErrorCard(
    errorMessage: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(vertical = 16.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp),
        border = BorderStroke(1.dp, Color.Black),
        colors = CardDefaults.cardColors(
            containerColor = SoftLavender,
            contentColor = DeepBlue
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
        ) {
            Text(
                text = "Pinjaman kosong",
                fontSize = 30.sp,
                color = DeepBlue
            )

            Text(
                text = "pinjaman user kosong",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Red,
                modifier = Modifier.padding(top = 15.dp)
            )
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminUserLoansPage(
    navController: NavHostController,
    userId: String,
    viewModel: PeminjamanUserViewModel,
    name: String // Consider moving name into ViewModel if needed
) {
    LaunchedEffect(userId) {
        viewModel.fetchPinjamanUser(userId)
    }

    val pinjamanList by viewModel.pinjamanList.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val selectedLoanId = remember { mutableStateOf<String?>(null) }

    // UI state
    val showRejectSheet = remember { mutableStateOf(false) }
    val showSuccessDialog = remember { mutableStateOf(false) }
    val showInterestAndFineModal = remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    // --- Modals and Dialogs (unchanged) ---
    if (showInterestAndFineModal.value) {
        ModalBottomSheet(
            onDismissRequest = { showInterestAndFineModal.value = false },
            sheetState = sheetState,
            containerColor = Color.White,
            dragHandle = { BottomSheetDefaults.DragHandle() }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Manage Interest & Fine", fontSize = 22.sp, color = DeepBlue)
                Spacer(Modifier.height(16.dp))
                InterestSlider { bunga, denda ->
                    selectedLoanId.value?.let { loanId ->
                        viewModel.approveLoan(
                            loanId = loanId,
                            bunga = bunga,
                            denda = denda,
                            userId = userId
                        )
                    }
                    showInterestAndFineModal.value = false
                }
                Spacer(Modifier.height(20.dp))
                TextButton(onClick = { showInterestAndFineModal.value = false }) {
                    Icon(Icons.Default.Close, contentDescription = null)
                    Text("Batal")
                }
            }
        }
    }

    if (showRejectSheet.value) {
        ModalBottomSheet(
            onDismissRequest = { showRejectSheet.value = false },
            sheetState = sheetState,
            containerColor = Color.White,
            dragHandle = { BottomSheetDefaults.DragHandle() }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Reject Application", fontSize = 22.sp, color = DeepBlue)
                Spacer(Modifier.height(16.dp))
                var message by remember { mutableStateOf("") }
                OutlinedTextField(
                    value = message,
                    onValueChange = { message = it },
                    label = { Text("Reason for rejection") },
                    modifier = Modifier.fillMaxWidth().height(250.dp),
                    textStyle = TextStyle(color = DeepBlue, fontSize = 16.sp),
                )
                Spacer(Modifier.height(20.dp))
                Button(
                    onClick = {
//                        println("selectedLoanId: ${selectedLoanId}")
                        selectedLoanId.value?.let { loanId ->
                            viewModel.rejectLoan(
                                loanId = loanId,
                                reason = message,
                                userId = userId
                            )
                        }
                        showRejectSheet.value = false
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = DeepBlue)
                ) {
                    Text("Send", color = Color.White)
                }
                TextButton(onClick = { showRejectSheet.value = false }) {
                    Icon(Icons.Default.Close, contentDescription = null)
                    Text("Close")
                }
            }
        }
    }

    if (showSuccessDialog.value) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = { showSuccessDialog.value = false },
            confirmButton = {
                TextButton(onClick = { showSuccessDialog.value = false }) { Text("NO") }
                TextButton(onClick = {
                    showSuccessDialog.value = false
                    showInterestAndFineModal.value = true
                }) { Text("OK") }
            },
            title = { Text(text = "Are you sure? ", color = DeepBlue) },
            text = { Text("Loan will be active in status", color = DeepBlue) }
        )
    }

    val searchEmail = remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 10.dp, end = 10.dp, top = 25.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TopBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .zIndex(3f)
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(bottom = 15.dp),
                searchEmail = searchEmail,
                navController = navController
            )

            // Main content
            when {
                isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                errorMessage != null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 10.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ){
                            item{
                                ErrorCard(
                                    errorMessage = errorMessage!!,
                                    modifier = Modifier.padding(16.dp)
                                )

                            }
                        }

                    }
                }

                else -> {
                    // Use LazyColumn for performance
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 10.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(
                            items = pinjamanList,
                            key = { it._id ?: "loan_${it.jumlah}" } // safe key
                        ) { pinjaman ->
                            ExpandableCardLoansOnProgress(
                                header = name, // or extract from pinjaman if available
                                subheader = pinjaman.status,
                                description = "Pinjaman: ${pinjaman.jumlah} | Tenor: ${pinjaman.tenor} bulan",
                                navController = navController,
                                routeLoansViewing = "PencatatanAngsuran/${pinjaman._id}",
                                pinjaman = pinjaman.jumlah,
                                onAcceptClick = {
                                    selectedLoanId.value = pinjaman._id
                                    showSuccessDialog.value = true
                                                },
                                onRejectClick = {
                                    selectedLoanId.value = pinjaman._id
                                    showRejectSheet.value = true
                                }
                            )
                        }

                        // Optional: Add padding at bottom
                        item {
                            Spacer(modifier = Modifier.height(24.dp))
                        }
                    }
                }
            }
        }
    }
}





@Preview(showBackground = true, name = "Manage loans", showSystemUi = true, device = Devices.PIXEL_5)
@Composable
fun AdminUserLoansPreview() {
    KoperasiSimpanPinjamTheme {
        val userId = "693d399289449e4a8e0698b1"
        val repository = UserRepositoryPeminjaman(api = RetrofitClient.instance)
        val viewModel: PeminjamanUserViewModel = viewModel(factory = PeminjamanUserViewModelFactory(repository = repository))
        val navController = rememberNavController()
        AdminUserLoansPage(navController = navController, userId = userId, viewModel = viewModel, name = "mantap")
    }
}