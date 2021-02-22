
package com.example.firebaseasistente

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.firebaseasistente.dto.FirestoreRestauranteDto
import com.example.firebaseasistente.dto.FirestoreRestauranteOrdenDto
import com.example.firebaseasistente.dto.FirestoreUsuarioOrdenDto
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Date
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.ktx.Firebase
import java.util.*

class COrdenes : AppCompatActivity() {

    val arregloRestaurantes = arrayListOf<FirestoreRestauranteDto>()
    var adaptadorRestaurantes: ArrayAdapter<FirestoreRestauranteDto>? = null

//    val arreglo = arrayListOf<BUsuarioFirebase>(
//            BUsuarioFirebase("a", "a@a.com", null),
//            BUsuarioFirebase("b", "b@b.com", null),
//            BUsuarioFirebase("c", "c@c.com", null)
//    )


    val arregloTiposComida = arrayListOf<String>()

    var restauranteSeleccionado: FirestoreRestauranteDto? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_c_ordenes)
        if(adaptadorRestaurantes == null) {
            adaptadorRestaurantes = ArrayAdapter(
                    this, android.R.layout.simple_spinner_item,
                    arregloRestaurantes
            )
            adaptadorRestaurantes?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            cargarRestaurantes()
        }
        val botonAnadirTipoComida = findViewById<Button>(R.id.btn_anadir_tipo_comida)
        botonAnadirTipoComida
                .setOnClickListener {
                    agregarTipoCOmida()
                }
        val textViewTipoComida = findViewById<TextView>(R.id.tv_tipo_comida)
        textViewTipoComida.setText("")

        val botonAnadirOrden = findViewById<Button>(R.id.btn_crear_orden)
        botonAnadirOrden
                .setOnClickListener {
                    crearOrden()
                }
        //buscarOrdenes()
        eliminacion()
    }

    fun buscarOrdenes() {
        val db = Firebase.firestore
        val referencia = db.collection("orden")
//        referencia
//                .whereEqualTo("review", 3)
//                .get()
//                .addOnSuccessListener {
//                    for( orden in it) {
//                        Log.i("firebase", "${orden.id}, ${orden.data}")
//                    }
//                }
//                .addOnFailureListener {
//                    Log.i("firebase", "Error")
//                }

        // busquedas compuestas operador iguaol igual

        referencia
                .whereEqualTo("restaurante.nombre", "fridays")
                .whereArrayContains("tiposComida", "ecuatoriana")
                .get()
                .addOnSuccessListener {
                    for (orden in it ) {
                       Log.i("firebase", "${orden.id}, ${orden.data}")
                    }
                }
                .addOnFailureListener {
                    Log.i("firebase", "Error")
                }
    }

    fun cargarRestaurantes () {
        val spinnerRestaurante = findViewById<Spinner>(R.id.sp_restaurantes)
        spinnerRestaurante.setAdapter(adaptadorRestaurantes)
        spinnerRestaurante.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
            ) {
                restauranteSeleccionado = arregloRestaurantes[position]
                Log.i("firebase", "${position}, ${id}")
                Log.i("firebase", "${arregloRestaurantes[position]}")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.i("firebase", "no selecciono nada")
            }

        }
        val db = Firebase.firestore

        val referencia = db.collection("restaurante")
        referencia.get()
                .addOnSuccessListener {
                    Log.i("firebase", "${it}")
                    for (document in it) {
                        Log.i("firebase","${document.id} => ${document.data}")
                        val restaurante = document.toObject(FirestoreRestauranteDto::class.java)
                        restaurante.uid = document.id
                        arregloRestaurantes.add(restaurante)
                        adaptadorRestaurantes?.notifyDataSetChanged()
                    }
                }
                .addOnFailureListener{

                }
    }
    @SuppressLint("WrongViewCast")
    fun agregarTipoCOmida() {
        val etTipoComida = findViewById<EditText>(R.id.et_tipo_comida)
        val texto = etTipoComida.text.toString()
        arregloTiposComida.add(texto)
        val textViewTipoComida = findViewById<TextView>(R.id.tv_tipo_comida)
        val textAnterior = textViewTipoComida.text.toString()
        textViewTipoComida.setText("${textAnterior}, ${texto}")
        etTipoComida.setText("")
    }
    @SuppressLint("WrongViewCast")
    fun crearOrden () {
        if(restauranteSeleccionado != null){
            var restaurante = FirestoreRestauranteOrdenDto()
            val instanciaAuth = FirebaseAuth.getInstance()
            val usuario = FirestoreUsuarioOrdenDto(instanciaAuth.currentUser!!.uid)
            val editTextReview = findViewById<EditText>(R.id.et_review)
            val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val nuevaOrden = hashMapOf<String, Any>(
                    "restaurante" to  restaurante,
                    "usuario" to usuario,
                    "review" to editTextReview.toString().toInt(),
                    "tipoComida" to arregloTiposComida,
                    "fechaCreacion" to timeStamp

            )
            val db = Firebase.firestore
            val referencia = db.collection("orden")
                    .document()
                    .set(nuevaOrden)
            referencia
                    .addOnSuccessListener {
                        Log.i("firestore", "orden creada correctamente")
                    }
                    .addOnFailureListener {
                        exception -> Log.w("firestore", "Error", exception)
                    }
        }

    }
    fun eliminacion() {
        val db = Firebase.firestore;
        val docRef = db
                .collection("cities")
                .document("BJ")
                .collection("landmarks")
                .document("cristhian.jumbo@epn.edu.ec\n")

        val eliminarCampo = hashMapOf<String, Any>(
                "name" to FieldValue.delete()
        )

        //campo delete
        docRef
                .update(eliminarCampo)
                .addOnSuccessListener {
                    Log.i("firestore", "Eliminando correctamente")
                }
                .addOnFailureListener {
                    exception -> Log.w("firestore", "Error", exception)
                }
        //documento delete
        docRef
                .update(eliminarCampo)
                .addOnSuccessListener {
                    Log.i("firestore", "Eliminando correctamente")
                }
                .addOnFailureListener {
                    exception -> Log.w("firestore", "Error", exception)
                }

    }

    fun eliminarDocumentosMedianteConsulta() {

        //(Buscar quiero las ordenes review >= 3) y eliminar todos esos documentos
        var uidEliminar = ""
        val db = Firebase.firestore
        val referencia = db.collectionGroup("orden")
        referencia
                .whereGreaterThanOrEqualTo("review",5 )
                .get()
                .addOnSuccessListener {

                    for (order in it){
                        Log.i("firebase","XXXXXX ${order.data}  ID${order.id}")
                        uidEliminar = order.id
                        db.collection("orden").document(uidEliminar).delete()
                                .addOnSuccessListener {
                                    Log.i("firebase","SE ELIMINO")
                                }.addOnFailureListener{

                                    Log.i("firebase","NO SE ELIMINO")
                                }
                    }
                }.addOnFailureListener{
                    Log.i("firebase","Failure : ${it}")
                }

    }
}