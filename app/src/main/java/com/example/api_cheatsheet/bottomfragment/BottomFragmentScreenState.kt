package com.example.api_cheatsheet.bottomfragment

import com.example.api_cheatsheet.api.ExchangeDetail

sealed class BottomFragmentScreenState {
    data class Success(val data: ExchangeDetail) : BottomFragmentScreenState()
    data class Error(val throwable: Throwable) : BottomFragmentScreenState()
    object Loading : BottomFragmentScreenState()
}
