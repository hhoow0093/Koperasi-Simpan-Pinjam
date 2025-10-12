package eu.tutorials.koperasi_simpan_pinjam.fragments

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eu.tutorials.koperasi_simpan_pinjam.Martel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordTextField(
    label: String,
    modifier: Modifier = Modifier,
    password: String,
    onPasswordChange: (String) -> Unit,
){
    var passwordVisible by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    BasicTextField(
        value = password,
        onValueChange = onPasswordChange,
        textStyle = TextStyle(fontSize = 15.sp),
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = TextFieldDefaults.MinHeight)
            .background(Color.White, RoundedCornerShape(8.dp)) // background of the field
            .padding(horizontal = 8.dp), // inner padding
        interactionSource = interactionSource,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        decorationBox = { innerTextField ->
            TextFieldDefaults.DecorationBox(
                value = password,
                innerTextField = innerTextField,
                enabled = true,
                singleLine = true,
                interactionSource = interactionSource,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                label = {
                    Text(
                        label,
                        style = TextStyle(
                            fontFamily = Martel,
                            fontWeight = FontWeight.Light,
                            fontSize = 15.sp
                        )
                    )
                },
                trailingIcon = {
                    val image = if (passwordVisible) {
                        Icons.Filled.Visibility
                    } else {
                        Icons.Filled.VisibilityOff
                    }

                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, contentDescription = null)
                    }
                },
                contentPadding = PaddingValues(horizontal = 0.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
        }
    )

}