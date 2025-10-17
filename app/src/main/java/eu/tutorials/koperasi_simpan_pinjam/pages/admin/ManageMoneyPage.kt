package eu.tutorials.koperasi_simpan_pinjam.pages.admin

import android.app.DatePickerDialog
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
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import eu.tutorials.koperasi_simpan_pinjam.ui.theme.DeepBlue
import eu.tutorials.koperasi_simpan_pinjam.ui.theme.KoperasiSimpanPinjamTheme
import eu.tutorials.koperasi_simpan_pinjam.ui.theme.SoftLavender
import eu.tutorials.koperasi_simpan_pinjam.ui.theme.white
import java.util.Calendar


@Composable
fun DateOfBirthField2(
    label: String = "Date of Birth",
    initialDate: String = "",
    onDateSelected: (String) -> Unit,
    onAgeCalculated: (Int) -> Unit
) {
    val context = LocalContext.current
    var date by remember { mutableStateOf(initialDate) }

    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    // ✅ Create dialog once and remember it
    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _, selectedYear, selectedMonth, selectedDay ->
                val formatted = "%04d-%02d-%02d".format(selectedYear, selectedMonth + 1, selectedDay)
                date = formatted
                onDateSelected(formatted)

                // Calculate age
                val today = Calendar.getInstance()
                var age = today.get(Calendar.YEAR) - selectedYear
                if (today.get(Calendar.DAY_OF_YEAR) < Calendar.getInstance().apply {
                        set(selectedYear, selectedMonth, selectedDay)
                    }.get(Calendar.DAY_OF_YEAR)) {
                    age--
                }
                onAgeCalculated(age)
            },
            year,
            month,
            day
        )
    }

    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = date,
            onValueChange = {},
            label = { Text(label) },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            enabled = false,
            textStyle = TextStyle(color = DeepBlue, fontSize = 16.sp),
            colors = OutlinedTextFieldDefaults.colors(
                disabledContainerColor = Color.White,
                disabledTextColor = DeepBlue,
                disabledBorderColor = Color.Gray,
                disabledLabelColor = Color.Gray
            )
        )

        // Transparent overlay to trigger the date picker
        Box(
            modifier = Modifier
                .matchParentSize()
                .clickable {
                    datePickerDialog.show()
                }
        )
    }
}
@Composable
fun ExpandableCardMoney(
    modifier: Modifier = Modifier,
    header: String,
    subheader: String,
    description: String,
) {
    val colors = listOf(
        Color(0xFF1E3A8A), // navy blue
        Color(0xFF2563EB), // dark blue
        Color(0xFF7C3AED)  // deep purple
    )
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
                if(subheader == "Spend"){
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
                }else{
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
                            onClick = { },
                        ) {
                            Text("Delete")
                        }
                        Button(
                            onClick = { },
                        ) {
                            Text("Edit")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TopBarReport2(
    modifier: Modifier = Modifier,
    searchEmail: MutableState<String>,
    showMakeReport: MutableState<Boolean>,
    navController: NavHostController
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ReportSearch(
            modifier = modifier
                .weight(1f)
                .height(56.dp),
            value = searchEmail.value,
            onValueChange = { searchEmail.value = it }
        )

        IconButton(
            onClick = { showMakeReport.value = true},
            modifier = Modifier
                .height(56.dp)
                .aspectRatio(1f)
                .clip(RoundedCornerShape(16.dp))
                .background(DeepBlue)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "add report",
                tint = Color.White
            )
        }

        IconButton(
            onClick = { navController.popBackStack()},
            modifier = Modifier
                .height(56.dp)
                .aspectRatio(1f)
                .clip(RoundedCornerShape(16.dp))
                .background(DeepBlue)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "back",
                tint = Color.White
            )
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageMoneyPage(navController: NavHostController){
    val scrollState = rememberScrollState()
    val searchEmail = remember { mutableStateOf("") }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val showMakeMoneyManagement = remember { mutableStateOf(false) }

    var title by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var myamount by remember{mutableStateOf("")}
    var expandedType by remember { mutableStateOf(false) }
    var type by remember {mutableStateOf("Monthly")}

    if(showMakeMoneyManagement.value){
        ModalBottomSheet(
            onDismissRequest = { showMakeMoneyManagement.value = false },
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
                Text("Report details", fontSize = 22.sp, color = DeepBlue)
                Spacer(Modifier.height(16.dp))

                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle(color = DeepBlue, fontSize = 16.sp)
                )

                OutlinedTextField(
                    value = myamount,
                    onValueChange = { myamount = it },
                    label = { Text("Cash amount") },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle(color = DeepBlue, fontSize = 16.sp)
                )

                ExposedDropdownMenuBox(
                    expanded = expandedType,
                    onExpandedChange = { expandedType = !expandedType }
                ) {
                    OutlinedTextField(
                        value = type,
                        onValueChange = {},
                        label = { Text("Type") },
                        modifier = Modifier.menuAnchor().fillMaxWidth(),
                        readOnly = true,
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedType)
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        ),
                        textStyle = TextStyle(color = DeepBlue, fontSize = 16.sp)
                    )

                    ExposedDropdownMenu(
                        expanded = expandedType,
                        onDismissRequest = { expandedType = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text(text = "Spend", color = DeepBlue) },
                            onClick = {
                                type = "Spend"
                                expandedType = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(text = "Earn", color = DeepBlue) },
                            onClick = {
                                type = "Earn"
                                expandedType = false
                            }
                        )
                    }
                }
                OutlinedTextField(
                    value = desc,
                    onValueChange = { desc = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle(color = DeepBlue, fontSize = 16.sp),
                )

                DateOfBirthField2(
                    initialDate = date,
                    onDateSelected = { selectedDate -> date = selectedDate },
                    onAgeCalculated = {},
                    label = "report date"
                )


                Spacer(Modifier.height(20.dp))

                Button(
                    onClick = {
                        showMakeMoneyManagement.value = false
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = DeepBlue)
                ) {
                    Text("Save Changes", color = white)
                }

                TextButton(onClick = { showMakeMoneyManagement.value = false }) {
                    Icon(Icons.Default.Close, contentDescription = null)
                    Text("Close")
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 10.dp, end = 10.dp, top = 25.dp)
    ){
        TopBarReport2(modifier = Modifier.padding(vertical = 15.dp), searchEmail = searchEmail, showMakeReport = showMakeMoneyManagement, navController = navController)
        Column (
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(top = 10.dp)){
            repeat(2){
                ExpandableCardMoney(
                    modifier = Modifier,
                    header = "Spending title",
                    subheader = "Spend",
                    description = "for maintaining building",
                )
            }
            repeat(2){
                ExpandableCardMoney(
                    modifier = Modifier,
                    header = "Earn",
                    subheader = "Earn",
                    description = "for saving money",
                )
            }
        }

    }



}

@Preview(showBackground = true, name = "Manage loans", showSystemUi = true, device = Devices.PIXEL_5)
@Composable
fun ManageMoneyPreview() {
    KoperasiSimpanPinjamTheme {
        val navController = rememberNavController()
        ManageMoneyPage(navController = navController)
    }
}