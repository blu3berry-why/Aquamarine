package hu.blueberry.projectaquamarine.features.stand.elemets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun StandUnitCounterRow(
    name: String,
    value: Int,
    add:() -> Unit,
    remove: ()-> Unit,
){
    Row (
        Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp), horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.CenterVertically){
        Button(onClick = { remove() } ) {
            Text(text = "-")
        }

        Text(text = "$name : $value")

        Button(onClick = { add () } ) {
            Text(text = "+")
        }
    }
}