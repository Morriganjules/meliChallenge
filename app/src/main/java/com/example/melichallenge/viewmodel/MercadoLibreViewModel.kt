package com.example.melichallenge.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.melichallenge.states.SearchState
import com.example.melichallenge.states.UiStates
import com.example.melichallenge.api.Status
import com.example.melichallenge.api.respository.MercadoLibreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MercadoLibreViewModel : ViewModel() {
    private val _uiState: MutableStateFlow<UiStates> = MutableStateFlow(UiStates.Loading)
    val uiState: StateFlow<UiStates> = _uiState

    private val _searchState: MutableStateFlow<SearchState> = MutableStateFlow(SearchState.DONE)
    val searchState: StateFlow<SearchState> = _searchState

    fun searchListOfProducts(searchInput: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _searchState.emit(SearchState.SEARCHING)
            MercadoLibreRepository.getSearchedItem(searchInput).collect{ result ->
                when(result.status) {
                    Status.ServerError -> {
                        _uiState.emit(UiStates.ServerError)
                        _searchState.emit(SearchState.DONE)
                    }
                    Status.Success -> {
                        if(result.data != null){
                            _uiState.emit(UiStates.Success(result.data))
                            _searchState.emit(SearchState.DONE)
                        } else {
                            _uiState.emit(UiStates.ServerError)
                            _searchState.emit(SearchState.DONE)
                        }
                    }
                }
            }
        }
    }
}