package com.example.nabungemas.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nabungemas.ui.theme.Error100
import com.example.nabungemas.ui.theme.Error500
import com.example.nabungemas.ui.theme.Gold400
import com.example.nabungemas.ui.theme.MutedText
import com.example.nabungemas.ui.theme.Neutral050
import com.example.nabungemas.ui.theme.Neutral800
import com.example.nabungemas.ui.theme.PlusJakartaSans

@Composable
fun ConfirmationDialog(
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
    title: String,
    message: String,
    confirmButtonText: String = "Konfirmasi",
    dismissButtonText: String = "Batal",
    isDeleteAction: Boolean = false,
    icon: ImageVector = Icons.Rounded.Warning
) {
    val isDark = isSystemInDarkTheme()
    val bgColor = if (isDark) Neutral800 else Neutral050

    AlertDialog(
        onDismissRequest = onDismissRequest,
        shape = RoundedCornerShape(24.dp),
        containerColor = bgColor,
        icon = {
            val iconBg = if (isDeleteAction) Error500.copy(alpha = 0.1f) else Gold400.copy(alpha = 0.1f)
            val iconColor = if (isDeleteAction) Error500 else Gold400
            
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(28.dp))
                    .background(iconBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (isDeleteAction) Icons.Rounded.Delete else icon,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(32.dp)
                )
            }
        },
        title = {
            Text(
                text = title,
                fontFamily = PlusJakartaSans,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        text = {
            Text(
                text = message,
                fontFamily = PlusJakartaSans,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                color = MutedText
            )
        },
        confirmButton = {
            val btnColor = if (isDeleteAction) Error500 else Gold400
            val onBtnColor = if (isDeleteAction) Color.White else Color.Black
            
            Button(
                onClick = {
                    onConfirm()
                    onDismissRequest()
                },
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = btnColor,
                    contentColor = onBtnColor
                ),
                modifier = Modifier.height(48.dp)
            ) {
                Text(
                    text = confirmButtonText,
                    fontFamily = PlusJakartaSans,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismissRequest,
                modifier = Modifier.height(48.dp)
            ) {
                Text(
                    text = dismissButtonText,
                    fontFamily = PlusJakartaSans,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = Gold400
                )
            }
        }
    )
}
