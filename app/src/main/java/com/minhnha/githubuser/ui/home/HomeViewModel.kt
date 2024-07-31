package com.minhnha.githubuser.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.minhnha.domain.model.User
import com.minhnha.domain.usecase.remote.GetUserUseCase
import com.minhnha.domain.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class HomeUiState(
    val listUsers: Result<List<User>>
) {
    companion object {
        val default = HomeUiState(
            listUsers = Result.Loading
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


    /**
     * Get list of users
     * @param page the page number to you want to get
     */
    fun getUsers(page: Int) {
        viewModelScope.launch {
            val list = getUserUseCase.invoke(page)
            list.onSuccess {
                _uiState.update { currentState ->
                    val updatedList = (currentState.listUsers as? Result.Success)?.data.orEmpty() + it
                    currentState.copy(listUsers = Result.Success(updatedList))
                }
            }.onFailure {
                _uiState.update { currentState -> currentState.copy(listUsers = Result.Error(it)) }
            }
        }
    }
}