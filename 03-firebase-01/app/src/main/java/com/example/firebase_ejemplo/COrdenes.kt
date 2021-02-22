package com.example.firebase_ejemplo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.firebase_ejemplo.dto.FirestoreRestauranteDto
import com.example.firebase_ejemplo.dto.FirestoreUsuarioDto
import com.example.firebase_ejemplo.dto.FirestoreUsuarioOrdenDto
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.type.Date
import java.sql.Timestamp

class COrdenes : AppCompatActivity() {

    val arregloRestaurantes = arrayListOf<FirestoreRestauranteDto>()
    var adaptadorRestaurantes: ArrayAdapter<Any>? = null

    var restauranteSeleccionado: FirestoreRestauranteDto? =  null

    val arregloTiposComida = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_c_ordenes)
        if (adaptadorRestaurantes == null){
            adaptadorRestaurantes = ArrayAdapter(
                    this,
                    android.R.layout.simple_spinner_item,
                    arregloRestaurantes as List<FirestoreRestauranteDto>
            )
            adaptadorRestaurantes?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            cargarRestaurantes()
        }

        val botonAnadirTipoComida = findViewById<Button>(R.id.btn_añadir_tipo_comida)
                .setOnClickListener {
                    agregarTipoComida()
                }

        val textViewTipoComida = findViewById<TextView>(R.id.tv_arreglo_tipo_comida)
        textViewTipoComida.setText("")

        val botonAnadirOrden = findViewById<Button>(R.id.btn_crear_orden)
                .setOnClickListener {
                    crearOrden()
                }

        buscarOrdenes()

    }

    fun buscarOrdenes(){
        val db = Firebase.firestore
        val referencia = db.collection("orden")

        //busqueda un campo '=='
        /*referencia
                .whereEqualTo("review", 1)
                .get()
                .addOnSuccessListener {
                    for(orden in it){
                        Log.i("firebase-consultas" ,"${orden.id} ${orden.data}")
                    }
                }
                .addOnFailureListener{
                    Log.i("firebase-consultas" ,"Error")
                }*/

        //busqueda por 2 o más campos '==' 'array Contains'
        /*referencia
                .whereEqualTo("restaurante.nombre", "KFC")
                .whereArrayContains("tiposComida", "Ecuatoriana")
                .get()
                .addOnSuccessListener {
                    for(orden in it){
                        Log.i("firebase-consultas" ,"${orden.id} ${orden.data}")
                    }
                }
                .addOnFailureListener{
                    Log.i("firebase-consultas" ,"Error")
                }*/

        //busqueda por 2 o más campos '==' '>='
        /*referencia
                .whereEqualTo("restaurante.nombre", "KFC")
                .whereGreaterThanOrEqualTo("review", 1)
                .get()
                .addOnSuccessListener {
                    for(orden in it){
                        Log.i("firebase-consultas" ,"${orden.id} ${orden.data}")
                    }
                }
                .addOnFailureListener{
                    Log.i("firebase-consultas" ,"Error")
                }*/

        //busqueda por 2 o más campos '==' '>=' (oreden descendente los nombres)
        /*referencia
                .whereEqualTo("restaurante.nombre", "KFC")
                .whereGreaterThanOrEqualTo("review", 1)
                .orderBy("review", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener {
                    for(orden in it){
                        Log.i("firebase-consultas" ,"${orden.id} ${orden.data}")
                    }
                }
                .addOnFailureListener{
                    Log.i("firebase-consultas" ,"Error")
                }*/

        //busqueda por 2 o más campos '==' 'array contains any'
        /*referencia
                .whereEqualTo("restaurante.nombre", "KFC")
                .whereArrayContainsAny("tiposComida", arrayListOf("Japonesa"))
                .get()
                .addOnSuccessListener {
                    for(orden in it){
                        Log.i("firebase-consultas" ,"${orden.id} ${orden.data}")
                    }
                }
                .addOnFailureListener{
                    Log.i("firebase-consultas" ,"Error")
                }*/

        //val referencia = db.collection("cities").document("BJ").collection("landmarks")
        /*referencia
                .whereEqualTo("type", "park")
                .get()
                .addOnSuccessListener {
                    for(orden in it){
                        Log.i("firebase-consultas" ,"${orden.id} ${orden.data}")
                    }
                }
                .addOnFailureListener{
                    Log.i("firebase-consultas" ,"Error")
                }*/

        /*val referencia = db.collectionGroup("landmarks")
        referencia
                .whereEqualTo("type", "park")
                .get()
                .addOnSuccessListener {
                    for(orden in it){
                        Log.i("firebase-consultas" ,"${orden.id} ${orden.data}")
                    }
                }
                .addOnFailureListener{
                    Log.i("firebase-consultas" ,"Error")
                }*/

        referencia
                .limit(2)
                .get()
                .addOnSuccessListener {
                    for(orden in it){
                        Log.i("firebase-consultas" ,"${orden.id} ${orden.data}")
                    }
                    val longitudArreglo = it.size()
                    val ultimoRegistro : QueryDocumentSnapshot = it.last()


                    // siguientes dos órdenes
                    referencia
                            .limit(2)
                            .startAfter(ultimoRegistro)
                            .get()
                            .addOnSuccessListener {
                                for (orden in it) {
                                    Log.i("firebase-consultas", "${orden.id} ${orden.data}")
                                }
                            }
                            .addOnFailureListener{
                                Log.i("firebase-consultas" ,"Error")
                            }
                }
                .addOnFailureListener{
                    Log.i("firebase-consultas" ,"Error")
                }

    }

    fun cargarRestaurantes(){
        val spinnerRestaurantes = findViewById<Spinner>(R.id.sp_restaurantes)
        spinnerRestaurantes.adapter = adaptadorRestaurantes
        spinnerRestaurantes.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
            ){
                Log.i("firebase-firestore", "${position}, ${id}")
                Log.i("firebase-firestore", "${arregloRestaurantes[position]}")
                restauranteSeleccionado = arregloRestaurantes[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>){
                Log.i("firebase-firestore", "No seleccionó nada")
            }
        }

        val db = Firebase.firestore
        val referencia = db.collection("restaurante")

        referencia.get()
                .addOnSuccessListener {
                    for (document in it){
                        Log.i("firebase-firestore", "${document.id} => ${document.data}")
                        val restaurante = document.toObject(FirestoreRestauranteDto::class.java)
                        restaurante.uid = document.id
                        arregloRestaurantes.add(restaurante)
                        adaptadorRestaurantes?.notifyDataSetChanged()
                    }
                }
                .addOnFailureListener {
                }
    }

    fun agregarTipoComida(){
        val etTipoComida = findViewById<EditText>(R.id.et_tipo_comida)

        val texto = etTipoComida.text.toString()
        arregloTiposComida.add(etTipoComida.text.toString())

        val textViewTipoComida = findViewById<TextView>(R.id.tv_arreglo_tipo_comida)
        val textoAnterior = textViewTipoComida.text.toString()
        textViewTipoComida.setText("${textoAnterior}, ${texto}")
        etTipoComida.setText("")
    }

    fun crearOrden(){

        if(restauranteSeleccionado != null && FirebaseAuth.getInstance().currentUser != null){
            var restaurante = restauranteSeleccionado
            val instaciaAuth = FirebaseAuth.getInstance()
            val usuario = FirestoreUsuarioOrdenDto(instaciaAuth.currentUser!!.uid)
            val editTextReview = findViewById<EditText>(R.id.et_review)

            val nuevaOrden = hashMapOf<String, Any?>(
                    "restaurante" to restauranteSeleccionado,
                    "usuario" to usuario,
                    "review" to editTextReview.text.toString().toInt(),
                    "tiposComida" to arregloTiposComida,
                    "fechaCreacion" to com.google.firebase.Timestamp(java.util.Date())
            )

            val db = Firebase.firestore
            val referencia = db.collection("orden")
                    .document()
                    .set(nuevaOrden)

            referencia
                    .addOnSuccessListener{ }
                    .addOnFailureListener{ }
        }
    }

    fun crearDatosBusquedaPorGrupo(){

        val db = Firebase.firestore
        val citiesRef = db.collection("cities")

        val ggbData = mapOf(
                "name" to "Golden Gate Bridge",
                "type" to "bridge"
        )
        citiesRef.document("SF").collection("landmarks").add(ggbData)

        val lohData = mapOf(
                "name" to "Legion of Honor",
                "type" to "museum"
        )
        citiesRef.document("SF").collection("landmarks").add(lohData)

        val gpData = mapOf(
                "name" to "Griffth Park",
                "type" to "park"
        )
        citiesRef.document("LA").collection("landmarks").add(gpData)

        val tgData = mapOf(
                "name" to "The Getty",
                "type" to "museum"
        )
        citiesRef.document("LA").collection("landmarks").add(tgData)

        val lmData = mapOf(
                "name" to "Lincoln Memorial",
                "type" to "memorial"
        )
        citiesRef.document("DC").collection("landmarks").add(lmData)

        val nasaData = mapOf(
                "name" to "National Air and Space Museum",
                "type" to "museum"
        )
        citiesRef.document("DC").collection("landmarks").add(nasaData)

        val upData = mapOf(
                "name" to "Ueno Park",
                "type" to "park"
        )
        citiesRef.document("TOK").collection("landmarks").add(upData)

        val nmData = mapOf(
                "name" to "National Musuem of Nature and Science",
                "type" to "museum"
        )
        citiesRef.document("TOK").collection("landmarks").add(nmData)

        val jpData = mapOf(
                "name" to "Jingshan Park",
                "type" to "park"
        )
        citiesRef.document("BJ").collection("landmarks").add(jpData)

        val baoData = mapOf(
                "name" to "Beijing Ancient Observatory",
                "type" to "musuem"
        )
        citiesRef.document("BJ").collection("landmarks").add(baoData)
    }
}