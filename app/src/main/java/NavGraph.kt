package com.example.notetakingapp

import NotingScreen
import WishViewModel
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import com.example.notetakingapp.ui.screens.HomeView

@Composable
fun NavGraph(navController: NavController) {
    val navController = rememberNavController() // Initialize NavController

    // Get Notes DAO instance from Singleton
    val context = LocalContext.current
    val notesDao = Singleton_Note.getNoteDao(context)

    // Initialize ViewModel directly with DAO
    val wishViewModel = WishViewModel(notesDao)

    // Collect the notes from the StateFlow using collectAsState
    val notes = wishViewModel.notes.collectAsState().value

    NavHost(navController = navController, startDestination = "HomeView") {
        composable("HomeView") {
            HomeView(navController = navController, notes = notes) // Pass the collected notes
        }
        composable("NotingScreen") {
            NotingScreen(
                navController = navController,
                viewModel = wishViewModel
            )
        }
    }
}
