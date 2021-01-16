package com.example.api_universidad

class Materia(
    var id: Int,
    var nombre: String,
    var creditos: Int? = null,
    var codigo: String? = null,
    var estado: Int? = 1
) {
    override fun toString(): String {
        return "${this.nombre}"
    }
}