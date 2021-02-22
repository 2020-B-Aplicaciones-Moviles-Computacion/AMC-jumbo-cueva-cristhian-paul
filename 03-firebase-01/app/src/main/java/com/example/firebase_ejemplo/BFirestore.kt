package com.example.firebase_ejemplo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.Button
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class BFirestore : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b_firestore)

        val db = Firebase.firestore


        db.collection("usuario")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.i("firebase-firestore", "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.i("firebase-firestore", "Error getting documents.", exception)
            }

        val botonProducto = findViewById<Button>(R.id.btn_ir_producto)
            .setOnClickListener { irActividad(CProducto::class.java) }

        val botonRestaurante = findViewById<Button>(R.id.btn_ir_restaurante)
            .setOnClickListener { irActividad(CRestaurante::class.java) }

        val botonOrdenes = findViewById<Button>(R.id.btn_ir_ordenes)
            .setOnClickListener { irActividad(COrdenes::class.java) }

        val botonListadoOrdenes = findViewById<Button>(R.id.btn_ir_listado_ordenes)
            .setOnClickListener { irActividad(ListadoOrdenes::class.java) }

        val botonImagenes = findViewById<Button>(R.id.btn_imagenes)
            .setOnClickListener { irActividad(Intento_Imagenes::class.java) }
    }

    fun irActividad(
        clase: Class<*>,
        parametros: ArrayList<Pair<String, *>>? = null,
        codigo: Int? = null
    ) {
        val intentExplicito = Intent(
            this,
            clase
        )
        if (parametros != null) {
            parametros.forEach {
                val nombreVariable = it.first
                val valorVariable = it.second

                var tipoDato = false

                tipoDato = it.second is String // instanceOf()
                if (tipoDato == true) {
                    intentExplicito.putExtra(nombreVariable, valorVariable as String)
                }

                tipoDato = it.second is Int // instanceOf()
                if (tipoDato == true) {
                    intentExplicito.putExtra(nombreVariable, valorVariable as Int)
                }

                tipoDato = it.second is Parcelable // instanceOf()
                if (tipoDato == true) {
                    intentExplicito.putExtra(nombreVariable, valorVariable as Parcelable)
                }

            }
        }
        if (codigo != null) {
            startActivityForResult(intentExplicito, codigo)
        } else {
            startActivity(intentExplicito)
        }


    }


}