package com.rddefelice.debtoptimizer.ui.offers

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

data class Offer(val title: String, val details: String, val fullDescription: String)

@Composable
fun OffersScreen(navController: NavController) {
    val offers = remember {
        listOf(
            Offer("Debt Consolidation", "Details", "Consolidate your high-interest credit cards into one low monthly payment. APR starting at 5.99%."),
            Offer("0% APR Credit Card", "Details", "Transfer your balance and pay 0% interest for 18 months. No annual fee."),
            Offer("Savings Account", "Details", "High-yield savings account with 4.50% APY. Start saving for your emergency fund today.")
        )
    }
    
    var selectedOffer by remember { mutableStateOf<Offer?>(null) }

    if (selectedOffer != null) {
        OfferDetailDialog(
            offer = selectedOffer!!,
            onDismiss = { selectedOffer = null }
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(offers) { offer ->
                OfferItem(
                    offer = offer,
                    onClick = { selectedOffer = offer }
                )
                HorizontalDivider(color = Color.LightGray, thickness = 0.5.dp)
            }
        }
    }
}

@Composable
fun OfferItem(offer: Offer, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Avatar
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(Color(0xFFE6E1FF), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = offer.title.take(1),
                color = Color(0xFF6750A4),
                fontSize = 20.sp
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(text = offer.title, fontSize = 16.sp)
            Text(text = offer.details, color = Color.Gray, fontSize = 14.sp)
        }

        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = Color.DarkGray
        )
    }
}

@Composable
fun OfferDetailDialog(offer: Offer, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = offer.title) },
        text = {
            Text(text = offer.fullDescription)
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        },
        dismissButton = {
            Button(onClick = { /* Handle "Apply" or similar */ }) {
                Text("Learn More")
            }
        }
    )
}