package com.example.practica4


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun spoty() {
    var volumen by remember { mutableStateOf(0F) }
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        item{Text(
            text ="Now Playing",
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 25.sp)

        )}
        item{
            Column (modifier = Modifier
                .padding(16.dp)){
                Image(painter = painterResource(id = R.drawable.top),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp))
            }
        }
        item{
            Column (modifier = Modifier
                .padding(16.dp)){
                Slider(
                    value = volumen,
                    onValueChange = { volumen = it },
                    onValueChangeFinished = {

                    },
                    steps = 50,
                    valueRange = 100F..140F
                )
            }
        }
        item {
            Column(modifier = Modifier
                .padding(16.dp)) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically) {
                    Button(onClick = { /*TODO*/ }) {
                        //Icon(painter = painterResource(id = R.drawable.ale), contentDescription = null)
                    }
                    Button(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Outlined.KeyboardArrowLeft, contentDescription = null)
                    }
                    Button(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Outlined.PlayArrow, contentDescription = null)
                    }
                    Button(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Outlined.KeyboardArrowRight, contentDescription = null)
                    }
                    Button(onClick = { /*TODO*/ }) {
                        Icon(painter = painterResource(id = R.drawable.bucle), contentDescription = null)
                    }

                }
            }
        }
    }
}