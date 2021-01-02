package com.example.movilescomputacion

class IPostHttp (
    val id: Int,
    val userId: Any,
    val title: String,
    val body: String
) {

    var userIdTranformado: Int = 0;
    init {
        if(userId is String){
            userIdTranformado = (userId as String).toInt()
        }
        if(userId is Int){
            userIdTranformado = userId.toInt()
        }

    }

}