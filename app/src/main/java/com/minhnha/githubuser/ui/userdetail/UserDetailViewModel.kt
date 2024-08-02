package com.minhnha.githubuser.ui.userdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.minhnha.domain.model.UserDetail
import com.minhnha.domain.usecase.remote.GetUserDetailUseCase
import com.minhnha.domain.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class UserDetailUiState(
    val userDetail: Result<UserDetail>
) {
    companion object {
        val default = UserDetailUiState(
            userDetail = Result.Loading
        )
    }
}

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val getUserDetailUseCase: GetUserDetailUseCase
) : ViewModel() {

    private var _uiState = MutableStateFlow(UserDetailUiState.default)
    val uiState: StateFlow<UserDetailUiState>
        get() = _uiState


    /**
     * Get user detail information
     * @param loginName user login name which you want to get information
     */
    fun getUserDetail(loginName: String) {
        viewModelScope.launch {
            val response = getUserDetailUseCase.invoke(loginName)
            response.onSuccess {
                _uiState.update { currentState -> currentState.copy(userDetail = Result.Success(it)) }
            }.onFailure {
                _uiState.update { currentState -> currentState.copy(userDetail = Result.Error(it)) }
            }
        }
    }
    /**
     * Reset screen state to Idle
     */
    fun resetScreenState() {
        _uiState.update { currentState -> currentState.copy(userDetail = Result.Idle) }
    }
}