package eu.tutorials.koperasi_simpan_pinjam.fragments

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import eu.tutorials.koperasi_simpan_pinjam.R

@Composable
fun SocialLogin() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        // Gmail Button
        Button(
            onClick = { /* TODO: Gmail login */ },
            shape = RoundedCornerShape(8.dp), // square-ish with little rounding
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .size(75.dp) // make it square
                .shadow(
                    elevation = 6.dp, // shadow size
                    shape = RoundedCornerShape(8.dp), // rounded corners
                    clip = false // don’t clip, so shadow shows outside
                ),

            contentPadding = PaddingValues(0.dp), // remove default padding
            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_gmail), // put Gmail logo in drawable
                contentDescription = "Login with Gmail",
                modifier = Modifier.size(30.dp)
            )
        }

        // Facebook Button
        Button(
            onClick = { /* TODO: Facebook login */ },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .size(75.dp) // make it square
                .shadow(
                    elevation = 6.dp, // shadow size
                    shape = RoundedCornerShape(8.dp), // rounded corners
                    clip = false // don’t clip, so shadow shows outside
                ),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White) // Facebook blue
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_facebook),
                contentDescription = "Login with Facebook",
            )
        }
    }
}