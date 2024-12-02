package hu.blueberry.projectaquamarine.features.product.old

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hu.blueberry.persistentstorage.model.Product

@Composable
fun ProductRow(product: Product, navigateToDetails: ()-> Unit = {}){

    Box(modifier = Modifier
        .fillMaxWidth(),
        contentAlignment = Alignment.Center)
    {
        Button(modifier = Modifier.fillMaxWidth()
            .padding(vertical =  3.dp)
            .clip(RoundedCornerShape(10))
            .border(2.dp, Color.Black, RoundedCornerShape(10))
            .background(Color.Gray)
            .padding(5.dp),
            onClick = { navigateToDetails()}) {
            Text(text = product.name ?: "No product name given.", fontSize = 20.sp)
        }
        
    }

}