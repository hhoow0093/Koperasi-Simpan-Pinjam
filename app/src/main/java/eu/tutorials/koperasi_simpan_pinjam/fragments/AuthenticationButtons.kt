package eu.tutorials.koperasi_simpan_pinjam.fragments

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun AuthenticationButtons(navController: NavHostController, modifier : Modifier = Modifier ){
    Column(){
        ButtonFilled(modifier = modifier.padding(vertical = 10.dp), label = "Login") { navController.navigate("login") }
        ButtonFilled(modifier = modifier.padding(vertical = 10.dp), label = "Register") { navController.navigate("register") }
    }
}