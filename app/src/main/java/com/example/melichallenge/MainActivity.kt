package com.example.melichallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.melichallenge.screens.DetailScreen
import com.example.melichallenge.screens.SearchInputScreen
import com.example.melichallenge.screens.SearchDetailsScreen
import com.example.melichallenge.screens.SplashScreen
import com.example.melichallenge.states.DetailState
import com.example.melichallenge.states.UiStates
import com.example.melichallenge.ui.theme.MeliChallengeTheme
import com.example.melichallenge.viewmodel.DetailsViewModel
import com.example.melichallenge.viewmodel.MercadoLibreViewModel

class MainActivity : ComponentActivity() {
    private val mercadoLibreViewModel: MercadoLibreViewModel by viewModels()
    private val detailsViewModel: DetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MeliChallengeTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "splash"
                ) {
                    composable("splash") {
                        SplashScreen(
                            onNavigateToSearch = { navController.navigate("search_input") }
                        )
                    }

                    composable("search_input") {
                        SearchInputScreen(
                            onSearch = { input ->
                                navController.navigate("search_results/${input.encodeString()}")
                            }
                        )
                    }

                    composable(
                        "search_results/{query}",
                        arguments = listOf(navArgument("query") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val query = backStackEntry.arguments?.getString("query") ?: ""

                        var hasSearched by rememberSaveable { mutableStateOf(false) }
                        LaunchedEffect(key1 = query) {
                            if (!hasSearched) {
                                mercadoLibreViewModel.searchListOfProducts(query)
                                hasSearched = true
                            }
                        }
                        val searchResults by mercadoLibreViewModel.uiState.collectAsState()
                        val searchState by mercadoLibreViewModel.searchState.collectAsState()

                        when (val screenState = searchResults) {
                            UiStates.Loading -> {
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    CircularProgressIndicator()
                                    Text(
                                        text = "Estamos procesando tu busqueda =)",
                                        modifier = Modifier.padding(top = 16.dp)
                                    )
                                }

                            }

                            UiStates.ServerError -> {}
                            is UiStates.Success -> {
                                SearchDetailsScreen(
                                    state = searchState,
                                    items = screenState.response.results ?: emptyList(),
                                    onSearch = { input ->
                                        mercadoLibreViewModel.searchListOfProducts(input.encodeString())
                                    },
                                    onSelectedItem = { selectedItem ->
                                        navController.navigate("detail/${selectedItem}")
                                    }
                                )
                            }
                        }
                    }
                    
                    composable(
                        "detail/{item}",
                        arguments = listOf(navArgument("item") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val item = backStackEntry.arguments?.getString("item") ?: ""
                        val detailResults by detailsViewModel.detailState.collectAsState()

                        LaunchedEffect(key1 = item) {
                            detailsViewModel.getItem(item)
                        }
                        when (val screenState = detailResults) {
                            DetailState.Loading -> {
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    CircularProgressIndicator()
                                }

                            }

                            DetailState.ServerError -> {}
                            is DetailState.Success -> {
                                screenState.response.attributes?.let {
                                    DetailScreen(
                                        name = screenState.response.title ?: "unknown",
                                        thumbnail = screenState.response.thumbnail ?: "unknown",
                                        price = screenState.response.price ?: 0f,
                                        currency = screenState.response.currencyId ?: "unknown",
                                        shipping = screenState.response.shipping,
                                        attributes = it
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
