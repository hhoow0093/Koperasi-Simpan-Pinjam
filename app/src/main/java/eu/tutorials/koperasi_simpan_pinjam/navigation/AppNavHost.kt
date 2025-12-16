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
import eu.tutorials.koperasi_simpan_pinjam.data.repository.admin.PembayaranLoanRepository
import eu.tutorials.koperasi_simpan_pinjam.data.repository.admin.UserRepository
import eu.tutorials.koperasi_simpan_pinjam.data.repository.admin.UserRepositoryPeminjaman
import eu.tutorials.koperasi_simpan_pinjam.data.repository.admin.UserRepositorySimpanan
import eu.tutorials.koperasi_simpan_pinjam.data.viewmodel.admin.DashBoardAdminViewModel
import eu.tutorials.koperasi_simpan_pinjam.data.viewmodel.admin.DashboardViewModelFactory
import eu.tutorials.koperasi_simpan_pinjam.data.viewmodel.admin.ManageUserAdminViewModel
import eu.tutorials.koperasi_simpan_pinjam.data.viewmodel.admin.ManageUserAdminViewModelFactory
import eu.tutorials.koperasi_simpan_pinjam.data.viewmodel.admin.PembayaranLoanViewModel
import eu.tutorials.koperasi_simpan_pinjam.data.viewmodel.admin.PembayaranLoanViewModelFactory
import eu.tutorials.koperasi_simpan_pinjam.data.viewmodel.admin.PeminjamanUserViewModel
import eu.tutorials.koperasi_simpan_pinjam.data.viewmodel.admin.PeminjamanUserViewModelFactory
import eu.tutorials.koperasi_simpan_pinjam.data.viewmodel.admin.SimpananViewModelFactoryUser
import eu.tutorials.koperasi_simpan_pinjam.data.viewmodel.admin.SimpananViewModelUser
import eu.tutorials.koperasi_simpan_pinjam.pages.admin.AdminReportPage
import eu.tutorials.koperasi_simpan_pinjam.pages.admin.AdminTransactionPage
import eu.tutorials.koperasi_simpan_pinjam.pages.admin.AdminUserLoansPage
import eu.tutorials.koperasi_simpan_pinjam.pages.admin.AdminUserSavingPage
import eu.tutorials.koperasi_simpan_pinjam.pages.admin.MachineLearningPage
import eu.tutorials.koperasi_simpan_pinjam.pages.admin.ManageMoneyPage
import eu.tutorials.koperasi_simpan_pinjam.pages.admin.PencatatanAngsuran
import eu.tutorials.koperasi_simpan_pinjam.pages.admin.ViewLoansTransactionHistoryPage
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "authentication") {
        composable(route = "dashboard") { DashBoard(navController) }
        composable(route = "login") { LoginPage(navController) }
        composable(route = "register") { RegisterPage(navController) }
        composable(route = "authentication") { AuthenticationPage(navController) }

        // admin routes
        composable(route = "dashboardAdmin") {
            val repository = UserRepository(RetrofitClient.instance)
            val viewModel: DashBoardAdminViewModel = viewModel(
                factory = DashboardViewModelFactory(repository)
            )
            DashboardAdminn(navController = navController, viewModel = viewModel)
        }
        composable(route = "manageuser") {
            val repository = UserRepository(RetrofitClient.instance)
            val viewModel: ManageUserAdminViewModel = viewModel(
                factory = ManageUserAdminViewModelFactory(repository)
            )
            ManageUserPage(navController, viewModel = viewModel)
        }
        composable(route = "AdminTransaction") {
            val repository = UserRepository(RetrofitClient.instance)
            val viewModel: ManageUserAdminViewModel = viewModel(
                factory = ManageUserAdminViewModelFactory(repository)
            )
            AdminTransactionPage(navController = navController, viewModel = viewModel)
        }

        composable(route = "FiturMachineLearning") {
            MachineLearningPage(navController = navController)
        }

        composable("AdminUserSaving/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId").toString()
            val repository = UserRepositorySimpanan(api = RetrofitClient.instance)
            val viewModel: SimpananViewModelUser = viewModel(
                factory = SimpananViewModelFactoryUser(repository)
            )
            AdminUserSavingPage(navController = navController, userId = userId, viewModel = viewModel)
        }
        composable(route = "AdminUserLoans/{userId}/{name}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId").toString()
            val name = backStackEntry.arguments?.getString("name").toString()
            val repository = UserRepositoryPeminjaman(api = RetrofitClient.instance)
            val viewModel: PeminjamanUserViewModel = viewModel(factory = PeminjamanUserViewModelFactory(repository = repository))
            AdminUserLoansPage(navController = navController, userId = userId, viewModel = viewModel, name = name)
        }
        composable(route = "PencatatanAngsuran/{pinjamanId}") {backStackEntry ->
            val pinjamanId= backStackEntry.arguments?.getString("pinjamanId").toString()
            val repository = PembayaranLoanRepository(api = RetrofitClient.instance)
            val viewModel: PembayaranLoanViewModel = viewModel(factory = PembayaranLoanViewModelFactory(repository = repository))
            PencatatanAngsuran( navController= navController, viewModel= viewModel, loanId=pinjamanId)
        }
        composable(route = "AdminReport") { AdminReportPage(navController = navController) }
        composable(route = "AdminManageMoney") { ManageMoneyPage(navController = navController) }


    }
}

