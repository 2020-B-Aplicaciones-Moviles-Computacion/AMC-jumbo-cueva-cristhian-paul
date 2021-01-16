package com.example.api_universidad

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class Estudiante(
    var id: Int,
    var nombre: String,
    var fechaNacimiento: String,
    var sexo: String,
    var estatura: Double,
    var tieneBeca: Int,
    var latitud: Double,
    var longitud: Double,
    var urlImagen: String,
    var urlRedSocial: String,
    var materia: Materia
) {
    override fun toString(): String {
        return "${nombre} - ${calcularEdad(Date(fechaNacimiento))}"
    }

    fun calcularEdad(fechaNac: Date): String {
        val fechaActual: Date = Date()
        val fechaFormateada: DateFormat = SimpleDateFormat("yyyyMMdd")
        val fechaUno: Int = fechaFormateada.format(fechaActual).toInt()
        val fechaDos: Int = fechaFormateada.format(fechaNac).toInt()
        val edad = (fechaUno - fechaDos) / 10000
        return "Edad: ${edad}"
    }
}