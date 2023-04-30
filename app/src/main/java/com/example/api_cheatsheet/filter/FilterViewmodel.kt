package com.example.api_cheatsheet.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.api_cheatsheet.api.DataSource
import com.example.api_cheatsheet.api.Exchanges
import com.example.api_cheatsheet.listExchanges.ScreenState
import kotlinx.coroutines.launch

class FilterViewmodel : ViewModel() {
    fun onTextChanged(query: String) {
        val state = screenState.value
        if (state is ScreenState.Success) {
            val filteredList = state.data.filter {
                it.name.orEmpty().lowercase().startsWith(query.trim().lowercase())

            }

            _exchangesList.value = filteredList
        }
    }

    private val _screenState = MutableLiveData<ScreenState>()
    val screenState: LiveData<ScreenState> = _screenState

    private val _exchangesList = MutableLiveData<List<Exchanges>>()
    val exchangesList: LiveData<List<Exchanges>> = _exchangesList


    init {
        _screenState.value = ScreenState.Loading
        viewModelScope.launch {
            try {
                val exchanges = DataSource.getExchangesList().body()!!
                _screenState.postValue(ScreenState.Success(exchanges))
            } catch (exception: Exception) {
                _screenState.postValue(ScreenState.Error(Throwable()))
            }
        }
    }
}