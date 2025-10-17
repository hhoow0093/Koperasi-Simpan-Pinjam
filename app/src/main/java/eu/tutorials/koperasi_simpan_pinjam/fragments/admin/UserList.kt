package eu.tutorials.koperasi_simpan_pinjam.fragments.admin

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BorderColor
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Modifier
import eu.tutorials.koperasi_simpan_pinjam.ui.theme.DeepBlue
import eu.tutorials.koperasi_simpan_pinjam.ui.theme.SoftLavender
import androidx.compose.ui.graphics.Color

@Composable
fun UserList(
    modifier: Modifier,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    name: String?,
    email: String?,
    memberStatus: Boolean
){
    Card(
        modifier = modifier
            .padding(vertical = 16.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp),
        border = BorderStroke(1.dp, Color.Black),
        colors = CardDefaults.cardColors(
            containerColor = SoftLavender,
            contentColor = DeepBlue
        ),
    ) {
        Column {
            // Top Row: Text + Button Icon
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(
                        start = 24.dp,
                        end = 24.dp,
                        top = 24.dp,
                        bottom = 5.dp
                    ),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = name ?: "name",
                    fontSize = 30.sp
                )

                // Button Icon (Arrow)
                IconButton(onClick = { onEditClick()}) {
                    Icon(
                        imageVector = Icons.Default.BorderColor,
                        contentDescription = "Go edit users"
                    )
                }
            }

            // Bottom Section
            Column(
                modifier = modifier.padding(
                    start = 24.dp,
                    end = 24.dp,
                    top = 5.dp,
                    bottom = 14.dp
                ).fillMaxWidth()
            ) {
                Text(text =  email ?: "email", style = MaterialTheme.typography.titleLarge)
                Row(  modifier = modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically ){
                    Text(
                        modifier = modifier.padding(vertical = 10.dp),
                        text = if (memberStatus) "Active" else "Inactive",
                        fontSize = 16.sp
                    )
                    IconButton(onClick = { onDeleteClick()}) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Go delete user",
                            modifier = Modifier.size(25.dp)
                        )
                    }

                }
            }
        }
    }
}

//@Preview(showBackground = true, name = "Manage user", showSystemUi = true, device = Devices.PIXEL_5)
//@Composable
//fun ManageUserPreview() {
//    KoperasiSimpanPinjamTheme {
//        val navController = rememberNavController()
//        ManageUserPage(navController = navController)
//    }
//}
