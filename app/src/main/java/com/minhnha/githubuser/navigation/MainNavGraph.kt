package com.minhnha.githubuser.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.minhnha.githubuser.ui.home.HomeView
import com.minhnha.githubuser.ui.userdetail.UserDetailView

fun NavGraphBuilder.mainNavGraph(
    navController: NavController
) {
    composable(route = HomeRoute.Home.Route) {
        HomeView(navController)
    }
    composable(
        route = UserDetailRoute.UserDetail.Route,
        arguments = listOf(navArgument("loginName") {
            type = NavType.StringType
        })
    ) { backStackEntry ->
        val loginName = backStackEntry.arguments?.getString("loginName") ?: ""
        UserDetailView(navController, loginName)
    }
}