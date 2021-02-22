package com.example.firebase_ejemplo.dto

data class FirestoreRestauranteDto(
        var uid:String = "",
        var nombre: String = ""
){
    override fun toString(): String {
        return nombre
    }
}
