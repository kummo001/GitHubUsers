package com.minhnha.githubuser.navigation

abstract class GitHubUserRoute {
    abstract val route: String
}

sealed class HomeRoute : GitHubUserRoute() {
    class Home: HomeRoute() {
        override val route = Route

        companion object {
            const val Route = "Home"
        }
    }
}

sealed class UserDetailRoute : GitHubUserRoute() {
    class UserDetail: UserDetailRoute() {
        override val route = Route

        companion object {
            const val Route = "UserDetail/{loginName}"
        }
    }
}
