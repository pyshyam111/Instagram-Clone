package com.shyam.instagramclone

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.shyam.instagramclone.screen.HomeScreen
import com.shyam.instagramclone.screen.NotificationScreen
import com.shyam.instagramclone.screen.ProfileScreen
import com.shyam.instagramclone.screen.ReelsScreen
import com.shyam.instagramclone.screen.SearchScreen
import com.shyam.instagramclone.ui.theme.BottomNavRoutes

@Composable
fun InstagramNavGraph(navHostController: NavHostController) {

    NavHost(
        navController = navHostController,
        startDestination = BottomNavRoutes.HOME.routes
    ) {
        composable(route = BottomNavRoutes.HOME.routes) {
            HomeScreen()
        }
        composable(route = BottomNavRoutes.SEARCH.routes) {
            SearchScreen()
        }
        composable(route = BottomNavRoutes.REELS.routes) {
            ReelsScreen()
        }
        composable(route = BottomNavRoutes.NOTIFICATION.routes) {
            NotificationScreen()
        }
        composable(route = BottomNavRoutes.PROFILE.routes) {
            ProfileScreen()
        }
    }
}
