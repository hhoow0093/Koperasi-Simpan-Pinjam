package eu.tutorials.koperasi_simpan_pinjam.fragments

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eu.tutorials.koperasi_simpan_pinjam.Martel

@Composable
fun ButtonFilled(modifier: Modifier = Modifier, label: String, function: () -> Unit){
    Button(
        onClick =  function,
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp)

    ) {
        Text(text = label,
            style = TextStyle(
                fontFamily = Martel,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            ),
        )
    }
}