package eu.tutorials.koperasi_simpan_pinjam.pages.admin

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import eu.tutorials.koperasi_simpan_pinjam.ui.theme.KoperasiSimpanPinjamTheme
import ir.ehsannarmani.compose_charts.ColumnChart
import ir.ehsannarmani.compose_charts.models.BarProperties
import ir.ehsannarmani.compose_charts.models.Bars
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import eu.tutorials.koperasi_simpan_pinjam.data.API.RetrofitClient
import eu.tutorials.koperasi_simpan_pinjam.data.API.TipeTransaksi
import eu.tutorials.koperasi_simpan_pinjam.data.API.TransaksiSimpanan
import eu.tutorials.koperasi_simpan_pinjam.data.repository.admin.UserRepositorySimpanan
import eu.tutorials.koperasi_simpan_pinjam.data.viewmodel.admin.SimpananViewModelFactoryUser
import eu.tutorials.koperasi_simpan_pinjam.data.viewmodel.admin.SimpananViewModelUser
import eu.tutorials.koperasi_simpan_pinjam.ui.theme.DeepBlue
import eu.tutorials.koperasi_simpan_pinjam.ui.theme.SoftLavender
import eu.tutorials.koperasi_simpan_pinjam.ui.theme.white


fun extractMonth(tanggal: String): String {
    return tanggal.split(" ")[1] // "Des"
}
@Composable
fun CardSavings(
    modifier: Modifier = Modifier,
    header: Double,
    subheader: String,
    description: TipeTransaksi,
    tanggal: String,
    buktiImageUrl: String?,
    onViewBukti: (String) -> Unit,
    onDelete: () -> Unit
) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    val colors = listOf(
        Color(0xFF1E3A8A), // navy blue
        Color(0xFF2563EB), // dark blue
        Color(0xFF7C3AED)  // deep purple
    )

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
                Text(text = "Rp. $header,00", fontSize = 30.sp, )
            }

            // Main Info
            Column(
                modifier = modifier.padding(horizontal = 24.dp, vertical = 14.dp)
            ) {
                if(subheader == "Wajib"){
                    Text(
                        text = subheader,
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White,
                        modifier = Modifier
                            .background(
                                color = colors[0],
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )

                }else if(subheader == "Sukarela"){
                    Text(
                        text = subheader,
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White,
                        modifier = Modifier
                            .background(
                                color = colors[1],
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
                else{
                    Text(
                        text = subheader,
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White,
                        modifier = Modifier
                            .background(
                                color = colors[2],
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )

                }
            }

            // Animated expansion
            AnimatedVisibility(visible = expanded) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(tanggal)

                    Text(modifier = Modifier.padding(top = 5.dp), text = description.toString())

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = {
                                buktiImageUrl?.let { onViewBukti(it) }
                            },
                            enabled = buktiImageUrl != null,
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Bukti Pembayaran")
                        }

                        TextButton(
                            onClick = { showDeleteDialog = true },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "Hapus Simpanan",
                                color = MaterialTheme.colorScheme.error,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }


                }
            }
        }
    }
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = {
                Text("Hapus Simpanan?", color = Color.Black)
            },
            text = {
                Text("Apakah Anda yakin ingin menghapus simpanan ini? Tindakan ini tidak dapat dibatalkan.")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog = false
                        onDelete()
                    }
                ) {
                    Text("Hapus", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDeleteDialog = false }
                ) {
                    Text("Batal")
                }
            }
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarWithDropDown(modifier: Modifier, searchEmail: MutableState<String>, navController: NavController, name: String){
    var expandedSavingType by remember { mutableStateOf(false) }
    var savingtype by remember { mutableStateOf("Jenis Simpanan") }
    Column(modifier = modifier.fillMaxWidth()){
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically, modifier = modifier.fillMaxWidth()){
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .background(
                        color = DeepBlue,
                        shape = RoundedCornerShape(16.dp)
                    )
            ) {
                IconButton(
                    onClick = { navController.popBackStack()},
                    modifier = Modifier
                        .size(48.dp)
                        .align(Alignment.Center)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Go to Manage Users",
                        tint = white
                    )
                }

            }
            Text("$name", fontSize = 25.sp)
        }
        ExposedDropdownMenuBox(
            expanded = expandedSavingType,
            onExpandedChange = { expandedSavingType = !expandedSavingType }
        ) {
            OutlinedTextField(
                value = savingtype,
                onValueChange = {},
                label = { Text("Type") },
                modifier = Modifier.menuAnchor().fillMaxWidth(),
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedSavingType)
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                ),
                textStyle = TextStyle(color = DeepBlue, fontSize = 16.sp)
            )

            ExposedDropdownMenu(
                expanded = expandedSavingType,
                onDismissRequest = { expandedSavingType = false }
            ) {
                DropdownMenuItem(
                    text = { Text(text = "Pokok", color = DeepBlue) },
                    onClick = {
                        savingtype = "Pokok"
                        expandedSavingType = false
                    }
                )
                DropdownMenuItem(
                    text = { Text(text = "Wajib", color = DeepBlue) },
                    onClick = {
                        savingtype  = "Wajib"
                        expandedSavingType = false
                    }
                )
                DropdownMenuItem(
                    text = { Text(text = "Sukarela", color = DeepBlue) },
                    onClick = {
                        savingtype  = "Sukarela"
                        expandedSavingType = false
                    }
                )
            }
        }

    }
}

fun buildBarsFromTransaksi(
    transaksi: List<TransaksiSimpanan>
): List<Bars> {
    if (transaksi.isEmpty()) return emptyList()

    return transaksi
        .groupBy { extractMonth(it.tanggal) }
        .map { (month, list) ->
            Bars(
                label = month,
                values = list.map {
                    Bars.Data(
                        label = it.tipe.toString(),
                        value = it.jumlah.toDouble(),
                        color = SolidColor(Color(0xFF2563EB))
                    )
                }
            )
        }
        .filter { it.values.isNotEmpty() } // 🔥 CRITICAL
}

@Composable
fun AdminUserSavingStatistics(
    transaksi: List<TransaksiSimpanan>,
    modifier: Modifier = Modifier
) {
    val barsData = remember(transaksi) {
        buildBarsFromTransaksi(transaksi)
    }

    if (barsData.isEmpty() || barsData.all { it.values.isEmpty() }) {
        // SAFE fallback UI
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            Text("Belum ada data transaksi")
        }
        return
    }

    ColumnChart(
        modifier = modifier.border(2.dp, Color.Gray).padding(15.dp),
        data = barsData,
        barProperties = BarProperties(spacing = 3.dp)
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AdminUserSavingPage(navController: NavHostController, userId: String, viewModel: SimpananViewModelUser) {

    LaunchedEffect(userId) {
        viewModel.fetchAllTransaksiSimpanan(userId)
        viewModel.getUserName(userId)
    }

    val TransaksiSimpanan by viewModel.TransaksiSimpananList.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val username by viewModel.name.collectAsState()

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showImageSheet by remember { mutableStateOf(false) }
    var selectedImageUrl by remember { mutableStateOf<String?>(null) }

    if (showImageSheet && selectedImageUrl != null) {
        ModalBottomSheet(
            onDismissRequest = { showImageSheet = false },
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Bukti Pembayaran",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.Black
                )

                Spacer(Modifier.height(16.dp))

                AsyncImage(
                    model = "http://192.168.1.194:3000$selectedImageUrl",
                    contentDescription = "Bukti Pembayaran",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp)
                )

                Spacer(Modifier.height(16.dp))

                Button(
                    onClick = { showImageSheet = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Tutup")
                }
            }
        }
    }




    val searchEmail = remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp, vertical = 15.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 5.dp)
        ) {
            TopBarWithDropDown(
                modifier = Modifier
                    .fillMaxWidth()
                    .zIndex(3f)
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(bottom = 25.dp),
                searchEmail = searchEmail,
                navController = navController,
                name = username?: ""
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 10.dp)
            ) {
                item {
                    AdminUserSavingStatistics(
                        transaksi = TransaksiSimpanan,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                    )
                    Text("Riwayat transaksi", fontSize = 25.sp, modifier = Modifier.padding(top = 10.dp))
                }

                when {
                    isLoading -> {
                        item {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }

                    errorMessage != null -> {
                        item {
                            Text("Error: $errorMessage")
                        }
                    }

                    else -> {
                        items(
                            items = TransaksiSimpanan,
                            key = { it.id!! }
                        ) { transaksi ->
                            CardSavings(
                                header = transaksi.jumlah,
                                subheader = transaksi.keterangan,
                                description = transaksi.tipe,
                                tanggal = transaksi.tanggal,
                                buktiImageUrl = transaksi.buktiImageUrl,
                                onViewBukti = { imageUrl ->
                                    selectedImageUrl = imageUrl
                                    showImageSheet = true
                                },
                                onDelete = {
                                    viewModel.DeleteSimpanan(
                                        userId = userId,
                                        simpananId = transaksi.id!!
                                    )
                                }
                            )
                        }
                    }
                }
            }


        }

    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, name = "Admin saving user", showSystemUi = true, device = Devices.PIXEL_5)
@Composable
fun DashboardAdminSavingPreview() {
    KoperasiSimpanPinjamTheme {
        val userId = "693d399289449e4a8e0698b1"
        val repository = UserRepositorySimpanan(api = RetrofitClient.instance)
        val viewModel: SimpananViewModelUser = viewModel(
            factory = SimpananViewModelFactoryUser(repository)
        )
        val navController = rememberNavController()
        AdminUserSavingPage(navController = navController, userId = userId, viewModel = viewModel)
    }
}

