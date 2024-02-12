package com.chattingapp.chattingapp.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.chattingapp.chattingapp.R
import com.chattingapp.chattingapp.activities.ui.theme.ChattingAppTheme
import com.chattingapp.chattingapp.constants.Constants
import com.chattingapp.chattingapp.viewmodel.UserViewModel
import com.chattingapp.chattingapp.views.fragments.FragmentHome
import com.chattingapp.chattingapp.views.fragments.FragmentProfile
import com.chattingapp.chattingapp.views.fragments.FragmentSearch


class HomePageActivity : ComponentActivity() {
    private lateinit var  userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChattingAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    getUserViewModel()
                    HomePageSkeleton(userViewModel)
                }
            }
        }
    }
    private fun getUserViewModel(){
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
    }

}


@Composable
fun HomePageSkeleton(userViewModel: UserViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "fragmentHome"
    ) {
        composable("fragmentHome") { FragmentHome(userViewModel) }
        composable("fragmentSearch") {
            Constants.resetClickedUser()
            FragmentSearch()
        }
        composable("fragmentProfile") { FragmentProfile() }

    }

    Scaffold(
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.fillMaxWidth().fillMaxHeight(0.05f),
                Color.Black

            ) {


                BottomNavigation {
                    BottomNavigationItem(
                        modifier = Modifier.background(Color.Black),
                        selected = navController.currentDestination?.route == "fragmentHome",
                        onClick = {
                            navController.navigate("fragmentHome")
                        },
                        icon = {
                            Image(
                                painter = painterResource(R.drawable.home),
                                contentDescription = null
                            )
                        }   ,
                    )

                    BottomNavigationItem(
                        modifier = Modifier.background(Color.Black),
                        selected = navController.currentDestination?.route == "fragmentSearch",
                        onClick = {
                            navController.navigate("fragmentSearch")
                        },
                        icon = {
                            Image(
                                painter = painterResource(R.drawable.search),
                                contentDescription = null
                            )
                        }
                    )

                    BottomNavigationItem(
                        modifier = Modifier.background(Color.Black),
                        selected = navController.currentDestination?.route == "fragmentProfile",
                        onClick = {
                            navController.navigate("fragmentProfile")
                        },
                        icon = {
                            Image(
                                painter = painterResource(R.drawable.profile),
                                contentDescription = null
                            )
                        }                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "fragmentHome",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("fragmentHome") { FragmentHome(userViewModel) }
            composable("fragmentSearch") {
                Constants.resetClickedUser()
                FragmentSearch()
            }
            composable("fragmentProfile") {
                Constants.resetClickedUser()
                FragmentProfile()
            }

        }
    }
}

