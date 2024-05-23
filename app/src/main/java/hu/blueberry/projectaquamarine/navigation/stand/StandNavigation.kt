package hu.blueberry.projectaquamarine.navigation.stand

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navigation
import androidx.navigation.toRoute
import hu.blueberry.projectaquamarine.ui.stand.SingleItemStand

import hu.blueberry.projectaquamarine.ui.stand.StandPage

fun NavGraphBuilder.addStandNestedGraph(navController: NavController){
    navigation<StandNavigation>(startDestination = StandScreen){
        composable<StandScreen> {
            StandPage ( { navController.navigate(it) } )
        }

        composable<SingleStandItemScreen> {
            val args = it.toRoute<SingleStandItemScreen>()
            SingleItemStand(
                id = args.id,
                itemCount = args.itemCount,
                standType = args.standType,
                onNavigateToSingleItemStandPage = { singleStandItemScreen -> navController.navigate(singleStandItemScreen)},
                onNavigateToStandPage = {
                    navController.navigate(StandNavigation){
                    popUpTo(StandNavigation){
                        inclusive = true
                    }
                } })
        }
    }
}