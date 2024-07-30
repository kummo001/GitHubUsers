package com.minhnha.githubuser.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.minhnha.domain.model.User
import com.minhnha.domain.usecase.remote.GetUserUseCase
import com.minhnha.domain.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class HomeUiState(
    val listUsers: UiState<List<User>>
) {
    companion object {
        val default = HomeUiState(
            listUsers = UiState.Loading
        )
    }
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {

    private var _uiState = MutableStateFlow(HomeUiState.default)
    val uiState: StateFlow<HomeUiState>
        get() = _uiState


    fun getUsers(page: Int) {
        viewModelScope.launch {
            val list = getUserUseCase.invoke(page)
            list.onSuccess {
                _uiState.update { currentState -> currentState.copy(listUsers = UiState.Success(it)) }
            }.onFailure {
                _uiState.update { currentState -> currentState.copy(listUsers = UiState.Error(it)) }
            }
        }
    }
}