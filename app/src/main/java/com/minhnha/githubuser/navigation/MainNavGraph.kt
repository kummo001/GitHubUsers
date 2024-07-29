package com.minhnha.githubuser.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.minhnha.githubuser.ui.home.HomeView
import com.minhnha.githubuser.ui.userdetail.UserDetailView

fun NavGraphBuilder.mainNavGraph(
    navController: NavController
) {
    composable(route = HomeRoute.Home.Route) {
        HomeView(navController)
    }
    composable(route = UserDetailRoute.UserDetail.Route) {
        UserDetailView()
    }
}