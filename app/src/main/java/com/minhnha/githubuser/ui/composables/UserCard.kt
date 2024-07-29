package com.minhnha.githubuser.ui.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.minhnha.domain.entity.UserInfo

@Composable
fun UserCard(userInfo: UserInfo) {

}

@Preview
@Composable
fun UserCardPreview() {
    val userInfo = UserInfo("Minh", "github.com/minh", "minh.com")
    UserCard(userInfo = userInfo)
}