package com.example.practica4


import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.practica4.SpotyViewModel.spotyViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun spoty() {


    val contexto = LocalContext.current
    val spotyViewModel: spotyViewModel = viewModel()
    val cancionactual = spotyViewModel.cancionActual
    val reproduciendose = spotyViewModel.rep.collectAsState()
    val progreso = spotyViewModel.progresoCancion.collectAsState()




    LaunchedEffect(cancionactual){
        spotyViewModel.crearPlayer(contexto)
        spotyViewModel.sonarCanciones(contexto)
    }

        Column(
            horizontalAlignment = CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
                .padding(top = 50.dp)
                .padding(bottom = 60.dp)) {
            Text(
                text ="Now Playing",
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 25.sp)
            )
            Spacer(Modifier.height(10.dp))
            Text(
                text = cancionactual.value.nombre + " - " + cancionactual.value.artista,
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 25.sp)
            )
            Image(painter = painterResource(id = cancionactual.value.foto),
                contentDescription = null,
                modifier = Modifier
                    .weight(1f)
            )
            Spacer(Modifier.height(60.dp))
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { spotyViewModel.reproducirAleatoria() }) {
                    Icon(Icons.Default.Shuffle, contentDescription = null)
                }
                IconButton(onClick = { spotyViewModel.anterior(contexto) }) {
                    Icon(imageVector = Icons.Outlined.KeyboardArrowLeft, contentDescription = null)
                }
                IconButton(onClick = { spotyViewModel.reproducirCancion() }) {
                    val iconoReproduccion = if (reproduciendose.value) {
                        Icons.Default.Pause
                    } else {
                        Icons.Default.PlayArrow
                    }
                    Icon(iconoReproduccion, contentDescription = null)
                }
                IconButton(onClick = { spotyViewModel.CambiarCancion(contexto) }) {
                    Icon(imageVector = Icons.Outlined.KeyboardArrowRight, contentDescription = null)
                }
                IconButton(onClick = { spotyViewModel.reproducirBucle()}) {
                    Icon(Icons.Default.Repeat, contentDescription = null)
                }

            }
        }
    }



