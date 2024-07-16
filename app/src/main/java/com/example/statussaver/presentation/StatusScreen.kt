package com.example.statussaver.presentation

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size

@Composable
fun StatusScreen(
    modifier: Modifier,
    onDownloadClick: () -> Unit = {},
    onImagesButtonClick: () -> Unit,
    onVideosButtonClick: () -> Unit,
    photoUris: List<Uri>,
) {

    val uriList = listOf(1, 2, 2, 2, 5, 7, 9)

    Column(modifier = modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {

            ElevatedButton(
                onClick = {
                    //update uri list
                    onImagesButtonClick()
                },
                elevation = ButtonDefaults.buttonElevation(5.dp)
            ) {
                Icon(imageVector = Icons.Default.Image, contentDescription = "images")
                Spacer(modifier = Modifier.size(20.dp))
                Text(text = "Photos", fontSize = 20.sp)
            }
            ElevatedButton(
                onClick = {
                    //update uri list
                    onVideosButtonClick()
                },
                elevation = ButtonDefaults.buttonElevation(5.dp)
            ) {
                Icon(imageVector = Icons.Default.VideoLibrary, contentDescription = "videos")
                Spacer(modifier = Modifier.size(20.dp))
                Text(text = "Videos", fontSize = 20.sp)
            }
        }

        if (photoUris.isEmpty()) {
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = " No Statuses to show  or \n you need to give permission to access folder\n click on the top-right most icon to give permission",
                    modifier = Modifier.size(400.dp),
                )
            }
        }
        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
            items(photoUris) {
                StatusPhotoCard(bitmap = getBitMap(uri = it))
            }
        }


    }

}


@Composable
fun StatusPhotoCard(
    onDownloadClick: () -> Unit = {},
    bitmap: Bitmap? = null,
) {
    Box(
        modifier = Modifier
            .padding(10.dp)
            .height(300.dp)
    ) {
        ElevatedCard(
            modifier = Modifier
                .fillMaxSize(),
            elevation = CardDefaults.cardElevation(20.dp),
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                bitmap?.let {
                    Image(
                        bitmap = it.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }

                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomEnd)
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.primaryContainer)
                    ) {
                        IconButton(
                            onClick = { onDownloadClick() }, Modifier.align(Alignment.Center),
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Download,
                                contentDescription = "download",
                                modifier = Modifier.size(40.dp),
                                tint = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                }


            }
        }
    }
}

@Composable
fun getBitMap(uri: Uri?): Bitmap? {
    val imageState: AsyncImagePainter.State = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(uri)
            .size(Size.ORIGINAL)
            .build()
    ).state

    if (imageState is AsyncImagePainter.State.Success) {
        return imageState.result.drawable.toBitmap()
    }

    return null
}






