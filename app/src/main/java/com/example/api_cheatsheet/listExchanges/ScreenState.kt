package com.example.api_cheatsheet.listExchanges

import com.example.api_cheatsheet.api.Exchanges

sealed class ScreenState {
    data class Success(val data: List<Exchanges>) : ScreenState()
    data class Error(val throwable: Throwable) : ScreenState()
    object Loading : ScreenState()
}
