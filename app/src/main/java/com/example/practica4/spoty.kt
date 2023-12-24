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
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.practica4.SpotyViewModel.spotyViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@Composable
fun cancionesCuadro(canciones: Canciones){
    Text(
        text = canciones.nombre,
        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp)

    )
    Column (modifier = Modifier
        .padding(16.dp)){
        Image(painter = painterResource(id = canciones.foto),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp))
    }

    Column (modifier = Modifier
        .padding(16.dp)) {
        Slider(
            value = canciones.duracion,
            onValueChange = {
                canciones.duracion = it
            },
            onValueChangeFinished = {

            },
            valueRange = 0f..canciones.duracion.toFloat()
        )
    }
}


@Composable
fun spoty() {

    val listaCanciones = listOf(
        Canciones("Migraine", 239F, R.drawable.top),
        Canciones("Ca&Dog", 187F, R.drawable.txt),
        Canciones("War of Hormones", 265F, R.drawable.bts),
        Canciones("Friction", 201F, R.drawable.imaginedragons),
        Canciones("Pet Cheeta", 198F, R.drawable.top2)
    )

    var duracion by remember { mutableStateOf(0F) }
    var cancionactual by remember { mutableStateOf(0)}
    var currentSongIndex by remember { mutableStateOf(0) }
    val spotyViewModel: spotyViewModel = spotyViewModel()
    val contexto = LocalContext.current



    LaunchedEffect(cancionactual){
        spotyViewModel.crearPlayer(contexto)
        spotyViewModel.sonarCanciones(contexto, cancionactual)
    }


    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        item { Text(
            text ="Now Playing",
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 25.sp)

        ) }

        item {
            cancionesCuadro(listaCanciones[cancionactual])
        }

        item {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = {
                    cancionactual = (0 until listaCanciones.size).random()
                    duracion = listaCanciones[cancionactual].duracion
                    spotyViewModel.CambiarCancion(contexto, cancionactual) }) {
                    Icon(painter = painterResource(id = R.drawable.ale), contentDescription = null)
                }
                IconButton(onClick = {
                    if (cancionactual > 0) {
                        cancionactual--
                        duracion = listaCanciones[cancionactual].duracion
                        spotyViewModel.CambiarCancion(contexto, cancionactual)
                    }
                }) {
                    Icon(imageVector = Icons.Outlined.KeyboardArrowLeft, contentDescription = null)
                }
                IconButton(onClick = { spotyViewModel.PausarOSeguirMusica() }) {
                    Icon(imageVector = Icons.Outlined.PlayArrow, contentDescription = null)
                }
                IconButton(onClick = {
                    if (cancionactual < listaCanciones.size - 1) {
                        cancionactual++
                        duracion = listaCanciones[cancionactual].duracion
                        spotyViewModel.CambiarCancion(contexto, cancionactual)
                    }
                }) {
                    Icon(imageVector = Icons.Outlined.KeyboardArrowRight, contentDescription = null)
                }
                IconButton(onClick = {  }) {
                    Icon(painter = painterResource(id = R.drawable.bucle), contentDescription = null)
                }

            }
        }
    }
}

