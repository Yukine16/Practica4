package com.example.practica4



data class Canciones(
            val nombre:String,
            val artista: String,
            val foto: Int,
            val duracion: String,
            val cancion: Int
)

val listaCanciones = listOf(
    Canciones("Migraine", "Twenty One Pilots", R.drawable.top, "3:59", R.raw.migrane),
    Canciones("Cat&Dog", "TXT", R.drawable.txt, "3:24", R.raw.catdog),
    Canciones("War of hormones", "BTS", R.drawable.bts, "2:13", R.raw.warofhormones),
    Canciones("Friction", "Imagine Dragons", R.drawable.imaginedragons, "3:14", R.raw.friction),
    Canciones("Pet Cheeta", "Tewnty One Pilots", R.drawable.top2, "2:34", R.raw.petcheeta)
)
