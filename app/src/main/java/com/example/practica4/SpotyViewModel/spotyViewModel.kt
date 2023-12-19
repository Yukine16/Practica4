package com.example.practica4.SpotyViewModel

import android.content.ContentResolver
import android.content.Context
import android.content.res.Resources
import android.net.Uri
import androidx.annotation.AnyRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.example.practica4.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class spotyViewModel : ViewModel(){
    private val _exoPlayer : MutableStateFlow<ExoPlayer?> = MutableStateFlow(null)
    val exoPlayer = _exoPlayer.asStateFlow()

    private val _Cancionactual  = MutableStateFlow(R.raw.migrane)
    val Cancionactual = _Cancionactual.asStateFlow()

    private val _duracionCancion  = MutableStateFlow(0)
    val duracionCancion = _duracionCancion.asStateFlow()

    private val _progresoCancion = MutableStateFlow(0)
    val progresoCancion = _progresoCancion.asStateFlow()

    fun crearPlayer(context: Context){
        _exoPlayer.value = ExoPlayer.Builder(context).build()
        _exoPlayer.value!!.prepare()
        _exoPlayer.value!!.playWhenReady = true
    }

    fun sonarCanciones(context : Context){
        var cancion = MediaItem.fromUri(obtenerRuta(context,_Cancionactual.value))
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
                else if(playbackState == Player.STATE_BUFFERING){

                }
                else if(playbackState == Player.STATE_ENDED){
                    CambiarCancion(context)

                }
                else if(playbackState == Player.STATE_IDLE){

                }

            }
        }
        )
    }

    override fun onCleared() {
        _exoPlayer.value!!.release()
        super.onCleared()
    }


    fun CambiarCancion(context: Context) {
        _exoPlayer.value!!.stop()
        _exoPlayer.value!!.clearMediaItems()
        if(_Cancionactual.value == R.raw.migrane) _Cancionactual.value = R.raw.catdog
        else _Cancionactual.value = R.raw.warofhormones
        _exoPlayer.value!!.setMediaItem(MediaItem.fromUri(obtenerRuta(context, _Cancionactual.value)))
        _exoPlayer.value!!.prepare()
        _exoPlayer.value!!.playWhenReady = true
    }

    fun PausarOSeguirMusica() {
        if (_exoPlayer.value!!.isPlaying){
            _exoPlayer.value!!.pause()
        }else {
            _exoPlayer.value!!.play()
        }
    }
}

@Throws(Resources.NotFoundException::class)
fun obtenerRuta(context: Context, @AnyRes resId: Int): Uri {
    val res: Resources = context.resources
    return Uri.parse(
        ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + res.getResourcePackageName(resId)
                + '/' + res.getResourceTypeName(resId)
                + '/' + res.getResourceEntryName(resId)
    )
}