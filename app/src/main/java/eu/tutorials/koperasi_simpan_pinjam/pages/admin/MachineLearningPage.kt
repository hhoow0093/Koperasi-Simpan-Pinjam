package eu.tutorials.koperasi_simpan_pinjam.pages.admin

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import eu.tutorials.koperasi_simpan_pinjam.data.API.RetrofitClient
import eu.tutorials.koperasi_simpan_pinjam.data.repository.admin.MLRepository
import eu.tutorials.koperasi_simpan_pinjam.data.viewmodel.admin.MachineLearningViewModel
import eu.tutorials.koperasi_simpan_pinjam.data.viewmodel.admin.MachineLearningViewModelFactory
import eu.tutorials.koperasi_simpan_pinjam.ui.theme.DeepBlue
import eu.tutorials.koperasi_simpan_pinjam.ui.theme.white
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

fun formatRupiah(value: Float): String {
    val localeID = Locale("in", "ID")
    val formatter = NumberFormat.getCurrencyInstance(localeID)
    formatter.maximumFractionDigits = 0
    return formatter.format(value)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MachineLearningPage(
    modifier: Modifier = Modifier,
    navController: NavController
) {

    val viewModel: MachineLearningViewModel = viewModel(
        factory = MachineLearningViewModelFactory(
            MLRepository(RetrofitClient.instance)
        )
    )
    var age by remember { mutableFloatStateOf(1f) }
    var income by remember { mutableFloatStateOf(1f) }
    var credit by remember { mutableFloatStateOf(1f) }
    var loanFreq by remember { mutableFloatStateOf(1f) }

    var showSheet by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        /* 🔙 Back Button */
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .background(DeepBlue, RoundedCornerShape(16.dp))
            ) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = white
                    )
                }
            }
        }

        Spacer(Modifier.height(20.dp))

        NumericSlider("Umur", age, 100f) { age = it }

        NumericSlider(
            label = "Penghasilan (bulanan)",
            value = income,
            max = 20_000_000f,
            isCurrency = true
        ) { income = it }

        NumericSlider("Poin kredit koperasi", credit, 1000f) { credit = it }

        NumericSlider("Jumlah pinjaman per bulan", loanFreq, 10f) { loanFreq = it }

        Spacer(Modifier.height(24.dp))

        /* 🚀 Submit */
        Button(
            onClick = {
                showSheet = true
                viewModel.predict(age, income, credit, loanFreq)
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text("Prediksi risiko")
        }
    }

    /* 📦 Modal Bottom Sheet */
    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSheet = false }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Hasil Prediksi",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = DeepBlue
                )

                Spacer(Modifier.height(16.dp))

                when {
                    viewModel.isLoading -> {
                        CircularProgressIndicator()
                    }

                    viewModel.result != null -> {
                        println(viewModel.result)
                        Text(
                            text = if (viewModel.result!!.defaultPrediction == 1)
                                "Anggota tidak dapat bayar"
                            else
                                "Angota dapat bayar",
                            fontSize = 18.sp,
                            color = DeepBlue
                        )

                        Spacer(Modifier.height(8.dp))

                        Text(
                            text = "risiko probabilitas: ${viewModel.result!!.riskProbability}",
                            fontSize = 16.sp,
                            color = DeepBlue
                        )
                    }

                    viewModel.errorMessage != null -> {
                        Text(viewModel.errorMessage!!, color = Color.Red)
                    }
                }


                Spacer(Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun NumericSlider(
    label: String,
    value: Float,
    max: Float,
    isCurrency: Boolean = false,
    onValueChange: (Float) -> Unit
){
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(label, fontWeight = FontWeight.Medium)

        Spacer(Modifier.height(4.dp))

        OutlinedTextField(
            value = if (isCurrency)
                formatRupiah(value)
            else
                value.toInt().toString(),

            onValueChange = {},
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(
                color = DeepBlue,
                fontSize = 16.sp
            )
        )

        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = 0f..max
        )

        Spacer(Modifier.height(12.dp))
    }
}

