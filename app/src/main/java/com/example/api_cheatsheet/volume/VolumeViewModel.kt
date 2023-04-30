package com.example.api_cheatsheet.volume

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.api_cheatsheet.api.DataSource.getExchangesList
import com.example.api_cheatsheet.listExchanges.ScreenState
import kotlinx.coroutines.launch

class VolumeFragmentViewModel : ViewModel() {

    private val _screenState = MutableLiveData<ScreenState>()
    val screenState: LiveData<ScreenState> = _screenState

    init {
        volumeData()
    }

    private fun volumeData() {
        _screenState.value = ScreenState.Loading

        viewModelScope.launch {
            try {
                val exchanges =
                    getExchangesList().body()!!.sortedByDescending { it.tradeVolume24hBtc }
                val firstTenExchanges = exchanges.take(7)
                _screenState.postValue(ScreenState.Success(firstTenExchanges))
            } catch (exception: Exception) {
                _screenState.postValue(ScreenState.Error(Throwable()))
            }
        }
    }

    fun refreshVolume() {
        volumeData()
    }
}