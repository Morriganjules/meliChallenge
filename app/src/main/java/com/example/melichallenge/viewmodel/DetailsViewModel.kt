package com.example.melichallenge.viewmodel

import android.util.Base64
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.melichallenge.model.Item
import com.example.melichallenge.states.DetailState
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailsViewModel : ViewModel() {
    private val _detailState: MutableStateFlow<DetailState> = MutableStateFlow(DetailState.Loading)
    val detailState: StateFlow<DetailState> = _detailState

    fun getItem(base64: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val jsonString = String(Base64.decode(base64, Base64.DEFAULT))
            val item = Gson().fromJson(jsonString, Item::class.java)
            item.let {
                _detailState.emit(
                    DetailState.Success(it)
                )
            }
        }
    }
}
