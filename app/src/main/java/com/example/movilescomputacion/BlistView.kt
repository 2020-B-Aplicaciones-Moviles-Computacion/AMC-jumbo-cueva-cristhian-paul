package com.example.movilescomputacion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView

class BlistView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blist_view)

        val listNumeros = arrayListOf(1,2,3,4)

        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, // xml visual q ya existe
            listNumeros // arreglo que se necesita mandar

        )

        val listView = findViewById<ListView>(R.id.lv_entrenador)
        listView.adapter = adaptador

        listNumeros.add(5)
        listNumeros.add(6)

        adaptador.notifyDataSetChanged()

        val botonAnadirLV = findViewById<Button>(R.id.btn_anadir_item_lv)

        botonAnadirLV
            .setOnClickListener {
                anadirLitView(adaptador, 1, listNumeros)
            }


    }
    fun anadirLitView(
        adaptador: ArrayAdapter<Int>,
        item: Int,
        arreglo: ArrayList<Int>
    ) {
        arreglo.add(item)
        adaptador.notifyDataSetChanged()

    }
}