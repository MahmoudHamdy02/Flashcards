package com.example.flashcards

sealed class Screen(val route: String) {
    data object HomeScreen : Screen("main_screen")
    data object PackScreen : Screen("pack_screen")
    data object BrowseScreen : Screen("browse_screen")
}