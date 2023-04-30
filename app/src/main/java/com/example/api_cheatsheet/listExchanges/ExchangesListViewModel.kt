package com.example.api_cheatsheet.listExchanges

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.api_cheatsheet.api.DataSource
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class ExchangesListViewModel : ViewModel() {

    private val _screenState = MutableLiveData<ScreenState>()
    val screenState: LiveData<ScreenState> = _screenState

    private var job: Job? = null

    init {
        fetchExchangesRepeatedly()
    }

    private fun fetchExchangesRepeatedly() {
        if (job != null) return
        _screenState.value = ScreenState.Loading

        job = viewModelScope.launch {
            while (isActive) {
                fetchExchanges()
                delay(FETCH_INTERVAL)
            }
        }
    }

    private suspend fun fetchExchanges() {
        try {
            val exchanges = DataSource.getExchangesList().body()!!
            _screenState.postValue(ScreenState.Success(exchanges))
        } catch (exception: Exception) {
            _screenState.postValue(ScreenState.Error(Throwable()))
        }
    }

    fun cancelRepeatedFetches() {
        job?.cancel()
        job = null
    }

    fun refreshExchanges() {
        _screenState.value = ScreenState.Loading

        viewModelScope.launch {
            fetchExchanges()
        }
    }

    companion object {
        private val FETCH_INTERVAL = 90.seconds
    }
}