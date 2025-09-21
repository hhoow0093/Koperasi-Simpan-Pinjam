package eu.tutorials.koperasi_simpan_pinjam.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import eu.tutorials.koperasi_simpan_pinjam.pages.*

@Composable
fun AppNavHost(navController: NavHostController){
    NavHost(navController = navController, startDestination = "register"){
        composable("dashboard") { DashBoard(navController) }
        composable("login") { LoginPage(navController) }
        composable("register") { RegisterPage(navController) }
        composable("authentication") { AuthenticationPage(navController) }

        composable("dummy/{title}") { backStackEntry ->
            val title = backStackEntry.arguments?.getString("title") ?: "Unknown"
            DummyPage(title)
        }
    }
}
