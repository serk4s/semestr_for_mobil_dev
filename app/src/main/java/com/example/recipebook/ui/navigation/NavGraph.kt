package com.example.recipebook.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.navArgument
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavType
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.recipebook.ui.detail.RecipeDetailScreen
import com.example.recipebook.ui.list.RecipeListScreen


@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "list") {

        composable("list") {
            RecipeListScreen(
                viewModel = hiltViewModel(),
                onRecipeClick = { id ->
                    navController.navigate("detail/$id")
                }
            )
        }

        composable(
            route = "detail/{id}",
            arguments = listOf(
                navArgument("id") { type = NavType.IntType }
            )
        ) {
            RecipeDetailScreen(
                viewModel = hiltViewModel(),
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
