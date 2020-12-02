package com.example.movilescomputacion

class BEntrenador (
    var nombre: String,
    var descripcion: String
) {
    override fun toString(): String {
        return "${this.nombre} ${this.descripcion}"
    }
}