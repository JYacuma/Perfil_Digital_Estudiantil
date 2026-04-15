package com.example.aplicacionperfiles.viewmodel

import androidx.lifecycle.ViewModel
import com.example.aplicacionperfiles.model.UserProfile

class ProfileViewModel : ViewModel() {
    val profile = UserProfile(
        nombre = "Jonathan Alejandro Yacuma Rivera",
        programa = "Ingeniería de Sistemas y Computación",
        semestre = "Quinto",
        biografia = "Estudiante de Ingeniería de Sistemas con el gusto de crear soluciones en código. Me enfoco en el backend con Java y bases de datos y diseño el frontend.",
        edad = 20,
        municipio = "Chía",
        correo = "jyacuma@ucundinamarca.edu.co",
        hobbies = listOf("Programación", "Videojuegos", "Lectura técnica"),
        pasatiempos = listOf("Escuchar música", "Caminar"),
        deportes = listOf("Fútbol", "Ciclismo"),
        intereses = listOf("Inteligencia Artificial", "Desarrollo Backend", "Cloud Computing")
    )
}