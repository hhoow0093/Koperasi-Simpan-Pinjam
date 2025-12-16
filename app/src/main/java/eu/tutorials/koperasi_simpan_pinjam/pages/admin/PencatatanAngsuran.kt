package eu.tutorials.koperasi_simpan_pinjam.pages.admin

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import eu.tutorials.koperasi_simpan_pinjam.data.viewmodel.admin.PembayaranLoanViewModel
import eu.tutorials.koperasi_simpan_pinjam.ui.theme.DeepBlue
import eu.tutorials.koperasi_simpan_pinjam.ui.theme.KoperasiSimpanPinjamTheme
import eu.tutorials.koperasi_simpan_pinjam.ui.theme.SoftLavender
import eu.tutorials.koperasi_simpan_pinjam.fragments.admin.AdminSearch
import eu.tutorials.koperasi_simpan_pinjam.ui.theme.white


data class LoanItem(
    val id: String,
    val status: String,
    val tanggal: String,
    val buktiImageUrl: String?,
    val angsuranKe: Int
)



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarSearchLoans(
    searchQuery: MutableState<String>,
    onSearchClick: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = searchQuery.value,
                onValueChange = { searchQuery.value = it },
                label = { Text("Cari nasabah") },
                modifier = Modifier.weight(1f),
                singleLine = true
            )

            Button(
                onClick = onSearchClick,
                modifier = Modifier.height(56.dp)
            ) {
                Text("Cari")
            }
        }
    }
}


@Composable
fun ExpandableLoanCard(
    loan: LoanItem,
    onViewImage: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy
                )
            )
            .clickable { expanded = !expanded },
        elevation = CardDefaults.cardElevation(4.dp),
        border = BorderStroke(1.dp, Color.Black),
        colors = CardDefaults.cardColors(containerColor = SoftLavender)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {

            // 🔥 LARGE HEADER
            Text(
                text = "Angsuran ke - ${loan.angsuranKe}",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = DeepBlue
            )

            Spacer(Modifier.height(4.dp))

            // Optional sub info
            Text(
                text = loan.tanggal,
                fontSize = 13.sp,
                color = Color.DarkGray
            )

            Spacer(Modifier.height(10.dp))

            AnimatedVisibility(visible = expanded) {
                Column(
                    modifier = Modifier.padding(top = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    Button(
                        onClick = {
                            loan.buktiImageUrl?.let { onViewImage(it) }
                        },
                        enabled = loan.buktiImageUrl != null,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Lihat Bukti")
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PencatatanAngsuran( navController: NavHostController, viewModel: PembayaranLoanViewModel, loanId: String) {

    LaunchedEffect(loanId) {
        viewModel.fetchPembayaranByLoanId(loanId)
    }

    val pembayaranList by viewModel.pembayaranList.collectAsState()
    val searchEmail = remember { mutableStateOf("") }


    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showImageSheet by remember { mutableStateOf(false) }
    var Id by remember { mutableStateOf<String?>(null) }

    if (showImageSheet && Id != null) {
        ModalBottomSheet(
            onDismissRequest = { showImageSheet = false },
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Bukti Pinjaman", fontSize = 20.sp, color = DeepBlue)

                Spacer(Modifier.height(12.dp))

                AsyncImage(
                    model = "http://192.168.1.194:3000/pembayaran-loan/image/$Id",
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp)
                )

                Spacer(Modifier.height(12.dp))

                Button(
                    onClick = { showImageSheet = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Tutup")
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        TopBar(modifier = Modifier, searchEmail = searchEmail, navController = navController)

        Spacer(Modifier.height(12.dp))

        Spacer(Modifier.height(12.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            itemsIndexed(pembayaranList) { index, pembayaran ->
                val loanItem = LoanItem(
                    id = pembayaran._id,
                    status = "Dibayar",
                    tanggal = pembayaran.createdAt,
                    buktiImageUrl = pembayaran._id,
                    angsuranKe = index + 1
                )

                ExpandableLoanCard(
                    loan = loanItem,
                    onViewImage = { imageId ->
                        Id = imageId
                        showImageSheet = true
                    }
                )
            }
        }
    }
}




