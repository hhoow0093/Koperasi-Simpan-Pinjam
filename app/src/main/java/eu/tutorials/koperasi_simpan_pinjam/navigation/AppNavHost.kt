package eu.tutorials.koperasi_simpan_pinjam.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import eu.tutorials.koperasi_simpan_pinjam.pages.AuthenticationPage
import eu.tutorials.koperasi_simpan_pinjam.pages.DashBoard
import eu.tutorials.koperasi_simpan_pinjam.pages.LoginPage
import eu.tutorials.koperasi_simpan_pinjam.pages.RegisterPage

@Composable
fun AppNavHost(navController : NavHostController){
    NavHost(navController = navController, startDestination = "register"){
        composable (route = "register"){ RegisterPage(navController) }
        composable (route = "login"){ LoginPage(navController) }
        composable (route = "authentication"){ AuthenticationPage(navController) }
        composable (route = "dashboard"){ DashBoard(navController) }
    }
}