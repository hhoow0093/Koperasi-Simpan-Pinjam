package eu.tutorials.koperasi_simpan_pinjam.pages.admin

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
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
    routeLoansViewing : String,
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
                if(subheader == "On progress"){
                    Text(
                        text = subheader,
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White,
                        modifier = Modifier
                            .background(
                                color = Color(0XFFe05600),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )

                }
                else if (subheader == "Active"){
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
                else if (subheader == "Settled"){
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
                        if(subheader == "On progress"){
                            Button(// muncul modal untuk atur denda dan bunga
                                onClick = { onAcceptClick() },
                            ) {
                                Text("Accept")
                            }
                            Button(// muncul modal untuk tolak. Biasanya diberi notifikasi
                                onClick = { onRejectClick() },
                            ) {
                                Text("Reject")
                            }
                        }else if (subheader == "Settled" || subheader == "Active"){
                            Button(// lihat histori pembayaran angsuran
                                onClick = { navController.navigate(route = routeLoansViewing) },
                            ) {
                                Text("View history")
                            }

                        }

                    }
                    Text(color = DeepBlue, text = "$500.00", modifier = Modifier.padding(horizontal = 8.dp))

                }
            }
        }
    }
}
@Composable
fun InterestSlider() {
    var interest by remember { mutableStateOf(5f) }
    var fine by remember { mutableStateOf(3f) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text("Interest: ${interest.toInt()}%", style = MaterialTheme.typography.bodyLarge, color = DeepBlue)

        Slider(
            value = interest,
            onValueChange = { interest = it },
            valueRange = 0f..10f,
            steps = 9,
            modifier = Modifier.fillMaxWidth()
        )
        Text("Fine: ${fine.toInt()}%", style = MaterialTheme.typography.bodyLarge, color = DeepBlue)
        Slider(
            value = fine,
            onValueChange = { fine = it },
            valueRange = 0f..10f,
            steps = 9,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminUserLoansPage(navController: NavHostController){
    val showRejectSheet = remember { mutableStateOf(false) }
    val showSuccessDialog = remember { mutableStateOf(false) }
    val showInterestAndFineModal = remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    if(showInterestAndFineModal.value){
        ModalBottomSheet(
            onDismissRequest = { showInterestAndFineModal.value = false },
            sheetState = sheetState,
            containerColor = white,
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

                InterestSlider()

                Spacer(Modifier.height(20.dp))

                Button(
                    onClick = {
                        showInterestAndFineModal.value = false
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = DeepBlue)
                ) {
                    Text("Set", color = white)
                }

                TextButton(onClick = { showInterestAndFineModal.value = false }) {
                    Icon(Icons.Default.Close, contentDescription = null)
                    Text("Close")
                }
            }
        }

    }

    if (showRejectSheet.value) {
        ModalBottomSheet(
            onDismissRequest = { showRejectSheet.value = false },
            sheetState = sheetState,
            containerColor = white,
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
                        // send rejection notice to user
                        showRejectSheet.value = false
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = DeepBlue)
                ) {
                    Text("Send", color = white)
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
                TextButton(onClick = { showSuccessDialog.value = false }) {
                    Text("NO")
                }
                TextButton(onClick = {
                    showSuccessDialog.value = false
                    showInterestAndFineModal.value = true
                }) {
                    Text("OK")
                }
            },
            title = { Text(text = "Are you sure? ", color = DeepBlue) },
            text = { Text("Loans will be active in status", color = DeepBlue) }
        )
    }
    val searchEmail = remember { mutableStateOf("") }
    val scrollState = rememberScrollState()

    Column (modifier = Modifier.fillMaxSize().padding(start = 10.dp, end = 10.dp, top = 25.dp)){
        TopBar(
            modifier = Modifier
                .fillMaxWidth()
                .zIndex(3f)
                .background(MaterialTheme.colorScheme.surface)
                .padding(bottom = 15.dp),
            searchEmail = searchEmail,
            navController = navController
        )
        Column (
            modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(top = 10.dp)){
            repeat(2){
                ExpandableCardLoansOnProgress(
                    modifier = Modifier,
                    header = "Howard",
                    subheader = "On progress",
                    description =  "Building Business requirements",
                    navController = navController,
                    routeLoansViewing =  "ViewLoansTransactionHistory",
                    onAcceptClick = {showSuccessDialog.value = true},
                    onRejectClick = {showRejectSheet.value = true}
                )

            }
            repeat(2){
                ExpandableCardLoansOnProgress(
                    modifier = Modifier,
                    header = "Howard",
                    subheader = "Active",
                    description =  "Paying rent",
                    navController = navController,
                    routeLoansViewing =  "ViewLoansTransactionHistory",
                    onAcceptClick = {},
                    onRejectClick = {}
                )

            }
            repeat(2){
                ExpandableCardLoansOnProgress(
                    modifier = Modifier,
                    header = "Howard",
                    subheader = "Settled",
                    description =  "College tuition",
                    navController = navController,
                    routeLoansViewing =  "ViewLoansTransactionHistory",
                    onAcceptClick = {},
                    onRejectClick = {}
                )

            }
            repeat(2){
                ExpandableCardLoansOnProgress(
                    modifier = Modifier,
                    header = "Howard",
                    subheader = "Rejected",
                    description =  "eating",
                    navController = navController,
                    routeLoansViewing =  "ViewLoansTransactionHistory",
                    onAcceptClick = {},
                    onRejectClick = {}
                )

            }

        }

    }


}

@Preview(showBackground = true, name = "Manage loans", showSystemUi = true, device = Devices.PIXEL_5)
@Composable
fun AdminUserLoansPreview() {
    KoperasiSimpanPinjamTheme {
        val navController = rememberNavController()
        AdminUserLoansPage(navController = navController)
    }
}