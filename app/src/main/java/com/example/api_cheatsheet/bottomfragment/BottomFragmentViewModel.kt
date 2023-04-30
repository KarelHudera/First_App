package com.example.api_cheatsheet.bottomfragment

import androidx.lifecycle.*
import com.example.api_cheatsheet.api.DataSource
import kotlinx.coroutines.launch

class BottomFragmentViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    private val exchageId = ExchangeDetailArgs.fromSavedStateHandle(savedStateHandle).exchangeId

    private val _screenState = MutableLiveData<BottomFragmentScreenState>()
    val screenState: LiveData<BottomFragmentScreenState> = _screenState

    init {
        _screenState.value = BottomFragmentScreenState.Loading

        viewModelScope.launch {
            try {
                val exchange = DataSource.getExchangesDetail(exchageId).body()!!
                _screenState.postValue(BottomFragmentScreenState.Success(exchange))
            } catch (exception: Exception) {
                _screenState.postValue(BottomFragmentScreenState.Error(Throwable()))
            }
        }
    }
}
