package com.example.firebase_ejemplo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import com.example.firebase_ejemplo.dto.FirestoreOrdenes
import com.example.firebase_ejemplo.dto.FirestoreRestauranteDto
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ListadoOrdenes : AppCompatActivity() {

    //var arregloOrdenes:ArrayList<String> = arrayListOf()

    val db = Firebase.firestore
    val referencia = db.collection("orden")
    var ultimoRegistro: QueryDocumentSnapshot? = null
    var arregloOrdenes:ArrayList<FirestoreOrdenes> = arrayListOf()
    val adaptador : ArrayAdapter<Any>? = null
    var fechaCreacion: com.google.firebase.Timestamp? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listado_ordenes)

        llenarListView()

        val botonAumentar = findViewById<Button>(R.id.btn_aumentar_ordenes)
            .setOnClickListener {
                aumentarOrden()
                //llenarListView()
            }

    }

    fun llenarListView(){
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arregloOrdenes
        )

        referencia
                .orderBy("fechaCreacion")
                .limit(1)
            .get()
            .addOnSuccessListener {
                for (orden in it){
                    val ordenes = orden.toObject(FirestoreOrdenes::class.java)
                    ordenes.uid = orden.id
                    arregloOrdenes.add(ordenes)

                    Log.i("firebase-consultas" ,"${orden.data}")
                    fechaCreacion = ordenes.fechaCreacion
                }
                ultimoRegistro = it.last()
                adaptador.notifyDataSetChanged()
            }
            .addOnFailureListener{
                Log.i("firebase-consultas" ,"Error")
            }

        val listViewOredenes= findViewById<ListView>(R.id.lv_lista_ordenes)
        listViewOredenes.adapter = adaptador
        adaptador.notifyDataSetChanged()
        registerForContextMenu(listViewOredenes)

        //arregloOrdenes = arrayListOf()

    }


    fun aumentarOrden(){

        val adaptador = ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                arregloOrdenes
        )

        referencia
            .orderBy("fechaCreacion")
            .limit(1)
            .startAfter(fechaCreacion)
            .get()
            .addOnSuccessListener {
                for (orden in it) {
                    //Log.i("firebase-consultas", "${orden.id} ${orden.data}")
                    val ordenes = orden.toObject(FirestoreOrdenes::class.java)
                    ordenes.uid = orden.id
                    arregloOrdenes.add(ordenes)
                    //adaptador?.notifyDataSetChanged()
                    Log.i("firebase-consultas" ,"boton ${orden.data}")
                    fechaCreacion = ordenes.fechaCreacion
                }
            }
            .addOnFailureListener{
                Log.i("firebase-consultas" ,"Error")
            }

        val listViewOredenes= findViewById<ListView>(R.id.lv_lista_ordenes)
        listViewOredenes.adapter = adaptador
        adaptador.notifyDataSetChanged()
        registerForContextMenu(listViewOredenes)
    }
}