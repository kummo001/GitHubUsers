package com.minhnha.githubuser.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.minhnha.domain.model.User
import com.minhnha.githubuser.R

@Composable
fun UserCard(
    userInfo: User,
    isDetailCard: Boolean = false,
    location: String? = null,
    onClick: (name: String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clickable {
                onClick(userInfo.loginName)
            },
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
            SubcomposeAsyncImage(
                modifier = Modifier
                    .weight(1.5f)
                    .padding(horizontal = 10.dp),
                model = userInfo.avatarUrl,
                contentDescription = "avatar",
                contentScale = ContentScale.Crop,
                loading = {
                    CircularProgressIndicator()
                }
            )
            Column(modifier = Modifier.weight(3f)) {
                Text(
                    text = userInfo.loginName,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                HorizontalDivider(
                    thickness = 1.dp,
                    color = Color.Gray,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
                if (isDetailCard) {
                    Row {
                        Image(
                            imageVector = ImageVector.vectorResource(id = R.drawable.baseline_location_on_24),
                            contentDescription = "location"
                        )
                        Text(
                            text = location.toString(),
                            color = Color.Black
                        )
                    }
                } else {
                    Text(
                        text = userInfo.htmlUrl,
                        color = Color.Black
                    )
                }
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
    UserCard(userInfo = userInfo) {

    }
}