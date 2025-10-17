package eu.tutorials.koperasi_simpan_pinjam.pages.admin

import android.app.DatePickerDialog
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import eu.tutorials.koperasi_simpan_pinjam.Martel
import eu.tutorials.koperasi_simpan_pinjam.fragments.admin.AdminSearch
import eu.tutorials.koperasi_simpan_pinjam.ui.theme.DeepBlue
import eu.tutorials.koperasi_simpan_pinjam.ui.theme.KoperasiSimpanPinjamTheme
import eu.tutorials.koperasi_simpan_pinjam.ui.theme.SoftLavender
import eu.tutorials.koperasi_simpan_pinjam.ui.theme.white
import ir.ehsannarmani.compose_charts.ColumnChart
import ir.ehsannarmani.compose_charts.models.BarProperties
import ir.ehsannarmani.compose_charts.models.Bars
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


@Composable
fun AdminUserProfitStatistics(modifier: Modifier = Modifier) {
    val colors = listOf(
        Color(0xFF1E3A8A), // navy blue
        Color(0xFF2563EB), // dark blue
        Color(0xFF7C3AED)  // deep purple
    )

    ColumnChart(
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(horizontal = 22.dp),
        data = remember {
            listOf(
                Bars(
                    label = "Jan",
                    values = listOf(
                        Bars.Data(label = "Profit", value = 50.0, color = SolidColor(colors[0])),
                        Bars.Data(label = "loss", value = 30.0, color = SolidColor(colors[1])),

                        ),
                ),
                Bars(
                    label = "Feb",
                    values = listOf(
                        Bars.Data(label = "Profit", value = 20.0, color = SolidColor(colors[0])),
                        Bars.Data(label = "loss", value = 25.0, color = SolidColor(colors[1])),

                        ),
                ),
                Bars(
                    label = "March",
                    values = listOf(
                        Bars.Data(label = "Profit", value = 10.0, color = SolidColor(colors[0])),
                        Bars.Data(label = "loss", value = 15.0, color = SolidColor(colors[1])),

                        ),
                ),
                Bars(
                    label = "April",
                    values = listOf(
                        Bars.Data(label = "Profit", value = 10.0, color = SolidColor(colors[0])),
                        Bars.Data(label = "loss", value = 23.0, color = SolidColor(colors[1])),

                        ),
                ),
                Bars(
                    label = "May",
                    values = listOf(
                        Bars.Data(label = "Profit", value = 5.0, color = SolidColor(colors[0])),
                        Bars.Data(label = "loss", value = 45.0, color = SolidColor(colors[1])),

                        ),
                ),
                Bars(
                    label = "June",
                    values = listOf(
                        Bars.Data(label = "Profit", value = 3.0, color = SolidColor(colors[0])),
                        Bars.Data(label = "loss", value = 35.0, color = SolidColor(colors[1])),

                        ),
                ),
            )
        },
        barProperties = BarProperties(
            spacing = 3.dp,
        ),
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
    )
}
@Composable
fun DateOfBirthField3(
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
fun ExpandableCardReport(
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
                if(subheader == "Monthly"){
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
                }else{
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
                            Text("Download")
                        }
                    }
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportSearch(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    onSearchClick: () -> Unit = {}
) {
    val interactionSource = remember { MutableInteractionSource() }

    Row(
        modifier = modifier
            .height(56.dp)
            .border(1.dp, Color.Black.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
            .background(Color.White, RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicTextField(
            value = value,
            textStyle = TextStyle(fontSize = 15.sp),
            onValueChange = onValueChange,
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            interactionSource = interactionSource,
            decorationBox = { innerTextField ->
                TextFieldDefaults.DecorationBox(
                    value = value,
                    innerTextField = innerTextField,
                    enabled = true,
                    singleLine = true,
                    visualTransformation = VisualTransformation.None,
                    interactionSource = interactionSource,
                    label = {
                        Text(
                            text = "Search Report...",
                            style = TextStyle(
                                fontFamily = Martel,
                                fontWeight = FontWeight.Light,
                                fontSize = 15.sp
                            )
                        )
                    },
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
            }
        )

        IconButton(
            onClick = onSearchClick,
            modifier = Modifier
                .size(40.dp)
                .padding(start = 4.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = Color.Black
            )
        }
    }
}

@Composable
fun TopBarReport(
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
fun AdminReportPage(navController: NavHostController){
    val scrollState = rememberScrollState()
    val searchEmail = remember { mutableStateOf("") }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val showMakeReport = remember { mutableStateOf(false) }

    var title by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var expandedType by remember { mutableStateOf(false) }
    var type by remember {mutableStateOf("Monthly")}

    if(showMakeReport.value){
        ModalBottomSheet(
            onDismissRequest = { showMakeReport.value = false },
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
                    label = { Text("Name") },
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
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth(),
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
                            text = { Text(text = "Monthly", color = DeepBlue) },
                            onClick = {
                                type = "Monthly"
                                expandedType = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(text = "Yearly", color = DeepBlue) },
                            onClick = {
                                type = "Yearly"
                                expandedType = false
                            }
                        )
                    }
                }

                OutlinedTextField(
                    value = desc,
                    onValueChange = { desc = it },
                    label = { Text("Description") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                    textStyle = TextStyle(color = DeepBlue, fontSize = 16.sp),
                )



                DateOfBirthField3(
                    initialDate = date,
                    onDateSelected = { selectedDate -> date = selectedDate },
                    onAgeCalculated = {},
                    label = "report date"
                )


                Spacer(Modifier.height(20.dp))

                Button(
                    onClick = {
                        showMakeReport.value = false
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = DeepBlue)
                ) {
                    Text("Save Changes", color = white)
                }

                TextButton(onClick = { showMakeReport.value = false }) {
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
    ) {
        TopBarReport(modifier = Modifier.padding(vertical = 15.dp), searchEmail = searchEmail, showMakeReport = showMakeReport, navController = navController)
        Column (
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(top = 10.dp)){
            AdminUserProfitStatistics(
                modifier = Modifier
                    .border(2.dp, Color.Gray)
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(vertical = 35.dp, horizontal = 10.dp)// half screen
            )
            repeat(2){
                ExpandableCardReport(
                    modifier = Modifier,
                    header = "Report title",
                    subheader = "Monthly",
                    description = "02/03/2024",
                )
            }
            repeat(2){
                ExpandableCardReport(
                    modifier = Modifier,
                    header = "Report title",
                    subheader = "Yearly",
                    description = "02/03/2025",
                )
            }
        }



    }

}

@Preview(showBackground = true, name = "Manage user", showSystemUi = true, device = Devices.PIXEL_5)
@Composable
fun AdminReportPreview() {
    KoperasiSimpanPinjamTheme {
        val navController = rememberNavController()
        AdminReportPage(navController = navController)
    }
}