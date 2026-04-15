package com.example.aplicacionperfiles.model

data class UserProfile(
    val nombre: String,
    val programa: String,
    val semestre: String,
    val biografia: String,
    val edad: Int,
    val municipio: String,
    val correo: String,
    val hobbies: List<String>,
    val pasatiempos: List<String>,
    val deportes: List<String>,
    val intereses: List<String>
)