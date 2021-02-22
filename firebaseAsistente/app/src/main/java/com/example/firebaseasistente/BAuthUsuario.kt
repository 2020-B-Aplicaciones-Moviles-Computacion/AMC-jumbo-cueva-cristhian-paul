package com.example.firebaseasistente

import com.google.firebase.auth.FirebaseUser

class BAuthUsuario {
    companion object {
        var usuario: BUsuarioFirebase?

        init {
            this.usuario = null;
        }
    }
}