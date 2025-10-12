package eu.tutorials.koperasi_simpan_pinjam.pages

import android.util.Log
import android.widget.Toast
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import eu.tutorials.koperasi_simpan_pinjam.API.LoginRequest
import eu.tutorials.koperasi_simpan_pinjam.API.RetrofitClient
import eu.tutorials.koperasi_simpan_pinjam.Martel
import eu.tutorials.koperasi_simpan_pinjam.R
import eu.tutorials.koperasi_simpan_pinjam.fragments.ButtonFilled
import eu.tutorials.koperasi_simpan_pinjam.fragments.EmailAlignedTextField
import eu.tutorials.koperasi_simpan_pinjam.fragments.PasswordTextField
import kotlinx.coroutines.launch
import org.json.JSONObject

@Composable
fun LoginPage(navController: NavHostController, modifier: Modifier = Modifier){
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // Local states for input values
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

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
                EmailAlignedTextField(
                    value = email,
                    onValueChange = { email = it }
                )
            }
            Surface (modifier = modifier
                .padding(vertical = 15.dp)
                .shadow(
                    elevation = 6.dp, // shadow size
                    shape = RoundedCornerShape(8.dp), // rounded corners
                    clip = false // don’t clip, so shadow shows outside
                )
            ){
                PasswordTextField(
                    label= "Enter your password",
                    modifier,
                    password = password,
                    onPasswordChange = {password = it})
            }
            Surface (
                modifier = modifier
                    .padding(vertical = 5.dp)
            ){
                ButtonFilled(modifier, "Login"){
                    coroutineScope.launch {
                        try {
                            val request = LoginRequest(email, password)
                            val response = RetrofitClient.instance.loginUser(request)

                            if (response.isSuccessful) {
                                val message = response.body()?.message ?: "No message"
                                Toast.makeText(context, "✅ $message", Toast.LENGTH_LONG).show()
                                navController.navigate("dashboard")
                            } else {
                                val errorBody = response.errorBody()?.string()
                                val message = errorBody?.let {
                                    JSONObject(it).optString("message", "Unknown error")
                                }
                                Toast.makeText(context, "❌ Error: $message", Toast.LENGTH_LONG).show()
                            }

                        } catch (e: Exception) {
                            Toast.makeText(context, "⚠️ ${e.message}", Toast.LENGTH_LONG).show()
                            e.printStackTrace()
                        }
                    }
                }
            }

        }
    }
}