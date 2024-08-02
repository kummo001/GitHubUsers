package com.minhnha.githubuser.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.minhnha.domain.model.User
import com.minhnha.domain.utils.Result
import com.minhnha.githubuser.ui.composables.ErrorDialog
import com.minhnha.githubuser.ui.composables.TopBar
import com.minhnha.githubuser.ui.composables.UserCard

@Composable
fun HomeView(navController: NavController) {
    val viewModel = hiltViewModel<HomeViewModel>()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val isLoading = remember {
        mutableStateOf(false)
    }
    val showErrorDialog = remember {
        mutableStateOf(false)
    }
    val errorMessage = remember {
        mutableStateOf("")
    }
    var listUser = remember {
        mutableListOf<User>()
    }
    LaunchedEffect(key1 = uiState) {
        viewModel.getUsers(1)
    }
    val listUserState = uiState.value.screenState
    when (listUserState) {
        is Result.Loading -> {
            isLoading.value = true
        }

        is Result.Error -> {
            isLoading.value = false
            showErrorDialog.value = true
            errorMessage.value = listUserState.exception.message.toString()
        }

        is Result.Success -> {
            isLoading.value = false
            listUser = listUserState.data.toMutableStateList()
        }

        is Result.Idle -> {
            isLoading.value = false
        }
    }
    HomeViewContent(
        listUser = listUser,
        showErrorDialog = showErrorDialog,
        errorMessage = errorMessage,
        isLoading = isLoading,
        isLoadingMore = viewModel.isLoadingMore,
        onUserCardClick = {
            navController.navigate("UserDetail/${it}")
        },
        onLoadMore = {
            if (listUserState is Result.Success) {
                //Call api to get 20 more users every time
                viewModel.getUsers(listUser.size / 20 + 1)
            }
        }
    ) {
        navController.popBackStack()
    }
}

@Composable
fun HomeViewContent(
    listUser: MutableList<User>,
    showErrorDialog: MutableState<Boolean>,
    errorMessage: MutableState<String>,
    isLoading: MutableState<Boolean>,
    isLoadingMore: MutableState<Boolean>,
    onUserCardClick: (loginName: String) -> Unit,
    onLoadMore: () -> Unit,
    onBackButtonClick: () -> Unit
) {
    val listState = rememberLazyListState()
    val layoutInfo = remember {
        derivedStateOf { listState.layoutInfo }
    }
    Scaffold(topBar = {
        TopBar(title = "GitHub Users") {
            onBackButtonClick()
        }
    }) { contentPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xFFFFFFFF))
                .padding(contentPadding)
        ) {
            LazyColumn(
                state = listState,
                modifier = Modifier.padding(horizontal = 20.dp)
            ) {
                items(count = listUser.size) { index ->
                    if (index == 0) {
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                    UserCard(userInfo = listUser[index]) {
                        onUserCardClick(it)
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
            if (layoutInfo.value.visibleItemsInfo.lastOrNull()?.index == listUser.size - 1) {
                if (!isLoadingMore.value) {
                    onLoadMore()
                }
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