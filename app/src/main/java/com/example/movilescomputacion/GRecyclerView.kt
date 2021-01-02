package com.example.movilescomputacion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GRecyclerView : AppCompatActivity() {

    var totalLikes = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_g_recycler_view)

        val listaEntrenador = arrayListOf<BEntrenador>()
        val ligaPokemon = DLiga("Kanto", "Liga Kanto")
        listaEntrenador
            .add(
                BEntrenador(
                    "Stalin",
                    "11041257798",
                    ligaPokemon
                )
            )
        listaEntrenador
            .add(
                BEntrenador(
                    "Carlos",
                    "1104125883",
                    ligaPokemon
                )
            )

        val recyclerViewEntrenador = findViewById<RecyclerView>(
            R.id.rv_entrenadores
        )
        this.iniciarRecyclerView(
            listaEntrenador,
            this,
            recyclerViewEntrenador
        )
    }

    fun iniciarRecyclerView(
        lista: List<BEntrenador>,
        actividad: GRecyclerView,
        recyclerView: RecyclerView
    ) {
        val adaptador = FRecyclerViewAdaptadorNombreCedula(
            lista,
            actividad,
            recyclerView
        )

//        recyclerView.adapter = adaptador
//        recyclerView.itemAnimator = DefaultItemAnimator()
//        recyclerView.layoutManager = LinearLayoutManager(actividad)
//        adaptador.notifyDataSetChanged()
    }

    fun aumentarTotalLikes() {
        totalLikes = totalLikes +1
        val textView = findViewById<TextView>(
            R.id.tv_tota_likes
        )
        textView.text = totalLikes.toString()
    }
}