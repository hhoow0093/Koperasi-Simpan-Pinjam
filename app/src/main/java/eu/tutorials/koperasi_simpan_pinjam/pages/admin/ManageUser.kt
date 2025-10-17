package eu.tutorials.koperasi_simpan_pinjam.pages.admin

import android.app.DatePickerDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import eu.tutorials.koperasi_simpan_pinjam.ui.theme.DeepBlue
import eu.tutorials.koperasi_simpan_pinjam.ui.theme.white
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import eu.tutorials.koperasi_simpan_pinjam.data.viewmodel.ManageUserAdminViewModel
import eu.tutorials.koperasi_simpan_pinjam.fragments.admin.AdminSearch
import eu.tutorials.koperasi_simpan_pinjam.fragments.admin.UserList
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import eu.tutorials.koperasi_simpan_pinjam.data.API.RetrofitClient
import eu.tutorials.koperasi_simpan_pinjam.data.API.User
import eu.tutorials.koperasi_simpan_pinjam.data.repository.UserRepository
import eu.tutorials.koperasi_simpan_pinjam.data.viewmodel.ManageUserAdminViewModelFactory
import eu.tutorials.koperasi_simpan_pinjam.ui.theme.KoperasiSimpanPinjamTheme
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale


@Composable
fun TopBar(modifier: Modifier, searchEmail: MutableState<String>, navController: NavController){

    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)){
        Box(
            modifier = Modifier
                .size(64.dp)
                .background(
                    color = DeepBlue,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            IconButton(
                onClick = { navController.popBackStack()},
                modifier = Modifier
                    .size(48.dp)
                    .align(Alignment.Center)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Go to Manage Users",
                    tint = white
                )
            }
        }
        AdminSearch(modifier, value = searchEmail.value, onValueChange = { searchEmail.value = it })

    }
}

@Composable
fun DateOfBirthField(
    label: String = "Date of Birth",
    initialDate: String = "",
    onDateSelected: (String) -> Unit,
    onAgeCalculated: (Int) -> Unit
) {
    val context = LocalContext.current
    var date by remember { mutableStateOf(initialDate) }

    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDay ->
            val formatted = "%04d-%02d-%02d".format(selectedYear, selectedMonth + 1, selectedDay)
            date = formatted
            onDateSelected(formatted)

            // Calculate age
            val today = Calendar.getInstance()
            var age = today.get(Calendar.YEAR) - selectedYear
            if (today.get(Calendar.DAY_OF_YEAR) < Calendar.getInstance().apply {
                    set(selectedYear, selectedMonth, selectedDay)
                }.get(Calendar.DAY_OF_YEAR)) {
                age--
            }
            onAgeCalculated(age)
        },
        year,
        month,
        day
    )

    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = date,
            onValueChange = {},
            label = { Text(label) },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            enabled = false,
            textStyle = TextStyle(color = DeepBlue, fontSize = 16.sp),
            colors = OutlinedTextFieldDefaults.colors(
                disabledContainerColor = Color.White,
                disabledTextColor = DeepBlue,
                disabledBorderColor = Color.Gray,
                disabledLabelColor = Color.Gray
            )
        )

        // Transparent clickable overlay to trigger date picker
        Box(
            modifier = Modifier
                .matchParentSize()
                .clickable { datePickerDialog.show() }
        )
    }
}

@Composable
fun AgeField(
    label: String = "Age",
    ageValue: String
) {
    OutlinedTextField(
        value = ageValue,
        onValueChange = {},
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        readOnly = true,
        enabled = false,
        textStyle = TextStyle(color = DeepBlue, fontSize = 16.sp),
        colors = OutlinedTextFieldDefaults.colors(
            disabledContainerColor = Color.White,
            disabledTextColor = DeepBlue,
            disabledBorderColor = Color.Gray,
            disabledLabelColor = Color.Gray
        )
    )
}
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageUserPage(
    navController: NavHostController,
    viewModel: ManageUserAdminViewModel,
    modifier: Modifier = Modifier
) {
    fun formatDateToString(date: Date?): String {
        return if (date != null) {
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            sdf.format(date)
        } else {
            ""
        }
    }

    LaunchedEffect(Unit) {
        viewModel.fetchAllUsers()
    }

    val users by viewModel.userList.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    val searchEmail = remember { mutableStateOf("") }
    val showSheet = remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val selectedUser = remember { mutableStateOf<User?>(null) }

    // Add these for delete functionality
    val showDeleteDialog = remember { mutableStateOf(false) }
    val userToDelete = remember { mutableStateOf<User?>(null) }

    // Edit user bottom sheet (existing code)
    if (showSheet.value && selectedUser.value != null) {
        val user = selectedUser.value!!
        var expandedGender by remember { mutableStateOf(false) }
        var expandedStatus by remember { mutableStateOf(false) }
        var membershipStatus by remember { mutableStateOf(user.member_status) }

        var name by remember { mutableStateOf(user.name.orEmpty()) }
        var email by remember { mutableStateOf(user.email.orEmpty()) }
        var age by remember { mutableStateOf(user.age?.toString().orEmpty()) }
        var gender by remember { mutableStateOf(user.gender.orEmpty()) }
        var dateBirth by remember { mutableStateOf(formatDateToString(user.date_birth)) }

        ModalBottomSheet(
            onDismissRequest = { showSheet.value = false },
            sheetState = sheetState,
            containerColor = white,
            dragHandle = { BottomSheetDefaults.DragHandle() }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("User Details", fontSize = 22.sp, color = DeepBlue)
                Spacer(Modifier.height(16.dp))

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle(color = DeepBlue, fontSize = 16.sp)
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle(color = DeepBlue, fontSize = 16.sp)
                )

                AgeField(ageValue = age)

                ExposedDropdownMenuBox(
                    expanded = expandedGender,
                    onExpandedChange = { expandedGender = !expandedGender }
                ) {
                    OutlinedTextField(
                        value = gender,
                        onValueChange = {},
                        label = { Text("Gender") },
                        modifier = Modifier.menuAnchor().fillMaxWidth(),
                        readOnly = true,
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedGender)
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        ),
                        textStyle = TextStyle(color = DeepBlue, fontSize = 16.sp)
                    )

                    ExposedDropdownMenu(
                        expanded = expandedGender,
                        onDismissRequest = { expandedGender = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text(text = "Male", color = DeepBlue) },
                            onClick = {
                                gender = "Male"
                                expandedGender = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(text = "Female", color = DeepBlue) },
                            onClick = {
                                gender = "Female"
                                expandedGender = false
                            }
                        )
                    }
                }

                DateOfBirthField(
                    initialDate = dateBirth,
                    onDateSelected = { selectedDate -> dateBirth = selectedDate },
                    onAgeCalculated = { calculatedAge ->
                        age = calculatedAge.toString()
                    }
                )

                ExposedDropdownMenuBox(
                    expanded = expandedStatus,
                    onExpandedChange = { expandedStatus = !expandedStatus }
                ) {
                    OutlinedTextField(
                        value = if (membershipStatus) "Active" else "Inactive",
                        onValueChange = {},
                        label = { Text("Membership Status") },
                        modifier = Modifier.menuAnchor().fillMaxWidth(),
                        readOnly = true,
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedStatus)
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        ),
                        textStyle = TextStyle(color = DeepBlue, fontSize = 16.sp)
                    )

                    ExposedDropdownMenu(
                        expanded = expandedStatus,
                        onDismissRequest = { expandedStatus = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text(text = "Active", color = DeepBlue) },
                            onClick = {
                                membershipStatus = true
                                expandedStatus = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(text = "Inactive", color = DeepBlue) },
                            onClick = {
                                membershipStatus = false
                                expandedStatus = false
                            }
                        )
                    }
                }

                Spacer(Modifier.height(20.dp))

                Button(
                    onClick = {
                        val dateOfBirth: Date? = if (dateBirth.isNotEmpty()) {
                            try {
                                val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                                sdf.parse(dateBirth)
                            } catch (e: Exception) {
                                null
                            }
                        } else {
                            null
                        }

                        val updatedUser = user.copy(
                            member_status = membershipStatus,
                            name = name,
                            email = email,
                            age = age.toIntOrNull() ?: 0,
                            gender = gender,
                            date_birth = dateOfBirth
                        )
                        viewModel.updateUser(updatedUser)
                        showSheet.value = false
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = DeepBlue)
                ) {
                    Text("Save Changes", color = white)
                }

                TextButton(onClick = { showSheet.value = false }) {
                    Icon(Icons.Default.Close, contentDescription = null)
                    Text("Close")
                }
            }
        }
    }

    // Delete confirmation dialog
    if (showDeleteDialog.value && userToDelete.value != null) {
        AlertDialog(
            onDismissRequest = {
                showDeleteDialog.value = false
                userToDelete.value = null
            },
            title = { Text("Delete User", color = DeepBlue) },
            text = {
                Text(
                    "Are you sure you want to delete ${userToDelete.value?.name}? This action cannot be undone.",
                    color = DeepBlue
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        userToDelete.value?.let { user ->
                            viewModel.deleteUser(user)
                        }
                        showDeleteDialog.value = false
                        userToDelete.value = null
                    }
                ) {
                    Text("Delete", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog.value = false
                        userToDelete.value = null
                    }
                ) {
                    Text("Cancel", color = DeepBlue)
                }
            },
            containerColor = white
        )
    }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 30.dp, horizontal = 10.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            TopBar(modifier = modifier, searchEmail = searchEmail, navController = navController)

            when {
                isLoading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                errorMessage != null -> {
                    Text("Error: $errorMessage")
                }

                else -> {
                    LazyColumn {
                        items(
                            items = users,
                            key = { user -> user._id!! }
                        ) { user ->
                            UserList(
                                modifier = modifier,
                                onEditClick = {
                                    showSheet.value = true
                                    selectedUser.value = user
                                },
                                onDeleteClick = {
                                    userToDelete.value = user
                                    showDeleteDialog.value = true
                                },
                                name = user.name,
                                email = user.email,
                                memberStatus = user.member_status
                            )
                        }
                    }
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, name = "Manage user", showSystemUi = true, device = Devices.PIXEL_5)
@Composable
fun ManageUserPreview() {
    val repository = UserRepository(RetrofitClient.instance)
    val viewModel: ManageUserAdminViewModel = viewModel(
        factory = ManageUserAdminViewModelFactory(repository)
    )
    KoperasiSimpanPinjamTheme {
        val navController = rememberNavController()
        ManageUserPage(navController = navController, viewModel = viewModel, modifier = Modifier)
    }
}

