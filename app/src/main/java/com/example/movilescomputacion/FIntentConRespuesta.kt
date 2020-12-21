package com.example.movilescomputacion

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.Button

class FIntentConRespuesta : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_f_intent_con_respuesta)

        val botonSeleccionContacto = findViewById<Button>(
            R.id.btn_seleccionar_contacto
        )

        botonSeleccionContacto.setOnClickListener {
            val intentConRespuesta = Intent(
                Intent.ACTION_PICK,
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI
            )
            startActivityForResult(intentConRespuesta, 304)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            304 -> {
                if(requestCode == RESULT_OK) {

                    Log.i("resultado", "Usuario Selecciono un contacto")
                    val uri = data?.data
                    if(uri != null){
                        val cursor = contentResolver.query(
                            uri,
                            null,
                            null,
                            null,
                            null,
                            null
                        )
                        cursor?.moveToFirst()
                        val indiceTelefono = cursor?.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.NUMBER
                        )
                        val telefono = cursor?.getString(
                            indiceTelefono!!
                        )
                        cursor?.close()
                        Log.i("resultado", "Telefono: ${telefono}")
                    }

                } else {
                    Log.i("resultado", "Usuario no seleccion un contacto")
                }
            }
        }
    }
}
