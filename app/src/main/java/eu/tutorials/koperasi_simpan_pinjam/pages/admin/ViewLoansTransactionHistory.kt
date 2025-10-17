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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
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
import ir.ehsannarmani.compose_charts.ColumnChart
import ir.ehsannarmani.compose_charts.models.BarProperties
import ir.ehsannarmani.compose_charts.models.Bars


@Composable
fun AdminUserLoansStatistics(modifier: Modifier = Modifier) {
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
                        Bars.Data(label = "Payment history", value = 50.0, color = SolidColor(colors[0])),

                    ),
                ),
                Bars(
                    label = "Feb",
                    values = listOf(
                        Bars.Data(label = "Payment history", value = 20.0, color = SolidColor(colors[0])),

                    ),
                ),
                Bars(
                    label = "March",
                    values = listOf(
                        Bars.Data(label = "Payment history", value = 10.0, color = SolidColor(colors[0])),

                    ),
                ),
                Bars(
                    label = "April",
                    values = listOf(
                        Bars.Data(label = "Payment history", value = 10.0, color = SolidColor(colors[0])),

                    ),
                ),
                Bars(
                    label = "May",
                    values = listOf(
                        Bars.Data(label = "Payment history", value = 5.0, color = SolidColor(colors[0])),

                    ),
                ),
                Bars(
                    label = "June",
                    values = listOf(
                        Bars.Data(label = "Payment history", value = 3.0, color = SolidColor(colors[0])),

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
fun CardLoans(
    modifier: Modifier = Modifier,
    header: String,
    subheader: String,
) {
    val colors = listOf(
        Color(0xFF1E3A8A), // navy blue
    )
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
            }

        }
    }
}

@Composable
fun CardLoansHeader(
    modifier: Modifier = Modifier,
    header: String,
    subheader: String,
    description: String,
) {
    var expanded by remember { mutableStateOf(false) }
    val colors = listOf(
        Color(0xFF7C3AED), // navy blue
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

            }

            // Animated expansion
            AnimatedVisibility(visible = expanded) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(modifier = Modifier.padding(top = 5.dp), text = description)
                }
            }
        }
    }
}
@Composable
fun ViewLoansTransactionHistoryPage(navController: NavHostController){
    Column (modifier = Modifier.fillMaxSize().padding(start = 10.dp, end = 10.dp, top = 25.dp)){
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().padding(vertical = 15.dp)){
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
            Column(){
                Text("Howard's loan", fontSize = 25.sp)
                Row(verticalAlignment = Alignment.CenterVertically){
                    Text(
                        text = "Active",
                        color = Color.White,
                        modifier = Modifier
                            .background(
                                color = Color.Green,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(5.dp)
                    )
                    Text(text = "Due 01/03/2026", modifier = Modifier.padding(horizontal = 5.dp))
                }


            }

        }

        AdminUserLoansStatistics(
            modifier = Modifier
                .border(2.dp, Color.Gray)
                .fillMaxWidth()
                .height(300.dp)
                .padding(vertical = 35.dp, horizontal = 10.dp)// half screen
        )

        val scrollState = rememberScrollState()
        Column( modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(top = 10.dp)){
            CardLoansHeader(
                modifier= Modifier ,
                header= "$ 53.30",
                subheader= "Remainder",
                description= "3% Interest | 2% Fine" ,
            )
            Text("Payment History", fontSize = 25.sp)
            repeat(2){
                CardLoans(
                    modifier=  Modifier,
                    header= "$5.31",
                    subheader= "01/03/2025",
                )
            }

        }
    }

}

@Preview(showBackground = true, name = "Manage loans", showSystemUi = true, device = Devices.PIXEL_5)
@Composable
fun ViewLoansTransactionPreview() {
    KoperasiSimpanPinjamTheme {
        val navController = rememberNavController()
        ViewLoansTransactionHistoryPage(navController = navController)
    }
}