package com.example.melichallenge.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchInputScreen(
    onSearch: (String) -> Unit
) {
    var searchInput by rememberSaveable { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFffe500)),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://blog.saleslayer.com/hubfs/mercado-libre-logo.jpg")
                    .transformations(CircleCropTransformation())
                    .crossfade(true)
                    .build(),
                contentDescription = null
            )

            Spacer(modifier = Modifier.size(24.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color(0xFF2a3175), shape = RoundedCornerShape(50))
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon",
                    tint = Color.White,
                    modifier = Modifier
                        .size(24.dp)
                        .padding(start = 8.dp)
                        .clickable {
                            if (searchInput.isNotBlank()) {
                                onSearch(searchInput)
                                searchInput = ""
                            }
                        }
                )

                OutlinedTextField(
                    value = searchInput,
                    onValueChange = { newText ->
                        if (!newText.contains('\n')) {
                            searchInput = newText
                        }
                    },
                    placeholder = { Text("Buscar", color = Color.White) },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp)
                        .background(Color.Transparent),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            if (searchInput.isNotBlank()) {
                                onSearch(searchInput)
                                searchInput = ""
                            }
                        }
                    )
                )
            }
        }
    }
}
