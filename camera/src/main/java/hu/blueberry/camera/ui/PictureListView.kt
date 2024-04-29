package hu.blueberry.camera.ui

import android.graphics.Bitmap
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.blueberry.camera.models.photo.InternalStoragePhoto

@Composable
fun PictureListView(images: List<InternalStoragePhoto>) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(minSize = 108.dp),
        verticalItemSpacing = 4.dp,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.padding(4.dp)
    ) {

        items(images.size) {
            PicturePreviewGridItem(image = images[it].bitmap, name = images[it].name)
        }
    }
}

@Composable
fun PicturePreviewGridItem(image: Bitmap, name: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10))
            .border(border = BorderStroke(4.dp, Color.DarkGray), RoundedCornerShape(10))
            .background(Color.LightGray),
        contentAlignment = Alignment.CenterStart
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
        ) {
            Image(
                bitmap = image.asImageBitmap(),
                contentDescription = name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(157.dp)
            )

            Text(text = name, modifier = Modifier.padding(2.dp))
        }

        /**/

    }
}

@Preview
@Composable
fun PictureListViewPreview() {
    PictureListView(listOf())
}