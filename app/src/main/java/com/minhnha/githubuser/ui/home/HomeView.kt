package com.minhnha.githubuser.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.minhnha.domain.entity.UserInfo
import com.minhnha.githubuser.theme.GitHubUserTheme
import com.minhnha.githubuser.ui.composables.TopBar
import com.minhnha.githubuser.ui.composables.UserCard

@Composable
fun HomeView(navController: NavController) {
    HomeViewContent {
        navController.popBackStack()
    }
}

@Composable
fun HomeViewContent(
    onBackButtonClick: () -> Unit
) {
    val userInfo = UserInfo(
        loginName = "mojombo",
        avatarUrl = "https://avatars.githubusercontent.com/u/1?v=4",
        htmlUrl = "https://github.com/mojombo"
    )
    Scaffold(topBar = {
        TopBar(title = "GitHub Users") {
            onBackButtonClick()
        }
    }) { contentPadding ->
        Column(modifier = Modifier.padding(contentPadding)) {
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