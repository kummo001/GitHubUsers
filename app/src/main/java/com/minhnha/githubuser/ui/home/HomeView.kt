package com.minhnha.githubuser.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.minhnha.domain.model.User
import com.minhnha.domain.utils.UiState
import com.minhnha.githubuser.theme.GitHubUserTheme
import com.minhnha.githubuser.ui.composables.TopBar
import com.minhnha.githubuser.ui.composables.UserCard

@Composable
fun HomeView(navController: NavController) {
    val viewModel = hiltViewModel<HomeViewModel>()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = uiState) {
        viewModel.getUsers(1)
    }
    when(val listUser = uiState.value.listUsers) {
        is UiState.Loading -> {
            println("Loading now")
        }
        is UiState.Error -> {
            println("Error")
        }
        is UiState.Success -> {
            println(listUser.data)
        }
    }
    HomeViewContent {
        navController.popBackStack()
    }
}

@Composable
fun HomeViewContent(
    onBackButtonClick: () -> Unit
) {
    val userInfo = User(
        loginName = "mojombo",
        avatarUrl = "https://avatars.githubusercontent.com/u/1?v=4",
        htmlUrl = "https://github.com/mojombo"
    )
    Scaffold(topBar = {
        TopBar(title = "GitHub Users") {
            onBackButtonClick()
        }
    }) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xFFFFFFFF))
                .padding(contentPadding)
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            UserCard(userInfo = userInfo)
            Spacer(modifier = Modifier.height(20.dp))
            UserCard(userInfo = userInfo)
            Spacer(modifier = Modifier.height(20.dp))
            UserCard(userInfo = userInfo)
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Preview
@Composable
fun HomeViewPreview() {
    GitHubUserTheme {
        HomeViewContent {}
    }
}