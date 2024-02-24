package com.example.practica4.SpotyViewModel

import android.content.ContentResolver
import android.content.Context
import android.content.res.Resources
import android.net.Uri
import androidx.annotation.AnyRes
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.example.practica4.Canciones
import com.example.practica4.R
import com.example.practica4.listaCanciones
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class spotyViewModel : ViewModel(){
    private val _exoPlayer : MutableStateFlow<ExoPlayer?> = MutableStateFlow(null)
    val exoPlayer = _exoPlayer.asStateFlow()

    private val _duracionCancion  = MutableStateFlow(0)
    val duracionCancion = _duracionCancion.asStateFlow()

    private val _progresoCancion = MutableStateFlow(0)
    val progresoCancion = _progresoCancion.asStateFlow()

    private val _rep = MutableStateFlow(true)
    val rep = _rep.asStateFlow()

    var indCancionActual = MutableStateFlow(0)
    var cancionActual = mutableStateOf(listaCanciones[indCancionActual.value])

    private val _modoReproduccion = MutableStateFlow(Reproduccion.NORMAL)
    val modoReproduccion = _modoReproduccion.asStateFlow()

    enum class Reproduccion {
        NORMAL,
        ALEATORIA,
        BUCLE
    }

    fun crearPlayer(context: Context){
        _exoPlayer.value = ExoPlayer.Builder(context).build()
        _exoPlayer.value!!.prepare()
        _exoPlayer.value!!.playWhenReady = true
    }

    fun sonarCanciones(context : Context){
        var cancion =
            MediaItem.fromUri("android.resource://${context.packageName}/${cancionActual.value.cancion}")
        _exoPlayer.value!!.setMediaItem(cancion)
        _exoPlayer.value!!.playWhenReady = true
        _exoPlayer.value!!.addListener(object : Player.Listener{
            override fun onPlaybackStateChanged(playbackState: Int) {
                if(playbackState == Player.STATE_READY){
                    _duracionCancion.value = _exoPlayer.value!!.duration.toInt()

                    viewModelScope.launch {
                        while(isActive){
                            _progresoCancion.value = _exoPlayer.value!!.currentPosition.toInt()
                            delay(1000)
                        }
                    }
                }
                else if (playbackState == Player.STATE_BUFFERING)  {
                }
                else if(playbackState == Player.STATE_ENDED) {
                    CambiarCancion(context)
                }
                else if (playbackState == Player.STATE_IDLE){
                }
            }
        }
        )
    }

    fun CambiarCancion(context: Context) {
        if (_modoReproduccion.value == Reproduccion.ALEATORIA) {
            val nuevoIndice = (0 until listaCanciones.size).random()
            indCancionActual.value = nuevoIndice
        } else if (_modoReproduccion.value == Reproduccion.BUCLE) {
        } else {
            indCancionActual.value = (indCancionActual.value + 1) % listaCanciones.size
        }

        cancionActual.value = listaCanciones[indCancionActual.value]
        cargarCancion(context, cancionActual.value)
        _rep.value = !_exoPlayer.value!!.isPlaying
    }

    fun anterior(context: Context) {
        if (_modoReproduccion.value == Reproduccion.ALEATORIA) {
            if (_progresoCancion.value <= 3000) {
                val nuevoIndice = (0 until listaCanciones.size).random()
                indCancionActual.value = nuevoIndice
            }
        } else if (_modoReproduccion.value == Reproduccion.BUCLE) {
        } else {
            if (_progresoCancion.value <= 3000) {
                indCancionActual.value =
                    if (indCancionActual.value > 0) {
                        indCancionActual.value - 1
                    } else {
                        listaCanciones.size - 1
                    }
            }
        }

        cancionActual.value = listaCanciones[indCancionActual.value]
        cargarCancion(context, cancionActual.value)
        _rep.value = !_exoPlayer.value!!.isPlaying
    }

    fun reproducirCancion() {
        _rep.value = !_exoPlayer.value!!.isPlaying
        if (_exoPlayer.value!!.isPlaying) {
            _exoPlayer.value!!.pause()
        } else {
            _exoPlayer.value!!.play()
        }
    }
    fun reproducirAleatoria() {
        _modoReproduccion.value =
            if (_modoReproduccion.value != Reproduccion.ALEATORIA) Reproduccion.ALEATORIA else Reproduccion.NORMAL
        _exoPlayer.value?.shuffleModeEnabled = (_modoReproduccion.value == Reproduccion.ALEATORIA)
    }

    fun reproducirBucle() {
        _modoReproduccion.value =
            if (_modoReproduccion.value != Reproduccion.BUCLE) Reproduccion.BUCLE else Reproduccion.NORMAL
        _exoPlayer.value?.repeatMode = when (_modoReproduccion.value) {
            Reproduccion.BUCLE -> Player.REPEAT_MODE_ONE
            else -> Player.REPEAT_MODE_OFF
        }
    }

    fun actualizarProgresoCancion(nuevaPosicion: Int) {
        val exoPlayer = _exoPlayer.value ?: return

        exoPlayer.seekTo(nuevaPosicion.toLong())
    }

    private fun cargarCancion(context: Context, cancion: Canciones) {
        val mediaItem =
            MediaItem.fromUri("android.resource://${context.packageName}/${cancion.cancion}")

        _exoPlayer.value?.setMediaItem(mediaItem)
        _exoPlayer.value?.prepare()
        _exoPlayer.value?.play()
    }
}

