package com.example.flashcards

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.flashcards.browse.BrowseScreen
import com.example.flashcards.home.HomeScreen
import com.example.flashcards.pack.PackScreen

val LocalNavigation = compositionLocalOf<NavHostController> { error("Not provided") }

@Composable
fun Navigation() {
    val navController = rememberNavController()

    CompositionLocalProvider(LocalNavigation provides navController) {
        NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
            composable(Screen.HomeScreen.route) {
                HomeScreen()
            }
            composable(
                route = Screen.PackScreen.route + "/{id}",
                arguments = listOf(
                    navArgument("id") {
                        type = NavType.IntType
                    }
                )) { entry ->
                val id = entry.arguments?.getInt("id")
                if (id != null)
                    PackScreen(packId = id)
            }
            composable(
                route = Screen.BrowseScreen.route + "/{id}",
                arguments = listOf(
                    navArgument("id") {
                        type = NavType.IntType
                    }
                )) { entry ->
                val id = entry.arguments?.getInt("id")
                if (id != null)
                    BrowseScreen(packId = id)
            }
        }
    }
}