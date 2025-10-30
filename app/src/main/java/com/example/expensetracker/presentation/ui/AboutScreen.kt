package com.example.expensetracker.presentation.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Policy
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.expancetracker.R


@Composable
fun AboutScreen(navController: NavController){
    val context = LocalContext.current

    Scaffold(
        topBar = {
            LargeHeader(
                title = "About",
                onClick = { navController.popBackStack() },
                navigationIcon = Icons.Filled.ArrowBackIosNew,
            )
        },

    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            item {
                HeaderComponent(icon = R.drawable.about_icon)
            }


            item {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "MyFinance",
                        style = MaterialTheme.typography.headlineSmall,
                    )
                    Text(
                        text = "Version: v1.0.0",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 10.sp
                    )
                }
            }

            // 2. Huquqiy va Xizmatlar Bo'limi
            item {
                AboutItem(
                    title = "Maxfiylik Siyosati",
                    icon = Icons.Filled.Policy,
                    onClick = { openUrl(context, "https://sizning.saytingiz/privacy") }
                )
            }
            item {
                AboutItem(
                    title = "Foydalanish Shartlari",
                    icon = Icons.Filled.Description,
                    onClick = { openUrl(context, "https://sizning.saytingiz/terms") }
                )
            }
            item {
                AboutItem(
                    title = "Ochiq Manba Litsenziyalari",
                    icon = Icons.Filled.Code,
                    onClick = { openUrl(context, "https://sizning.saytingiz/litsenziya") } // Boshqa ekranga o'tish
                )
            }

            // 3. Aloqa Bo'limi
            item {
                Spacer(modifier = Modifier.height(16.dp))
                AboutItem(
                    title = "Yordam so'rash",
                    subtitle = "support@myfinance.uz",
                    icon = Icons.Filled.Email,
                    onClick = { sendEmail(context, "bbacked25@gmail.com") }
                )
            }
        }
    }
}


// Intent'larni ishga tushirish uchun yordamchi funksiyalar
fun openUrl(context: Context, url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    context.startActivity(intent)
}

fun sendEmail(context: Context, email: String) {
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:$email")
        putExtra(Intent.EXTRA_SUBJECT, "MyFinance App Yordam")
    }
    context.startActivity(Intent.createChooser(intent, "Email yuborish"))
}







@Composable
fun AboutItem(
    title: String,
    subtitle: String? = null,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            subtitle?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
    // Elementlarni ajratish uchun divider
    HorizontalDivider(thickness = 0.5.dp, color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
}

