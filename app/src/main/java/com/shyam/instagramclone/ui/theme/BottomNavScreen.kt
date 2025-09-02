package com.shyam.instagramclone.ui.theme

import com.shyam.instagramclone.R
sealed class BottomNavScreen(
    val title: String,
    val icon: Int= R.drawable.home,
    val route: String
) {
    object Home : BottomNavScreen(title = "", icon = R.drawable.home, route = BottomNavRoutes.HOME.routes)
    object Search : BottomNavScreen(title = "", icon = R.drawable.search, route = BottomNavRoutes.SEARCH.routes)
    object Reels : BottomNavScreen(title = "", icon = R.drawable.reels, route = BottomNavRoutes.REELS.routes)
    object Notifiction : BottomNavScreen(title = "", icon = R.drawable.notification, route = BottomNavRoutes.NOTIFICATION.routes)
    object Profile : BottomNavScreen(title = "", icon = R.drawable.profile, route = BottomNavRoutes.PROFILE.routes)
}

enum class BottomNavRoutes(val routes: String) {
    HOME("home"),
    SEARCH("search"),
    NOTIFICATION("plus"),
    REELS("reels"),
    PROFILE("profile")
}
