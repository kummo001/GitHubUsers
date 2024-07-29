package com.minhnha.githubuser.ui.userdetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.minhnha.githubuser.theme.GitHubUserTheme
import com.minhnha.githubuser.ui.composables.TopBar

@Composable
fun UserDetailView(navController: NavController) {
    UserDetailContent {
        navController.popBackStack()
    }
}

@Composable
fun UserDetailContent(
    onBackPress: () -> Unit
) {
    Scaffold(topBar = {
        TopBar(title = "User Details") {
            onBackPress()
        }
    }) { contentPadding ->
        Column(modifier = Modifier.padding(contentPadding)) {

        }
    }
}

@Preview
@Composable
fun UserDetailViewPreView() {
    GitHubUserTheme {
        UserDetailContent {

        }
    }
}