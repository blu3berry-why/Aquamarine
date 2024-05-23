package hu.blueberry.projectaquamarine.ui.stand

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun StandPage (
){
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {

        Button(onClick = { /*TODO*/ }) {
            Text(text = "Open")
        }

        Button(onClick = { /*TODO*/ }) {
            Text(text = "Cart")
        }

        Button(onClick = { /*TODO*/ }) {
            Text(text = "Close")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = { /*TODO*/ }) {
            Text(text = "Save")
        }
    }

}