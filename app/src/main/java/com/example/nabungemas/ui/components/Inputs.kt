package com.example.nabungemas.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nabungemas.ui.theme.Error500
import com.example.nabungemas.ui.theme.Gold400
import com.example.nabungemas.ui.theme.MutedText
import com.example.nabungemas.ui.theme.PlusJakartaSans

@Composable
fun GoldTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    helperText: String = "",
    errorText: String = "",
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    singleLine: Boolean = true,
    trailingIcon: (@Composable () -> Unit)? = null,
    leadingIcon: (@Composable () -> Unit)? = null,
    visualTransformation: androidx.compose.ui.text.input.VisualTransformation = androidx.compose.ui.text.input.VisualTransformation.None
) {
    Column(modifier = modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { 
                Text(
                    text = label,
                    fontFamily = PlusJakartaSans,
                    fontWeight = FontWeight.Medium
                ) 
            },
            placeholder = { 
                if (placeholder.isNotEmpty()) {
                    Text(
                        text = placeholder,
                        fontFamily = PlusJakartaSans,
                        color = MutedText
                    )
                }
            },
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            isError = isError || errorText.isNotEmpty(),
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            singleLine = singleLine,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Gold400,
                focusedLabelColor = Gold400,
                cursorColor = Gold400,
                errorBorderColor = Error500,
                errorLabelColor = Error500
            )
        )
        
        if (errorText.isNotEmpty()) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = errorText,
                fontFamily = PlusJakartaSans,
                fontWeight = FontWeight.Normal,
                fontSize = 11.sp,
                color = Error500
            )
        } else if (helperText.isNotEmpty()) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = helperText,
                fontFamily = PlusJakartaSans,
                fontWeight = FontWeight.Normal,
                fontSize = 11.sp,
                color = MutedText
            )
        }
    }
}
