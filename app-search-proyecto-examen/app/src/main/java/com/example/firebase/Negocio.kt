package com.example.firebase

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebase.fragments.CardFragment
import com.example.firebase.models.NegocioData
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
class Negocio : AppCompatActivity() {



    lateinit var mRecyclerView: RecyclerView
    lateinit var mDatabaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_negocio)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setTitle("Gestión Negocios")

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("negocios")
        mRecyclerView = findViewById(R.id.viewNegocios)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.setLayoutManager(LinearLayoutManager(this))


        mDatabaseReference.get().addOnSuccessListener {
            Log.i("firebase", "Got value ${it.value}")
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }

        logRecyclerView()

    }


    private fun logRecyclerView() {
        var query = FirebaseDatabase.getInstance()
            .reference
            .child("").child("negocios")
            .limitToLast(50)

        val options = FirebaseRecyclerOptions.Builder<NegocioData>()
            .setQuery(query, NegocioData::class.java)
            .setLifecycleOwner(this)
            .build()

        val FirebaseRecycleViewAdapter = object : FirebaseRecyclerAdapter<NegocioData, NegocioViewHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NegocioViewHolder {
                return NegocioViewHolder(
                    LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_layout, parent, false))
            }


            protected override fun onBindViewHolder(holder: NegocioViewHolder, position: Int, model: NegocioData) {
                holder.customView.findViewById<TextView>(R.id.nombre_neg).setText(model.nombre)
                holder.customView.findViewById<TextView>(R.id.tv_direccion).setText(model.direccion)
                holder.customView.findViewById<TextView>(R.id.tv_distancia).setText("60 mts")
                holder.customView.findViewById<TextView>(R.id.tv_telefono).setText(model.telefono)
               // holder.bind(model)
            }

            override fun onDataChanged() {
                // Called each time there is a new data snapshot. You may want to use this method
                // to hide a loading spinner or check for the "no documents" state and update your UI.
                // ...
            }
        }

        mRecyclerView.adapter = FirebaseRecycleViewAdapter
    }
    

    /// Vista holder

}


class NegocioViewHolder(val customView: View, var negocio: NegocioData? = null) : RecyclerView.ViewHolder(customView) {

        fun bind(negocio: NegocioData) {
            with(negocio) {
                customView
            }
        }
    }