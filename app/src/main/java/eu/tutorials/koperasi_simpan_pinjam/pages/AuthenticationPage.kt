package eu.tutorials.koperasi_simpan_pinjam.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import eu.tutorials.koperasi_simpan_pinjam.Martel
import eu.tutorials.koperasi_simpan_pinjam.R
import eu.tutorials.koperasi_simpan_pinjam.fragments.AuthenticationButtons
import eu.tutorials.koperasi_simpan_pinjam.fragments.ButtonFilled
import eu.tutorials.koperasi_simpan_pinjam.fragments.SocialLogin
import eu.tutorials.koperasi_simpan_pinjam.ui.theme.KoperasiSimpanPinjamTheme


@Composable
fun AuthenticationPage(navController: NavHostController, modifier: Modifier = Modifier){
    Surface(
        modifier = modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo section
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_kop),
                    contentDescription = "Logo",
                    modifier = Modifier.padding(top = 40.dp)
                )
            }
            // button sections
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                ,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                AuthenticationButtons(navController, modifier)
                SocialLogin()
            }
        }
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