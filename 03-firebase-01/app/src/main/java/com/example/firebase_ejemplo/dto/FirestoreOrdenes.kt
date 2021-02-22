package com.example.firebase_ejemplo.dto

import java.sql.Timestamp

data class FirestoreOrdenes(var uid:String = "",
                            var fechaCreacion: com.google.firebase.Timestamp? = null,
                            var restaurante:FirestoreRestauranteDto? = null,
                            var review: Int = 0,
                            var tiposComida:ArrayList<String> = arrayListOf(),
                            var usuario:FirestoreUsuarioDto? = null
){

    override fun toString(): String {
        return "$uid ${restaurante}"
    }
}
