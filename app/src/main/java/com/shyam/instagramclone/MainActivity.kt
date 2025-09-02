package com.shyam.instagramclone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.shyam.instagramclone.screen.StoriesSection
import com.shyam.instagramclone.ui.theme.BottomNavRoutes
import com.shyam.instagramclone.ui.theme.BottomNavScreen
import com.shyam.instagramclone.ui.theme.InstagramCloneTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InstagramCloneTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    InstagramApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun InstagramApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavBar(navController = navController)
        },
        containerColor = Color.White
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)){
            InstagramNavGraph(
                navHostController = navController
            )

        }
    }
}

@Composable
fun BottomNavBar(navController: androidx.navigation.NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val screens = listOf(
        BottomNavScreen.Home,
        BottomNavScreen.Search,
        BottomNavScreen.Reels,
        BottomNavScreen.Notifiction,
        BottomNavScreen.Profile,
    )

    NavigationBar {
        screens.forEach { screen ->
            NavigationBarItem(
                selected = currentDestination?.route == screen.route,
                onClick = {
                    if (currentDestination?.route != screen.route) {
                        navController.navigate(screen.route)
                    }
                },
                icon = {
                    if(screen.route == BottomNavRoutes.PROFILE.routes){
                        CircularProfileView()
                    }else{
                    Icon(
                        painter = painterResource(id = screen.icon),
                        contentDescription = "nac icon",
                        modifier = Modifier.size(28.dp),


                    )
                    }
                },
                modifier = Modifier.background(color = Color.White),
                label = {
                    Text(screen.title)
                }
            )
        }
    }

}


@Composable
fun CircularProfileView() {
    Image(
        painter = painterResource(id = R.drawable.hardik),
        contentDescription = "Profile picture",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(24.dp)
            .clip(CircleShape)
            .background(Color.White)
    )
}

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    CircularProfileView()
    //StoriesSection()

}
