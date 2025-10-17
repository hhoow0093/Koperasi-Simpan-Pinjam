package eu.tutorials.koperasi_simpan_pinjam.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import eu.tutorials.koperasi_simpan_pinjam.pages.*
import eu.tutorials.koperasi_simpan_pinjam.pages.admin.DashboardAdminn
import eu.tutorials.koperasi_simpan_pinjam.pages.admin.ManageUserPage
import androidx.lifecycle.viewmodel.compose.viewModel
import eu.tutorials.koperasi_simpan_pinjam.data.API.RetrofitClient
import eu.tutorials.koperasi_simpan_pinjam.data.repository.UserRepository
import eu.tutorials.koperasi_simpan_pinjam.data.viewmodel.DashBoardAdminViewModel
import eu.tutorials.koperasi_simpan_pinjam.data.viewmodel.DashboardViewModelFactory
import eu.tutorials.koperasi_simpan_pinjam.data.viewmodel.ManageUserAdminViewModel
import eu.tutorials.koperasi_simpan_pinjam.data.viewmodel.ManageUserAdminViewModelFactory
import eu.tutorials.koperasi_simpan_pinjam.pages.admin.AdminReportPage
import eu.tutorials.koperasi_simpan_pinjam.pages.admin.AdminTransactionPage
import eu.tutorials.koperasi_simpan_pinjam.pages.admin.AdminUserLoansPage
import eu.tutorials.koperasi_simpan_pinjam.pages.admin.AdminUserSavingPage
import eu.tutorials.koperasi_simpan_pinjam.pages.admin.ManageMoneyPage
import eu.tutorials.koperasi_simpan_pinjam.pages.admin.ViewLoansTransactionHistoryPage

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavHost(navController: NavHostController){
    NavHost(navController = navController, startDestination = "authentication"){
        composable(route="dashboard") { DashBoard(navController) }
        composable(route="login") { LoginPage(navController) }
        composable(route="register") { RegisterPage(navController) }
        composable(route="authentication") { AuthenticationPage(navController) }

        // admin routes
        composable(route = "dashboardAdmin") {
            val repository = UserRepository(RetrofitClient.instance)
            val viewModel: DashBoardAdminViewModel = viewModel(
                factory = DashboardViewModelFactory(repository)
            )
            DashboardAdminn(navController = navController, viewModel = viewModel)
        }
        composable (route = "manageuser"){
            val repository = UserRepository(RetrofitClient.instance)
            val viewModel: ManageUserAdminViewModel = viewModel(
                factory = ManageUserAdminViewModelFactory(repository)
            )
            ManageUserPage(navController, viewModel = viewModel)
        }
        composable (route = "AdminTransaction"){
            AdminTransactionPage(navController = navController)
        }
        composable (route = "AdminUserSaving"){
            AdminUserSavingPage(navController = navController)
        }
        composable (route = "AdminUserLoans"){ AdminUserLoansPage(navController = navController)}
        composable (route = "ViewLoansTransactionHistory"){ ViewLoansTransactionHistoryPage(navController = navController) }
        composable(route = "AdminReport") { AdminReportPage(navController = navController) }
        composable ( route= "AdminManageMoney" ){ ManageMoneyPage(navController = navController) }


    }
}
