package hu.blueberry.projectaquamarine.features.elements.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun WideFilledButton(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(vertical = 5.dp)
            .fillMaxWidth(0.9f).height(70.dp)
            .border(BorderStroke(1.dp, color = Color.LightGray), shape = RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .clickable {
                onClick.invoke()
            }
            .padding(vertical = 10.dp)

    )
    {
        Icon(
            imageVector = icon,
            contentDescription = text,
            modifier = Modifier.fillMaxHeight(0.9F).fillMaxWidth(0.2F).padding(end = 10.dp, start = 5.dp)
        )

        Text(
            text=text,
            fontSize = 15.sp,
            fontWeight = FontWeight(900)
        )

    }

}