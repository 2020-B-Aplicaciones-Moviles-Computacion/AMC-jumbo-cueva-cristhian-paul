package com.example.firebase

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_crear_editar_negocio.*

const val EXTRA_ID = "com.example.firebase.EXTRA_ID"
const val EXTRA_NOMBRE = "com.example.firebase.EXTRA_NOMBRE"
const val EXTRA_DIRECCION = "com.example.firebase.EXTRA_DIRECCION"
const val EXTRA_TELEFONO = "com.example.firebase.EXTRA_TELEFONO"
const val EXTRA_LATITUD = "com.example.firebase.EXTRA_LATITUD"
const val EXTRA_LONGITUD = "com.example.firebase.EXTRA_LONGITUD"

class CrearEditarNegocio : AppCompatActivity() {

    private lateinit var mode : Mode

    private var idMateria: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_editar_negocio)

        idMateria = intent.getIntExtra("IdNegocio", -1).toInt()

        Log.i("data", "$idMateria")
        mode = if(idMateria == -1) Mode.AddMateria
        else Mode.EditMateria

        when(mode) {
            Mode.AddMateria -> text_titulo.setText("Form Agregar Negocio")
            Mode.EditMateria -> {

                imageView.setImageDrawable(
                    ContextCompat.getDrawable(this,
                    R.drawable.ic_cart))
                text_titulo.setText("Form Editar Negocio")
                plain_txt_name.setText(intent.getStringExtra(EXTRA_NOMBRE))
                plain_txt_direccion.setText(intent.getStringExtra(EXTRA_DIRECCION))
                plain_txt_telefono.setText(intent.getStringExtra(EXTRA_TELEFONO))
                plain_txt_latidud.setText(intent.getStringExtra(EXTRA_LATITUD))
                plain_txt_longitud.setText(intent.getStringExtra(EXTRA_LONGITUD))
            }
        }

        btn_guardar.setOnClickListener {
            val nombre = plain_txt_name.text.toString()
            val direccion = plain_txt_direccion.text.toString()
            val telefono = plain_txt_telefono.text.toString()
            val latitud = plain_txt_latidud.text.toString()
            val longitud = plain_txt_longitud.text.toString()
            val data = Intent(
                this,
                Negocio::class.java
            )
            // only if note ID was provided i.e. we are editing
            if(idMateria != -1)
                data.putExtra("IdNegocio", idMateria)
            data.putExtra(EXTRA_NOMBRE, nombre)
            data.putExtra(EXTRA_DIRECCION, direccion)
            data.putExtra(EXTRA_TELEFONO, telefono)
            data.putExtra(EXTRA_LATITUD, latitud)
            data.putExtra(EXTRA_LONGITUD, longitud)

            setResult(Activity.RESULT_OK, data)
            finish()
        }

        btn_cancelar.setOnClickListener {
            finish()
        }
    }
    private sealed class Mode {
        object AddMateria : Mode()
        object EditMateria : Mode()

    }
}