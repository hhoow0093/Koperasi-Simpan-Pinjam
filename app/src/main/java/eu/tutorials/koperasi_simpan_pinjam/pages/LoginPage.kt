package eu.tutorials.koperasi_simpan_pinjam.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import eu.tutorials.koperasi_simpan_pinjam.Martel
import eu.tutorials.koperasi_simpan_pinjam.R
import eu.tutorials.koperasi_simpan_pinjam.fragments.ButtonFilled
import eu.tutorials.koperasi_simpan_pinjam.fragments.EmailAlignedTextField
import eu.tutorials.koperasi_simpan_pinjam.fragments.PasswordTextField

@Composable
fun LoginPage(navController: NavHostController, modifier: Modifier = Modifier){
    Surface (
        modifier = modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // Logo
            Image(
                painter = painterResource(id = R.drawable.logo_kop),
                contentDescription = "Logo",
            )

            // Title Text
            Text(
                text = "Welcome back",
                style = TextStyle(
                    fontFamily = Martel,
                    fontWeight = FontWeight.Light,
                    fontSize = 20.sp
                ),
                modifier = Modifier
                    .fillMaxWidth() // take full available width
                    .padding(top = 16.dp), // optional spacing
                textAlign = androidx.compose.ui.text.style.TextAlign.Start
            )
            Surface (modifier = modifier
                .padding(vertical = 15.dp)
                .shadow(
                    elevation = 6.dp, // shadow size
                    shape = RoundedCornerShape(8.dp), // rounded corners
                    clip = false // don’t clip, so shadow shows outside
                )
            ){
                EmailAlignedTextField()
            }
            Surface (modifier = modifier
                .padding(vertical = 15.dp)
                .shadow(
                    elevation = 6.dp, // shadow size
                    shape = RoundedCornerShape(8.dp), // rounded corners
                    clip = false // don’t clip, so shadow shows outside
                )
            ){
                PasswordTextField("Enter your password", modifier)
            }
            Surface (
                modifier = modifier
                    .padding(vertical = 5.dp)
            ){
                ButtonFilled(modifier, "Login"){navController.navigate("dashboard")}
            }

        }
    }
}