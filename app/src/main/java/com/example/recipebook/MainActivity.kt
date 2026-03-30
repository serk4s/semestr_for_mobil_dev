package com.example.recipebook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.recipebook.data.local.AppDatabase
import com.example.recipebook.data.remote.RecipeApi
import com.example.recipebook.data.remote.RetrofitClient
import com.example.recipebook.data.repository.RecipeRepositoryImpl
import com.example.recipebook.ui.detail.RecipeDetailScreen
import com.example.recipebook.ui.detail.RecipeDetailViewModel
import com.example.recipebook.ui.list.RecipeListScreen
import com.example.recipebook.ui.list.RecipeListViewModel
import com.example.recipebook.ui.theme.RecipeBookTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val api = RetrofitClient.retrofit.create(RecipeApi::class.java)

        val database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "recipe_db"
        ).build()

        val repository = RecipeRepositoryImpl(
            api = api,
            dao = database.recipeDao()
        )

        setContent {
            RecipeBookTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "list"
                    ) {
                        composable("list") {
                            val viewModel = remember {
                                RecipeListViewModel(repository)
                            }
                            RecipeListScreen(
                                viewModel = viewModel,
                                onRecipeClick = { id ->
                                    navController.navigate("detail/$id")
                                }
                            )
                        }

                        composable(
                            route = "detail/{id}",
                            arguments = listOf(
                                androidx.navigation.navArgument("id") {
                                    type = NavType.IntType
                                }
                            )
                        ) { backStackEntry ->
                            val recipeId = backStackEntry.arguments?.getInt("id")

                            if (recipeId != null) {
                                val viewModel = remember(recipeId) {
                                    RecipeDetailViewModel(
                                        repository = repository,
                                        recipeId = recipeId
                                    )
                                }
                                RecipeDetailScreen(
                                    viewModel = viewModel,
                                    onBackClick = { navController.popBackStack() }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
