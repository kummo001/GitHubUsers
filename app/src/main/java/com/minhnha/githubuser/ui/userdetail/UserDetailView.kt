package com.minhnha.githubuser.ui.userdetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.minhnha.data.mapper.toUser
import com.minhnha.domain.model.User
import com.minhnha.domain.model.UserDetail
import com.minhnha.domain.utils.Result
import com.minhnha.githubuser.R
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
    val userDetail = remember {
        mutableStateOf<UserDetail?>(null)
    }

    DisposableEffect(key1 = uiState) {
        onDispose {
            viewModel.resetScreenState()
        }
    }

    when (uiState.userDetail) {
        is Result.Loading -> {
            viewModel.getUserDetail(loginName)
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is Result.Success -> {
            userDetail.value = (uiState.userDetail as Result.Success).data
        }

        is Result.Error -> {
            errorMessage.value = (uiState.userDetail as Result.Error).exception.message ?: ""
            showErrorDialog.value = true
        }

        is Result.Idle -> {
            //do nothing
        }
    }
    UserDetailContent(
        userDetail = userDetail.value,
        showErrorDialog = showErrorDialog,
        isLoading = isLoading,
        errorMessage = errorMessage
    ) {
        navController.popBackStack()
    }
}

@Composable
fun UserDetailContent(
    userDetail: UserDetail?,
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
                if (userDetail != null) {
                    UserCard(
                        userInfo = userDetail.toUser(),
                        location = userDetail.location,
                        isDetailCard = true
                    )
                }
                UserSubscriptionInfo(userDetail = userDetail)
                Spacer(modifier = Modifier.height(20.dp))
                Footer(userDetail = userDetail)
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

@Composable
fun UserSubscriptionInfo(userDetail: UserDetail?) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.baseline_people_24),
                contentDescription = "location"
            )
            Text(
                text = "${userDetail?.followers ?: 0}", style = TextStyle(
                    color = Color.Black,
                    fontSize = 12.sp
                )
            )
            Text(
                text = "Follower", style = TextStyle(
                    color = Color.Black,
                    fontSize = 12.sp
                )
            )
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.baseline_people_24),
                contentDescription = "location"
            )
            Text(
                text = "${userDetail?.following ?: 0}", style = TextStyle(
                    color = Color.Black,
                    fontSize = 12.sp
                )
            )
            Text(
                text = "Following", style = TextStyle(
                    color = Color.Black,
                    fontSize = 12.sp
                )
            )
        }
    }
}

@Composable
fun Footer(userDetail: UserDetail?) {
    Text(
        text = "Blog",
        style = TextStyle(
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    )
    Spacer(modifier = Modifier.height(10.dp))
    Text(
        text = userDetail?.htmlUrl ?: "",
        style = TextStyle(
            color = Color.Black,
            fontSize = 12.sp
        )
    )
}