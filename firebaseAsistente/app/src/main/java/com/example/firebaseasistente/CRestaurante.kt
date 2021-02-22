package com.example.firebaseasistente

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CRestaurante : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_c_restaurante)

        val botonCrear = findViewById<Button>(R.id.btn_crear_restaurante)

        botonCrear.setOnClickListener {
            crearRestaurante()
        }

    }
    fun crearRestaurante() {
        val editTextNombre = findViewById<EditText>(R.id.et_nombre_restaurante)

        val  editTextDireccion = findViewById<EditText>(R.id.et_direccion_resturante)

        val nuevoRestaurante = hashMapOf<String, Any>(
                "nombre" to editTextNombre.text.toString(),
                "direccion" to editTextDireccion.text.toString().toDouble()
        )
        Log.i("firestore", "$nuevoRestaurante")

        val db = Firebase.firestore

        val referencia = db.collection("restaurante")
                .document()
                .set(nuevoRestaurante)
        referencia
                .addOnSuccessListener {
                    Log.i("firestore", "Restaurante creado correctamente")
                }
                .addOnFailureListener {
                    exception -> Log.w("firestore", "Error", exception)
                }

    }}