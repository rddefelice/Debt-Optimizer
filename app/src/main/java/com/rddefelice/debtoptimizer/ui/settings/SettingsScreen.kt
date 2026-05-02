package com.rddefelice.debtoptimizer.ui.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun SettingsScreen(navController: NavController) {
    var notificationsEnabled by remember { mutableStateOf(false) }
    var emailsEnabled by remember { mutableStateOf(false) }
    var textsEnabled by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))

        // Toggle Rows
        SettingsToggleRow(
            label = "Notifications",
            checked = notificationsEnabled,
            onCheckedChange = { notificationsEnabled = it }
        )
        HorizontalDivider(color = Color.LightGray, thickness = 0.5.dp)

        SettingsToggleRow(
            label = "Emails",
            checked = emailsEnabled,
            onCheckedChange = { emailsEnabled = it }
        )
        HorizontalDivider(color = Color.LightGray, thickness = 0.5.dp)

        SettingsToggleRow(
            label = "Text Messages",
            checked = textsEnabled,
            onCheckedChange = { textsEnabled = it }
        )
        HorizontalDivider(color = Color.LightGray, thickness = 0.5.dp)

        Spacer(modifier = Modifier.height(32.dp))

        // Action Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = { /* TODO */ },
                modifier = Modifier.weight(1f).height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6750A4))
            ) {
                Text("Export Plan")
            }
            Button(
                onClick = { /* TODO */ },
                modifier = Modifier.weight(1f).height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6750A4))
            ) {
                Text("Delete Account")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { /* TODO */ },
            modifier = Modifier.width(160.dp).height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6750A4))
        ) {
            Text("Contact Us")
        }
    }
}

@Composable
fun SettingsToggleRow(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = label, modifier = Modifier.weight(1f), fontSize = 16.sp)
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = Color.Gray,
                uncheckedThumbColor = Color.Gray,
                uncheckedTrackColor = Color.White
            )
        )
    }
}