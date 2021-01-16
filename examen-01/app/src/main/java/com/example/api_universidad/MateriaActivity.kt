package com.example.api_universidad

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.beust.klaxon.Klaxon
import com.example.api_universidad.data.roomdatabase.Entity.MateriaEntity
import com.example.api_universidad.data.roomdatabase.Model.MateriaViewModel
import com.github.kittinunf.result.Result
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_materia.*
import kotlinx.android.synthetic.main.dialos_materia.view.*


const val ADD_MATERIA_REQUEST = 1
const val EDIT_MATERIA_REQUEST = 2
const val ESTUDIANTE_REQUEST = 3

class MateriaActivity : AppCompatActivity() {

    private lateinit var materiaModel: MateriaViewModel

    private lateinit var adaptador: ArrayAdapter<MateriaEntity>

    private lateinit var listaMaterias: ArrayList<MateriaEntity>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_materia)

        materiaModel = ViewModelProviders.of(this)[MateriaViewModel::class.java]

        materiaModel.getAllMaterias().observe(this, Observer {
            Log.i("Materias observed", "$it")
            setearListaInicial(it.toCollection(ArrayList()))

           // adaptador.submitList(it)
        })

        cargarOpcionesListView()
        btn_nuevo.setOnClickListener {
            irACrearEditarMateria()
        }


    }

    fun setearListaInicial(lista: ArrayList<MateriaEntity>) {
        listaMaterias = lista
        adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            listaMaterias
        )
        list_view_materias.adapter = adaptador
    }

    fun cargarOpcionesListView() {
        val dialog = BottomSheetDialog(this)
        val mostrar = layoutInflater.inflate(R.layout.dialos_materia, null)
        dialog.setContentView(mostrar)

        list_view_materias.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                dialog.show()
                val idMateria = listaMaterias[position].id
                val materia = listaMaterias[position]
                if(idMateria != null) {
                    mostrar.txt_editar.setOnClickListener {
                        irACrearEditarMateria(idMateria, materia)
                        dialog.dismiss()
                    }
                    mostrar.txt_eliminar.setOnClickListener {
                        materiaModel.delete(materia)
                        refrescarTabla(adaptador)
                        mostrarToast("Materia eliminada")
                        dialog.dismiss()
                    }
                    mostrar.txt_estudiantes.setOnClickListener {
                        irAGestionEstudiantes(idMateria)
                        dialog.dismiss()
                    }
                }

            }
    }

    fun irAGestionEstudiantes(idMateria: Int) {
        val intentExplicito = Intent(
            this,
            EstudianteActivity::class.java
        )
        intentExplicito.putExtra(EXTRA_ID, idMateria)
        startActivityForResult(intentExplicito, ESTUDIANTE_REQUEST)
    }

    fun irACrearEditarMateria(idMateria: Int = -1, materia: MateriaEntity? = null) {
        val intentExplicito = Intent(
            this,
            CrearEditarMateriaActivity::class.java
        )
        if (idMateria != -1) {
            if (materia != null) {
                Log.i("data", "Materia a editar ${materia}")
                intentExplicito.putExtra(EXTRA_ID, idMateria)
                intentExplicito.putExtra(EXTRA_NOMBRE, materia.nombre)
                intentExplicito.putExtra(EXTRA_CREDITOS, materia.creditos)
                intentExplicito.putExtra(EXTRA_CODIGO, materia.codigo)
                intentExplicito.putExtra(EXTRA_ESTADO, materia.estado)
                startActivityForResult(intentExplicito, EDIT_MATERIA_REQUEST)
            }
        }
        startActivityForResult(intentExplicito, ADD_MATERIA_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(data != null && requestCode == ADD_MATERIA_REQUEST && resultCode == Activity.RESULT_OK) {
            val nombre: String = data.getStringExtra(EXTRA_NOMBRE)
            val creditos: String = data.getStringExtra(EXTRA_CREDITOS)
            val codigo: String = data.getStringExtra(EXTRA_CODIGO)
            val estado: Int = data.getIntExtra(EXTRA_ESTADO, 1)
            materiaModel.insert(MateriaEntity(nombre = nombre, codigo = codigo , creditos =  creditos.toInt() , estado = estado))
            Toast.makeText(this, "Materia Registrada", Toast.LENGTH_SHORT).show()
            //  setearListaInicial()
            Log.i("data", "Materia registrada con éxito")


        } else if(data != null && requestCode == EDIT_MATERIA_REQUEST && resultCode == Activity.RESULT_OK) {
            val id = data.getIntExtra(EXTRA_ID, -1)
            if(id == -1) {
                Toast.makeText(this, "La Materia no pudo se actulizada!", Toast.LENGTH_SHORT).show()
                return
            }
            val nombre: String = data.getStringExtra(EXTRA_NOMBRE)
            val creditos: String = data.getStringExtra(EXTRA_CREDITOS)
            val codigo: String = data.getStringExtra(EXTRA_CODIGO)
            val estado: Int = data.getIntExtra(EXTRA_ESTADO, 1)
            materiaModel.update(MateriaEntity(nombre = nombre, codigo = codigo , creditos =  creditos.toInt() , estado = estado, id = id))
            Toast.makeText(this, "Materia Actualizada!", Toast.LENGTH_SHORT).show()

        } else {
            Toast.makeText(this, "Materia no guardada!", Toast.LENGTH_SHORT).show()
        }
    }


    fun refrescarTabla(
        adaptador: ArrayAdapter<MateriaEntity>
    ) {
        adaptador.notifyDataSetChanged()
    }

    fun mostrarToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    fun stringEstadoActivoInactivo(estado: Int): String {
        if (estado == 0) {
            return "Inativo"
        }
        return "Activo"
    }
}