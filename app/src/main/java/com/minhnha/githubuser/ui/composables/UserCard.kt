package com.minhnha.githubuser.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.minhnha.domain.model.User

@Composable
fun UserCard(userInfo: User) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(horizontal = 20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = userInfo.avatarUrl,
                    contentScale = ContentScale.Crop
                ),
                contentDescription = "avatar",
                modifier = Modifier
                    .weight(1.5f)
                    .padding(horizontal = 10.dp)
            )
            Column(modifier = Modifier.weight(3f)) {
                Text(
                    text = userInfo.loginName ?: "Unknown",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                HorizontalDivider(
                    thickness = 1.dp,
                    color = Color.Gray,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
                Text(
                    text = userInfo.htmlUrl ?: "Unknown",
                    color = Color.Black
                )
            }
        }
    }
}

@Preview
@Composable
fun UserCardPreview() {
    val userInfo = User(
        loginName = "mojombo",
        avatarUrl = "https://avatars.githubusercontent.com/u/1?v=4",
        htmlUrl = "https://github.com/mojombo"
    )
    UserCard(userInfo = userInfo)
}