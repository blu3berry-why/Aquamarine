package hu.blueberry.projectaquamarine.features.SpreadSheetOverviewScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun SpreadSheeOverviewScreen (
    viewModel: SpreadSheetOverviewViewModel = hiltViewModel()
){

}

@Preview
@Composable
fun SpreadSheetOverviewPreview() {
    SpreadSheeOverviewScreen()
}