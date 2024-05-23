package hu.blueberry.projectaquamarine.ui.stand

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import hu.blueberry.drinks.model.StandType
import hu.blueberry.projectaquamarine.navigation.stand.SingleStandItemScreen
import hu.blueberry.projectaquamarine.ui.stand.elemets.StandUnitCounterRow
import hu.blueberry.projectaquamarine.viewModel.SingleItemStandViewModel



@Composable
fun SingleItemStand(
    id: Long,
    itemCount: Int,
    standType: String,
    onNavigateToSingleItemStandPage: (SingleStandItemScreen) -> Unit,
    onNavigateToStandPage: ()->Unit,
    viewModel: SingleItemStandViewModel = hiltViewModel(),
) {

    val product = viewModel.product.collectAsState()
    LaunchedEffect(key1 = product.value) {
        viewModel.loadProductFromDatabase(id, "F17", StandType.fromStringValue(standType))
    }


    val bottleCount = viewModel.botteCount.collectAsState()
    val cartonCount = viewModel.cartonCount.collectAsState()
    val sum = viewModel.sum.collectAsState()


    val scaleValue = viewModel.scaleValue.collectAsState()

    product.value?.let { product ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.15f)
                    .background(Color.Blue), contentAlignment = Alignment.Center
            ) {
                Text(text = product.name ?: "No name given", fontSize = 25.sp)
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.80f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.2f)
                        .background(Color.Cyan),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = sum.value.toString(), fontSize = 25.sp, fontWeight = FontWeight.Bold)
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.5f)
                        .background(Color.Red)
                ) {

                    StandUnitCounterRow(
                        name = "Üveg",
                        value = bottleCount.value,
                        add = { viewModel.addBottle() },
                        remove = { viewModel.removeBottle() })

                    StandUnitCounterRow(
                        name = "Doboz",
                        value = cartonCount.value,
                        add = { viewModel.addCarton() },
                        remove = { viewModel.removeCarton() })
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .background(Color.Green),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextField(
                        value = scaleValue.value.toString(),
                        onValueChange = { viewModel.setScaleValue(it) },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    )
                    Button(onClick = {
                        viewModel.addScale()
                    }) {
                        Text(text = "Add to Value")
                    }
                }


            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(Color.Blue), contentAlignment = Alignment.Center
            ) {
                Row {
                    Button(modifier = Modifier.width(150.dp), onClick = { //Save product
                        viewModel.saveProductToDatabase(StandType.fromStringValue(standType), "F17")
                        //Go to next page, if there is no more pages return to the stand page
                        if (id > 1){
                            onNavigateToSingleItemStandPage(SingleStandItemScreen((id - 1), itemCount, standType ))
                        }else{
                            //TODO should I save here to the Sheets?
                            onNavigateToStandPage()
                        } }) {
                        Text(text = "Previous")
                    }
                    Spacer(modifier = Modifier.width(30.dp))
                    Button(modifier = Modifier.width(150.dp), onClick = {
                        //Save product
                        viewModel.saveProductToDatabase(StandType.fromStringValue(standType), "F17")
                        //Go to next page, if there is no more pages return to the stand page
                        if (id <= itemCount){
                            onNavigateToSingleItemStandPage(SingleStandItemScreen((id + 1), itemCount, standType ))
                        }else{
                            //TODO should I save here to the Sheets?
                            onNavigateToStandPage()
                        }
                    }) {
                        Text(text = "Next")
                    }

                }
            }
        }
    }
}

