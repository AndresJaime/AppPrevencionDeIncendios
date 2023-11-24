package com.example.appprevenciondeincendios

data class Habitacion(
    val nombre: String? = null,
    val descripcion: String? = null,
    val temperatura: Double? = null // // Nuevo campo para los datos del Arduino
)
