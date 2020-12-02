package com.example.movilescomputacion

class BaseDatosMemoria{
    companion object {
        val arregloEnteros = arrayListOf<Int>(1,2,3,4,5,6)

        fun cargaInicialDatos(){
            arregloEnteros.add(7)
            arregloEnteros.add(8)
        }

    }
}