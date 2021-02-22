package com.example.firebaseasistente

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CProducto : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_c_producto)

        val botonCrear = findViewById<Button>(R.id.btn_crear_producto)
        botonCrear.setOnClickListener {
            crearProdcuto()
        }

    }
    fun crearProdcuto() {
        val  editTextNombre = findViewById<EditText>(R.id.et_nombre_prodcuto)
        val  editTextPrecio = findViewById<EditText>(R.id.et_precio_prodcuto)

        val nuevoProducto = hashMapOf<String, Any>(
            "nombre" to editTextNombre.text,
            "precio" to editTextPrecio.text.toString().toDouble()
        )
        Log.i("firestore", "$nuevoProducto")

        val db = Firebase.firestore

        val referencia = db.collection("producto")
            .document()
            .set(nuevoProducto)
        referencia
                .addOnSuccessListener {
                    Log.i("firestore", "Producto creado correctamente")
                }
                .addOnFailureListener {
                    exception -> Log.w("firestore", "Error", exception)
                }
    }
}