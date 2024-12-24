package com.example.melichallenge.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.melichallenge.SearchState
import com.example.melichallenge.addCurrency
import com.example.melichallenge.formatToLocalizedString
import com.example.melichallenge.model.Item
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer

@Composable
fun SearchDetailsScreen(
    state: SearchState,
    onSearch: (String) -> Unit,
    onSelectedItem: (String, String) -> Unit,
    items: List<Item>
) {
    var searchInput by rememberSaveable { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = searchInput,
            onValueChange = { newText ->
                if (!newText.contains('\n')) {
                    searchInput = newText
                }
            },
            label = { Text("Buscar") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                keyboardController?.hide()
                onSearch(searchInput)
            })
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                keyboardController?.hide()
                onSearch(searchInput)
                searchInput = ""
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Buscar")
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (state) {
            SearchState.SEARCHING -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                }
            }

            SearchState.DONE -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    items(items.size) { item ->
                        ResultItem(
                            item = items[item],
                            onSelectedItem = { nickname ->
                                onSelectedItem(nickname, searchInput)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ResultItem(
    item: Item,
    onSelectedItem: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .clickable {
                item.seller?.nickname?.let(onSelectedItem)
            }
    ) {
        AsyncImage(
            model = item.thumbnail,
            contentDescription = null,
            modifier = Modifier
                .size(100.dp)
                .placeholder(
                    visible = true,
                    highlight = PlaceholderHighlight.shimmer()
                )
        )
        Column(
            modifier = Modifier
                .padding(start = 6.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = item.title.toString(),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                item.price?.formatToLocalizedString()?.addCurrency(item.currencyId.toString())
                    ?.let {
                        Text(
                            text = it,
                            modifier = Modifier
                                .padding(start = 6.dp),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                PillTabView(modifier = Modifier, titleText = "Envío gratis.")
            }
        }
    }
    HorizontalDivider(thickness = 1.dp)
}

@Composable
fun PillTabView(
    modifier: Modifier,
    titleText: String
) {
    Box(
        modifier = modifier
            .padding(2.dp)
            .background(
                color = Color(0xFF66DE40),
                RoundedCornerShape(25.dp)
            )
            .clip(RoundedCornerShape(25.dp))
    ) {
        Text(
            text = titleText,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(8.dp),
            color = Color.White,
            lineHeight = 16.sp
        )
    }
}
