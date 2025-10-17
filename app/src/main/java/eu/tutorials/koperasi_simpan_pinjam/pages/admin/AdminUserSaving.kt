package eu.tutorials.koperasi_simpan_pinjam.pages.admin

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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import eu.tutorials.koperasi_simpan_pinjam.fragments.admin.AdminSearch
import eu.tutorials.koperasi_simpan_pinjam.ui.theme.DeepBlue
import eu.tutorials.koperasi_simpan_pinjam.ui.theme.SoftLavender
import eu.tutorials.koperasi_simpan_pinjam.ui.theme.white

@Composable
fun CardSavings(
    modifier: Modifier = Modifier,
    header: String,
    subheader: String,
    description: String,
) {
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
                Text(text = header, fontSize = 30.sp)
            }

            // Main Info
            Column(
                modifier = modifier.padding(horizontal = 24.dp, vertical = 14.dp)
            ) {
                if(subheader == "Mandatory"){
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

                }else if(subheader == "Volunteer"){
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
                    Text("01/03/2025")
                    Text(modifier = Modifier.padding(top = 5.dp), text = description)
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarWithDropDown(modifier: Modifier, searchEmail: MutableState<String>, navController: NavController){
    var expandedSavingType by remember { mutableStateOf(false) }
    var savingtype by remember { mutableStateOf("Browse Saving type") }
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
            Text("Howard's Savings", fontSize = 25.sp)
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
                    text = { Text(text = "Basic", color = DeepBlue) },
                    onClick = {
                        savingtype = "Basic"
                        expandedSavingType = false
                    }
                )
                DropdownMenuItem(
                    text = { Text(text = "Mandatory", color = DeepBlue) },
                    onClick = {
                        savingtype  = "Mandatory"
                        expandedSavingType = false
                    }
                )
                DropdownMenuItem(
                    text = { Text(text = "Volunteer", color = DeepBlue) },
                    onClick = {
                        savingtype  = "Volunteer"
                        expandedSavingType = false
                    }
                )
            }
        }

    }
}

@Composable
fun AdminUserSavingStatistics(modifier: Modifier = Modifier) {
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
                        Bars.Data(label = "Mandatory", value = 50.0, color = SolidColor(colors[0])),
                        Bars.Data(label = "Volunteer", value = 70.0, color = SolidColor(colors[1])),
                        Bars.Data(label = "Basic", value = 70.0, color = SolidColor(colors[2]))
                    ),
                ),
                Bars(
                    label = "Feb",
                    values = listOf(
                        Bars.Data(label = "Mandatory", value = 80.0, color = SolidColor(colors[0])),
                        Bars.Data(label = "Volunteer", value = 60.0, color = SolidColor(colors[1])),
                        Bars.Data(label = "Basic", value = 70.0, color = SolidColor(colors[2]))
                    ),
                ),
                Bars(
                    label = "March",
                    values = listOf(
                        Bars.Data(label = "Mandatory", value = 50.0, color = SolidColor(colors[0])),
                        Bars.Data(label = "Volunteer", value = 70.0, color = SolidColor(colors[1])),
                        Bars.Data(label = "Basic", value = 70.0, color = SolidColor(colors[2]))
                    ),
                ),
                Bars(
                    label = "April",
                    values = listOf(
                        Bars.Data(label = "Mandatory", value = 80.0, color = SolidColor(colors[0])),
                        Bars.Data(label = "Volunteer", value = 60.0, color = SolidColor(colors[1])),
                        Bars.Data(label = "Basic", value = 70.0, color = SolidColor(colors[2]))
                    ),
                ),
                Bars(
                    label = "May",
                    values = listOf(
                        Bars.Data(label = "Mandatory", value = 80.0, color = SolidColor(colors[0])),
                        Bars.Data(label = "Volunteer", value = 60.0, color = SolidColor(colors[1])),
                        Bars.Data(label = "Basic", value = 70.0, color = SolidColor(colors[2]))
                    ),
                ),
                Bars(
                    label = "June",
                    values = listOf(
                        Bars.Data(label = "Mandatory", value = 80.0, color = SolidColor(colors[0])),
                        Bars.Data(label = "Volunteer", value = 60.0, color = SolidColor(colors[1])),
                        Bars.Data(label = "Basic", value = 70.0, color = SolidColor(colors[2]))
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
fun AdminUserSavingPage(navController: NavHostController) {
    val scrollState = rememberScrollState()
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
                navController = navController
            )

            AdminUserSavingStatistics(
                modifier = Modifier
                    .border(2.dp, Color.Gray)
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(vertical = 35.dp, horizontal = 10.dp)// half screen
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(top = 10.dp)
            ){
                Text("Payment History", fontSize = 25.sp)

                repeat(2) {
                    CardSavings(
                        modifier = Modifier,
                        header = "$ 2.00",
                        subheader = "Mandatory",
                        description = "Debit"
                    )
                }
                repeat(2) {
                    CardSavings(
                        modifier = Modifier,
                        header = "$ 2.20",
                        subheader = "Volunteer",
                        description = "Cash"
                    )
                }
                repeat(2) {
                    CardSavings(
                        modifier = Modifier,
                        header = "$ 3.20",
                        subheader = "Basic",
                        description = "Credit"
                    )
                }

            }

        }

    }
}

@Preview(showBackground = true, name = "Admin saving user", showSystemUi = true, device = Devices.PIXEL_5)
@Composable
fun DashboardAdminSavingPreview() {
    KoperasiSimpanPinjamTheme {
        val navController = rememberNavController()
        AdminUserSavingPage(navController)
    }
}

