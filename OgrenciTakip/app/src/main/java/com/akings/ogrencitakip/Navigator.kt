package com.akings.ogrencitakip

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigator() {

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Destinations.LazyColumnScreen) {

        composable(route = Destinations.TextFieldScreen+"?bookId={bookId}"
        ){
            val updateViewModel:MyViewModel= hiltViewModel()
            val state = updateViewModel.state.value
            TextFieldScreen(
                navController,
                state=state,
                updateBook = updateViewModel::updateBook,
                addNewBook = updateViewModel::addNewBook
            )
        }
        composable(route = Destinations.LazyColumnScreen

        ){

            LazyColumnScreen(
                onItemClick = {bookId ->navController.navigate(Destinations.TextFieldScreen+"?bookId=$bookId")},
                navigateToBookTextField = {navController.navigate(Destinations.TextFieldScreen)}
            )
        }

    }
}

object Destinations {
    const val LazyColumnScreen = "lazy"
    const val TextFieldScreen = "text"
}