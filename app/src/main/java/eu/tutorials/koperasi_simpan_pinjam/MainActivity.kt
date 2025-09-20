package eu.tutorials.koperasi_simpan_pinjam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eu.tutorials.koperasi_simpan_pinjam.ui.theme.KoperasiSimpanPinjamTheme
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextFieldColors
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavHostController
import eu.tutorials.koperasi_simpan_pinjam.navigation.AppNavHost
import androidx.navigation.compose.rememberNavController
import eu.tutorials.koperasi_simpan_pinjam.pages.AuthenticationPage

val Martel = FontFamily(
    Font(R.font.martelsans_light, FontWeight.Light),
    Font(R.font.martelsans_bold, FontWeight.Bold)
)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KoperasiSimpanPinjamTheme {
                KoperasiSimpanPinjamTheme {
                    val navController = rememberNavController()
                    AppNavHost(navController)
                }
            }
        }
    }
}



@Preview(showBackground = true, name = "Register page", showSystemUi = true, device = Devices.PIXEL_5)
@Composable
fun RegisterPreview() {
    KoperasiSimpanPinjamTheme {
        val navController = rememberNavController()
        AppNavHost(navController)
    }
}

@Preview(showBackground = true, name = "Authentication page", showSystemUi = true, device = Devices.PIXEL_5)
@Composable
fun AuthenticationPreview(){
    KoperasiSimpanPinjamTheme {
        val navController = rememberNavController()
        AuthenticationPage(navController)
    }
}