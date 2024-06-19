package com.example.uaskel1

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

/**
 * Composable for the screen that displays the checklist of all albums.
 */
@Composable
fun ListScreen(navController: NavController, albumList: MutableList<Album>) {

    val albums = remember { albumList }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(50, 56, 70))
    ) {

        Column {
            TopNavBar(navController = navController)
            Divider(color = androidx.compose.ui.graphics.Color.White, thickness = 1.dp)

            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                items(
                    items = albums,
                    itemContent = {
                        ListItem(navController, album = it)
                    }
                )
            }
        }
    }
}

/**
 * Composable for the top navigation bar.
 */
@Composable
private fun TopNavBar(navController: NavController) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {

        Box(
            modifier = Modifier
                .clickable {
                    navController.navigate(com.example.uaskel1.Screen.HomeScreen.route)
                }
                .padding(top = 10.dp, bottom = 10.dp, start = 15.dp, end = 15.dp)
        ) {
            Row {
                Icon(
                    Icons.Filled.Home,
                    contentDescription = null,
                    tint = androidx.compose.ui.graphics.Color.White
                )
                Text(
                    "  Home",
                    color = androidx.compose.ui.graphics.Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

/**
 * Composable for each list item.
 */
@Composable
private fun ListItem(navController: NavController, album: Album) {

    val color = when (album.listened.value) {
        false -> Color(56, 69, 96)
        true -> Color(50, 56, 70)
    }

    val message = when (album.listened.value) {
        false -> "Not yet listened"
        true -> "Listened!"
    }

    val messageColor = when (album.listened.value) {
        false -> androidx.compose.ui.graphics.Color.Yellow
        true -> Color(243, 146, 55)
    }

    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth()
            .clickable {
                navController.navigate(
                    com.example.uaskel1.Screen.AlbumScreen.withArgs(
                        (album.number)
                    )
                )
            },
        elevation = 2.dp,
        backgroundColor = color,
        shape = RoundedCornerShape(corner = CornerSize(16.dp))
    ) {
        Row {
            ListImage(album = album)
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
            ) {
                Row {
                    Text(text = "#${album.number}    ", style = typography.caption)
                    Text(text = message, style = typography.caption, color = messageColor)
                }

                Text(text = album.name, style = typography.h6)
                Text(text = "Artist: ${album.artist}", style = typography.caption)
            }
        }
    }
}

/**
 * Composable for each list image.
 */
@Composable
private fun ListImage(album: Album) {
    Image(
        painter = painterResource(id = album.art),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .padding(8.dp)
            .size(84.dp)
            .clip(RoundedCornerShape(corner = CornerSize(16.dp)))
    )
}