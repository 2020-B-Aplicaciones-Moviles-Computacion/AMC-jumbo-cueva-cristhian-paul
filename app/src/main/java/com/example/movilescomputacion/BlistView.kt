
package com.example.movilescomputacion

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView

class BlistView : AppCompatActivity() {

    var posicionItemSeleccionado = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blist_view)


        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, // xml visual q ya existe
            BaseDatosMemoria.arregloEnteros // arreglo que se necesita mandar

        )

        val listView = findViewById<ListView>(R.id.lv_entrenador)
        listView.adapter = adaptador

//        listView
//                .setOnItemLongClickListener { parent, view, position, id ->
//                    Log.i("intent-explicito", "Hola ${position} ${id}")
//                    val builder = AlertDialog
//                            .Builder(this)
//                    builder.setTitle("titulo")
//
//                    val seleccionUsuario = booleanArrayOf(
//                            true,
//                            false,
//                            false
//                    )
//                    val opcioes = resources.getStringArray(R.array.string_arrays)
//                    builder.setMultiChoiceItems(
//                            opcioes,
//                            seleccionUsuario,
//                        {
//                            dialog, which, isChecked ->  Log.i("intent-explicito", "${which} ${isChecked}")
//                        }
//                    )
//                    val dialogo = builder.create()
//                    dialogo.show()
//                    return@setOnItemLongClickListener true
//                }

        adaptador.notifyDataSetChanged()

        registerForContextMenu(listView)

        val botonAnadirLV = findViewById<Button>(R.id.btn_anadir_item_lv)

//        botonAnadirLV
//            .setOnClickListener {
//                anadirLitView(adaptador, 1)
//            }


    }
    fun anadirLitView(
        adaptador: ArrayAdapter<Int>,
        item: Int,
        arreglo: ArrayList<BEntrenador>?
    ) {
      //  arreglo.add(BEntrenador("Carlos", "As frente batalla"))
        adaptador.notifyDataSetChanged()

    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)

        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        posicionItemSeleccionado = id
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item?.itemId) {
            // Editar
            R.id.mi_editar -> {
                Log.i("intent-explicito", "Editar" + "${BaseDatosMemoria.arregloEnteros[posicionItemSeleccionado]}")
                return true
            }
            // Eliminar
            R.id.mi_eliminar -> {
                Log.i("intent-explicito", "Eliminar" + "${BaseDatosMemoria.arregloEnteros[posicionItemSeleccionado]}")
                return true
            }

            else -> super.onContextItemSelected(item)

        }
    }
}