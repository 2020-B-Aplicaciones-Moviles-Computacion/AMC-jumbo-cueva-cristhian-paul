package com.example.firebase_ejemplo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CProducto : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_c_producto)

        val botonCrear = findViewById<Button>(R.id.btn_crear_producto)
            .setOnClickListener {
                crearProducto()
            }
    }

    fun crearProducto(){

        val editTextNombreProducto = findViewById<EditText>(R.id.et_nombre_producto)
        val editTextPrecioProducto = findViewById<TextView>(R.id.et_precio_producto)

        val nuevoProducto = hashMapOf<String, Any>(
            "nombre" to editTextNombreProducto.text.toString(),
            "precio" to editTextPrecioProducto.text.toString().toDouble()
        )

        Log.i("firebase-firestore", "${nuevoProducto}")

        val db = Firebase.firestore

        val referencia = db.collection("restaurante")
            .document()
            .set(nuevoProducto)
        referencia
            .addOnSuccessListener{  }
            .addOnFailureListener{ }

    }
}