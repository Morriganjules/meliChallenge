package com.example.melichallenge.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.melichallenge.addCurrency
import com.example.melichallenge.formatToLocalizedString
import com.example.melichallenge.model.Attributes
import com.example.melichallenge.model.Shipping
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer

@Composable
fun DetailScreen(
    name: String,
    thumbnail: String,
    price: Float,
    currency: String,
    shipping: Shipping?,
    attributes: List<Attributes>
) {
    Scaffold(
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = name,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                AsyncImage(
                    model = thumbnail,
                    contentDescription = null,
                    modifier = Modifier
                        .size(200.dp)
                        .placeholder(
                            visible = true,
                            highlight = PlaceholderHighlight.shimmer()
                        )
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    price.formatToLocalizedString().addCurrency(currency).let {
                        Text(
                            text = it,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    if (shipping?.freeShipping == true) {
                        PillTabView(modifier = Modifier, titleText = "Envío gratis.")
                    }
                }

                AttributesList(attributes)
            }
        }
    )
}

@Composable
fun AttributesList(attributes: List<Attributes>) {
    Box(
        modifier = Modifier
            .border(2.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
            .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(attributes.size) { attribute ->
                AttributeRow(attributes[attribute])
            }
        }
    }
}

@Composable
fun AttributeRow(attribute: Attributes) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = attribute.name ?: "Unknown",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = attribute.valueName ?: "N/A",
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.weight(1f),
            color = Color.Gray
        )
    }
}