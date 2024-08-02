package com.minhnha.githubuser.ui.home

import android.util.Log
import androidx.compose.runtime.mutableStateOf
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
    val screenState: Result<List<User>>,
) {
    companion object {
        val default = HomeUiState(
            screenState = Result.Idle
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

    val isLoadingMore = mutableStateOf(false)


    /**
     * Get list of users
     * @param page the page number to you want to get
     */
    fun getUsers(page: Int) {
        Log.d("GitHubUser", "getUsers in vm: $page")
        isLoadingMore.value = true
        viewModelScope.launch {
            val list = getUserUseCase.invoke(page)
            list.onSuccess {
                isLoadingMore.value = false
                _uiState.update { currentState ->
                    val updatedList = (currentState.screenState as? Result.Success)?.data.orEmpty() + it
                    currentState.copy(
                        screenState = Result.Success(updatedList)
                    )
                }
            }.onFailure {
                isLoadingMore.value = false
                _uiState.update { currentState -> currentState.copy(screenState = Result.Error(it)) }
            }
        }
    }
}