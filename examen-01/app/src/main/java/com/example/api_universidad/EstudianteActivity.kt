package com.example.api_universidad

import android.annotation.SuppressLint
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.example.api_universidad.data.roomdatabase.Entity.EstudianteEntity
import com.example.api_universidad.data.roomdatabase.Model.MateriaViewModel
import com.github.kittinunf.result.Result
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_estudiante.*
import kotlinx.android.synthetic.main.activity_estudiante.btn_nuevo
import kotlinx.android.synthetic.main.dialog_estudiante.view.*
import java.util.*
import kotlin.collections.ArrayList
import androidx.lifecycle.Observer
import com.example.api_universidad.data.roomdatabase.Entity.MateriaEntity


const val ADD_ESTUDIANTE_REQUEST = 4
const val EDIT_ESTUDIANTE_REQUEST = 5
class EstudianteActivity : AppCompatActivity() {

    private lateinit var materiaModel: MateriaViewModel

    private lateinit var adaptador: ArrayAdapter<EstudianteEntity>

    private lateinit var listaEstudiantes: ArrayList<EstudianteEntity>

    private var idMateria: Int = -1

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estudiante)

        materiaModel = ViewModelProviders.of(this)[MateriaViewModel::class.java]
        idMateria = intent.getIntExtra(EXTRA_ID, -1)

        materiaModel.getMateriaEstudiantes(idMateria).observe(this, Observer {
            val resultado = it[0].estudiantes as ArrayList<EstudianteEntity>
            Log.i("Materias afasdfsdfadfsdf", "$resultado")
            setearListaInicial(resultado)
        })

        mostrarOpcionesListView()
        btn_nuevo.setOnClickListener {
            irACrearEstudiante(idMateria)
        }

    }

    fun mostrarOpcionesListView() {
        val dialog = BottomSheetDialog(this)
        val mostrar = layoutInflater.inflate(R.layout.dialog_estudiante, null)
        dialog.setContentView(mostrar)
        list_view_estudiantes.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val idEstudiante = listaEstudiantes[position].id
                val estudiante = listaEstudiantes[position]
                dialog.show()
                if (idEstudiante != null) {
                    mostrar.txt_editar.setOnClickListener {
                        irACrearEstudiante(idMateria, idEstudiante, estudiante)
                        dialog.dismiss()
                    }
                    mostrar.txt_eliminar.setOnClickListener {
                        materiaModel.deleteEstudiante(estudiante)
                        refrescarTabla(adaptador)
                        mostrarToast("Estudiante eliminado")
                        dialog.dismiss()
                    }
                }
            }
    }

    fun setearListaInicial(lista: ArrayList<EstudianteEntity>) {
        Log.i("Materias estuadiotantyes", "$lista")

        listaEstudiantes = lista
        adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            listaEstudiantes
        )
        list_view_estudiantes.adapter = adaptador
    }

    fun eliminarEstudiante(idEstudiante: Int) {
        ServicioBDD
            .deleteOne("estudiante", idEstudiante)
            .responseString{
                request, response, result ->
                when (result) {
                    is Result.Success -> {
                        val respuesta = result.get()
                        runOnUiThread {
                            refrescarTabla(adaptador)
                            //setearListaInicial()
                            mostrarToast("Estudiante eliminado")
                        }
                    }
                    is Result.Failure -> {
                        val ex = result.getException()
                        Log.i("http-klaxon", "Error http ${ex.message}")
                    }
                }
            }
    }

    fun irACrearEstudiante(idMateria: Int, idEstudiante: Int = -1, estudiante: EstudianteEntity? = null) {
        val intentExplicito = Intent(
            this,
            CrearEditarEstudianteActivity::class.java
        )
        intentExplicito.putExtra(EXTRA_ID, idMateria)
        if (idEstudiante != -1) {
            if (estudiante != null) {
                Log.i("data", "Materia a editar ${estudiante.estatura}")
                intentExplicito.putExtra(EXTRA_ID, idMateria)
                intentExplicito.putExtra(EXTRA_EST_ID, idEstudiante)
                intentExplicito.putExtra(EXTRA_EST_NOMBRE, estudiante.nombre)
                intentExplicito.putExtra(EXTRA_FECHA, estudiante.fechaNacimiento)
                intentExplicito.putExtra(EXTRA_SEXO, estudiante.sexo)
                intentExplicito.putExtra(EXTRA_IMAGEN, estudiante.urlImagen)
                intentExplicito.putExtra(EXTRA_RED, estudiante.urlRedSocial)
                intentExplicito.putExtra(EXTRA_BECA, estudiante.tieneBeca)
                intentExplicito.putExtra(EXTRA_ESTATURA, estudiante.estatura)
                intentExplicito.putExtra(EXTRA_LATITUD, estudiante.latitud)
                intentExplicito.putExtra(EXTRA_LONGITUD, estudiante.longitud)
                startActivityForResult(intentExplicito, EDIT_ESTUDIANTE_REQUEST)

            }
        }
        startActivityForResult(intentExplicito, ADD_ESTUDIANTE_REQUEST)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(data != null && requestCode == ADD_ESTUDIANTE_REQUEST && resultCode == Activity.RESULT_OK) {
            val nombre: String = data.getStringExtra(EXTRA_EST_NOMBRE)
            val fechaNacimiento: String = data.getStringExtra(EXTRA_FECHA)
            val sexo: String = data.getStringExtra(EXTRA_SEXO)
            val urlImagen: String = data.getStringExtra(EXTRA_IMAGEN)
            val urlRedSocial: String = data.getStringExtra(EXTRA_RED)
            val estatura: Double = data.getDoubleExtra(EXTRA_ESTATURA, 0.00)
            val tieneBeca: Int = data.getIntExtra(EXTRA_BECA,0)
            val latitud: Double = data.getDoubleExtra(EXTRA_LATITUD,0.00)
            val longitud: Double = data.getDoubleExtra(EXTRA_LONGITUD,0.00)
            val materiaId: Int = data.getIntExtra(EXTRA_ID,1)
            materiaModel.insertEstudiante( EstudianteEntity(
                nombre = nombre,
                fechaNacimiento = fechaNacimiento,
                sexo =  sexo,
                estatura =  estatura,
                urlImagen =  urlImagen,
                urlRedSocial =  urlRedSocial,
                tieneBeca =  tieneBeca,
                latitud =  latitud,
                longitud =  longitud,
                materiaId = materiaId
            ))
            Toast.makeText(this, "Estudiante Registrado", Toast.LENGTH_SHORT).show()
            Log.i("data", "Estudiante registrado con éxito")


        } else if(data != null && requestCode == EDIT_ESTUDIANTE_REQUEST && resultCode == Activity.RESULT_OK) {
            val id = data.getIntExtra(EXTRA_EST_ID, -1)
            if(id == -1) {
                Toast.makeText(this, "El estudiante no pudo se actulizado!", Toast.LENGTH_SHORT).show()
                return
            }
            val nombre: String = data.getStringExtra(EXTRA_EST_NOMBRE)
            val fechaNacimiento: String = data.getStringExtra(EXTRA_FECHA)
            val sexo: String = data.getStringExtra(EXTRA_SEXO)
            val urlImagen: String = data.getStringExtra(EXTRA_IMAGEN)
            val urlRedSocial: String = data.getStringExtra(EXTRA_RED)
            val estatura: Double = data.getDoubleExtra(EXTRA_ESTATURA, 0.00)
            val tieneBeca: Int = data.getIntExtra(EXTRA_BECA,0)
            val latitud: Double = data.getDoubleExtra(EXTRA_LATITUD,0.00)
            val longitud: Double = data.getDoubleExtra(EXTRA_LONGITUD,0.00)
            val materiaId: Int = data.getIntExtra(EXTRA_CODIGO,1)
            materiaModel.insertEstudiante( EstudianteEntity(
                nombre = nombre,
                fechaNacimiento = fechaNacimiento,
                sexo =  sexo,
                estatura =  estatura,
                urlImagen =  urlImagen,
                urlRedSocial =  urlRedSocial,
                tieneBeca =  tieneBeca,
                latitud =  latitud,
                longitud =  longitud,
                materiaId = materiaId
            ))
            Toast.makeText(this, "Estudiante Actualizado!", Toast.LENGTH_SHORT).show()

        } else {
            Toast.makeText(this, "Estudiante no guardado!", Toast.LENGTH_SHORT).show()
        }
    }

    fun refrescarTabla(
        adaptador: ArrayAdapter<EstudianteEntity>
    ) {
        adaptador.notifyDataSetChanged()
    }

    fun mostrarToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    fun transformarDataString(fecha: Date): String {
        val calendar = Calendar.getInstance()
        calendar.time = fecha
        return "${calendar.get(Calendar.DAY_OF_MONTH)}/${calendar.get(Calendar.MONTH)}/${calendar.get(
            Calendar.YEAR
        )}"
    }

}