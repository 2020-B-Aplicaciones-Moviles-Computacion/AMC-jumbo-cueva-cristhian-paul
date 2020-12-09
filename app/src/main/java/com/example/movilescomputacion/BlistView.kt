package com.example.movilescomputacion

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView

class BlistView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blist_view)


        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, // xml visual q ya existe
            BaseDatosMemoria.arregloEntrenadores // arreglo que se necesita mandar

        )

        val listView = findViewById<ListView>(R.id.lv_entrenador)
        listView.adapter = adaptador

        listView
                .setOnItemLongClickListener { parent, view, position, id ->
                    Log.i("intent-explicito", "Hola ${position} ${id}")
                    val builder = AlertDialog
                            .Builder(this)
                    builder.setMessage("hola")
                    val seleccionUsuario = booleanArrayOf(
                            true,
                            false,
                            false
                    )
                    val opcioes = resources.getStringArray(R.array.string_arrays)
                    builder.setMultiChoiceItems(
                            opcioes,
                            seleccionUsuario,
                            null
                    )
                    builder.setMessage("hola")
                            .setPositiveButton(
                                    "Si",
                                    {dialog, which ->
                                        Log.i("intent-explicito", "Si")
                                    }
                            )
                            .setNegativeButton(
                                    "Si",
                                    null
                            )
                    val dialogo = builder.create()
                    dialogo.show()
                    return@setOnItemLongClickListener true
                }

        adaptador.notifyDataSetChanged()

        val botonAnadirLV = findViewById<Button>(R.id.btn_anadir_item_lv)

        botonAnadirLV
            .setOnClickListener {
                anadirLitView(adaptador, 1, BaseDatosMemoria.arregloEntrenadores)
            }


    }
    fun anadirLitView(
        adaptador: ArrayAdapter<BEntrenador>,
        item: Int,
        arreglo: ArrayList<BEntrenador>
    ) {
        arreglo.add(BEntrenador("Carlos", "As frente batalla"))
        adaptador.notifyDataSetChanged()

    }
}