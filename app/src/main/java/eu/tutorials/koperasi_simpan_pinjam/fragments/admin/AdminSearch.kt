package eu.tutorials.koperasi_simpan_pinjam.fragments.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eu.tutorials.koperasi_simpan_pinjam.Martel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminSearch(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    onSearchClick: () -> Unit = {} // 👈 optional callback for search button
) {
    val interactionSource = remember { MutableInteractionSource() }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, Color.Black.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
            .background(Color.White, RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicTextField(
            value = value,
            textStyle = TextStyle(fontSize = 15.sp),
            onValueChange = onValueChange,
            modifier = Modifier
                .weight(1f) // take all available horizontal space
                .defaultMinSize(minHeight = TextFieldDefaults.MinHeight),
            interactionSource = interactionSource,
            decorationBox = { innerTextField ->
                TextFieldDefaults.DecorationBox(
                    value = value,
                    innerTextField = innerTextField,
                    enabled = true,
                    singleLine = true,
                    visualTransformation = VisualTransformation.None,
                    interactionSource = interactionSource,
                    label = {
                        Text(
                            text = "Enter any user...",
                            style = TextStyle(
                                fontFamily = Martel,
                                fontWeight = FontWeight.Light,
                                fontSize = 15.sp
                            )
                        )
                    },
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
            }
        )

        IconButton(
            onClick = onSearchClick,
            modifier = Modifier
                .size(36.dp)
                .padding(start = 4.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = Color.Black
            )
        }
    }
}
