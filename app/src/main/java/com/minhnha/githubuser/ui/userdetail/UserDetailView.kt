package com.minhnha.githubuser.ui.userdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.minhnha.data.mapper.toUser
import com.minhnha.domain.model.UserDetail
import com.minhnha.domain.utils.Result
import com.minhnha.githubuser.ui.composables.ErrorDialog
import com.minhnha.githubuser.ui.composables.TopBar
import com.minhnha.githubuser.ui.composables.UserCard

@Composable
fun UserDetailView(
    navController: NavController,
    loginName: String
) {
    val viewModel = hiltViewModel<UserDetailViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val isLoading = remember {
        mutableStateOf(false)
    }
    val showErrorDialog = remember {
        mutableStateOf(false)
    }
    val errorMessage = remember {
        mutableStateOf("")
    }

    when (uiState.userDetail) {
        is Result.Loading -> {
            viewModel.getUserDetail("mojombo")
        }

        is Result.Success -> {
            val userDetail = (uiState.userDetail as Result.Success).data
            UserDetailContent(
                userDetail = userDetail,
                showErrorDialog = showErrorDialog,
                isLoading = isLoading,
                errorMessage = errorMessage
            ) {
                navController.popBackStack()
            }
        }

        is Result.Error -> {
            showErrorDialog.value = true
        }
    }
}

@Composable
fun UserDetailContent(
    userDetail: UserDetail,
    showErrorDialog: MutableState<Boolean>,
    errorMessage: MutableState<String>,
    isLoading: MutableState<Boolean>,
    onBackPress: () -> Unit
) {
    Scaffold(topBar = {
        TopBar(title = "User Details") {
            onBackPress()
        }
    }) { contentPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xFFFFFFFF))
                .padding(contentPadding)
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                UserCard(
                    userInfo = userDetail.toUser(),
                    location = userDetail.location,
                    isDetailCard = true
                ) {
                    //Don't need to implement click event for this detail card
                }
                Text(text = "following: ${userDetail.following}")
                Text(text = "follower: ${userDetail.followers}")
            }
            if (showErrorDialog.value) {
                ErrorDialog(
                    error = errorMessage,
                    showErrorDialog = showErrorDialog
                )
            }
            if (isLoading.value) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}