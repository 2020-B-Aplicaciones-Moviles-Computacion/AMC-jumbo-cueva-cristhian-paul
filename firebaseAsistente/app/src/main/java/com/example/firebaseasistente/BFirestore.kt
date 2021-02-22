package com.example.firebaseasistente

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
                .addOnSuccessListener {
                    result ->
                    for (document in result) {
                        Log.i("firebase", "${document.id} => ${document.data}")
                    }
                }
                .addOnFailureListener{
                    exception -> Log.w("firebase", "Error", exception)
                }

        val botonProducto = findViewById<Button>(R.id.btn_producto)

        botonProducto.setOnClickListener {
            irActividad(
                CProducto::class.java
            )
        }

        val botonRestaurante = findViewById<Button>(R.id.btn_restaurante)
        botonRestaurante.setOnClickListener {
            irActividad(
                CRestaurante::class.java
            )
        }


        val botonOrdenes = findViewById<Button>(R.id.btn_ordenes)
        botonOrdenes.setOnClickListener {
            irActividad(
                COrdenes::class.java
            )
        }



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
        if (parametros != null){
            parametros.forEach{
                var nombreVariables = it.first
                var valorVariable =  it.second
                var tipoDatos = false

                tipoDatos = it.second is String // instnceOf()
                if(tipoDatos == true){

                    intentExplicito.putExtra(nombreVariables, valorVariable as String)
                }
                tipoDatos = it.second is Int // instnceOf()
                if(tipoDatos == true){

                    intentExplicito.putExtra(nombreVariables, valorVariable as Int)
                }
                tipoDatos = it.second is Parcelable // instnceOf()
                if(tipoDatos == true){

                    intentExplicito.putExtra(nombreVariables, valorVariable as Parcelable)
                }
            }
        }
        if (codigo != null) {
            startActivityForResult(intentExplicito,codigo)
        } else {
            startActivity(intentExplicito)
        }
    }
}