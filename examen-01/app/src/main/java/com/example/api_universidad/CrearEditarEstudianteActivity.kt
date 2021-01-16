package com.example.api_universidad

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.Instrumentation
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.beust.klaxon.Klaxon
import kotlinx.android.synthetic.main.activity_crear_editar_estudiante.*
import java.util.*
import com.github.kittinunf.result.Result
import kotlinx.android.synthetic.main.activity_crear_editar_estudiante.btn_cancelar
import kotlinx.android.synthetic.main.activity_crear_editar_estudiante.btn_guardar
import kotlinx.android.synthetic.main.activity_crear_editar_estudiante.txt_nombre
import kotlinx.android.synthetic.main.activity_crear_editar_materia.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

const val EXTRA_EST_ID = " com.example.api_universidad.EXTRA_EST_ID"
const val EXTRA_EST_NOMBRE = " com.example.api_universidad.EXTRA_EST_NOMBRE"
const val EXTRA_FECHA = " com.example.api_universidad.EXTRA_FECHA"
const val EXTRA_SEXO = " com.example.api_universidad.EXTRA_SEXO"
const val EXTRA_IMAGEN = " com.example.api_universidad.EXTRA_IMAGEN"
const val EXTRA_RED = " com.example.api_universidad.EXTRA_RED"
const val EXTRA_BECA = " com.example.api_universidad.EXTRA_BECA"
const val EXTRA_ESTATURA = " com.example.api_universidad.EXTRA_ESTATURA"
const val EXTRA_LATITUD = " com.example.api_universidad.EXTRA_LATITUD"
const val EXTRA_LONGITUD = " com.example.api_universidad.EXTRA_LONGITUD"
class CrearEditarEstudianteActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {


    private lateinit var mode : CrearEditarEstudianteActivity.Mode

    private var idMateria: Int = -1
    private var idEstudiante: Int = -1
    private var dia = 0
    private var mes = 0
    private var anio = 0
    private var latitud = 0.0
    private var longitud = 0.0
    private var urlImagen = ""
    private var urlRedSocial = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_editar_estudiante)

        idMateria = intent.getIntExtra(EXTRA_ID, -1)
        txt_id_universidad.setText("${idMateria}")
        idEstudiante = intent.getIntExtra(EXTRA_EST_ID, -1)

        mode = if(idEstudiante == -1) Mode.AddEstudiante
        else Mode.EditEstudiante

        when(mode) {
            Mode.AddEstudiante -> textView2.setText("Agregar Estudiante")
            Mode.EditEstudiante -> {
                textView2.setText("Editar Estudiante")
                latitud = intent.getDoubleExtra(EXTRA_LATITUD,0.00)
                longitud = intent.getDoubleExtra(EXTRA_LONGITUD, 0.00)
                var simpleFormat =  DateTimeFormatter.ISO_DATE;
                var convertedDate = LocalDate.parse(intent.getStringExtra(EXTRA_FECHA), simpleFormat)
                txt_nombre.setText(intent.getStringExtra(EXTRA_EST_NOMBRE))
                txt_fecha_nacimiento.setText(convertedDate.toString())
                val estatura = intent.getDoubleExtra(EXTRA_ESTATURA,0.00)
                if (estatura != null)
                    txt_estatura.setText(estatura.toString())
                txt_url_foto_perfil.setText(intent.getStringExtra(EXTRA_IMAGEN))
                txt_red_social.setText(intent.getStringExtra(EXTRA_RED))
                urlImagen = intent.getStringExtra(EXTRA_IMAGEN)
                urlRedSocial = intent.getStringExtra(EXTRA_RED)
                rd_btn_masculino.isEnabled = false
                rd_btn_femenino.isEnabled = false
                btn_calendario.isEnabled = false
                if (intent.getStringExtra(EXTRA_SEXO) == "M") {
                    raddio_button_sexo.check(rd_btn_masculino.id)
                    txt_sexo.setText("M")
                } else {
                    raddio_button_sexo.check(rd_btn_femenino.id)
                    txt_sexo.setText("F")
                }

                if (intent.getIntExtra(EXTRA_BECA, 0) == 1) {
                    check_beca.setChecked(true)
                }
            }
        }

        mostrarDatePicker()
        setearRaddioButton()
        setearCheckbox()

        btn_guardar.setOnClickListener {
          //  guardar()
            val idMateriatxt = txt_id_universidad.text.toString()
            val nombre = txt_nombre.text.toString()
            var simpleFormat =  DateTimeFormatter.ISO_DATE;
            var convertedDate = LocalDate.parse(txt_fecha_nacimiento.text.toString(), simpleFormat)
            val fechaNac = convertedDate.toString()
            val sexo = txt_sexo.text.toString()
            val estatura = txt_estatura.text.toString()
            val tieneBeca = txt_beca.text.toString()
            val urlImagenPerfil = txt_url_foto_perfil.text.toString()
            val urlFacebook = txt_red_social.text.toString()
            val intentExplicito =Intent(
                this,
                EstudianteActivity::class.java
            )
            // only if note ID was provided i.e. we are editing
            if(idEstudiante != -1)
                intentExplicito.putExtra(EXTRA_EST_ID, idEstudiante)
            intentExplicito.putExtra(EXTRA_EST_NOMBRE, nombre)
            intentExplicito.putExtra(EXTRA_ID, idMateria)
            intentExplicito.putExtra(EXTRA_FECHA, fechaNac)
            intentExplicito.putExtra(EXTRA_SEXO, sexo)
            intentExplicito.putExtra(EXTRA_IMAGEN, urlImagenPerfil)
            intentExplicito.putExtra(EXTRA_RED, urlFacebook)
            intentExplicito.putExtra(EXTRA_BECA, tieneBeca)
            intentExplicito.putExtra(EXTRA_ESTATURA, estatura)
            intentExplicito.putExtra(EXTRA_LATITUD, latitud)
            intentExplicito.putExtra(EXTRA_LONGITUD, longitud)

            setResult(Activity.RESULT_OK, intentExplicito)
            finish()
        }

        btn_cancelar.setOnClickListener {
            finish()
        }

        btn_ubicacion.setOnClickListener {
            irAMapaEstudiante()
        }
    }

    fun irAMapaEstudiante() {
        val intent = Intent(
            this,
            MapaActivity::class.java
        )
        Log.i("latitud", "$latitud")
        if (idEstudiante != -1) {
            intent.putExtra("longitud", longitud)
            intent.putExtra("latitud", latitud)
            intent.putExtra("urlImagen", urlImagen)
            intent.putExtra("urlRedSocial", urlRedSocial)
        }
        startActivityForResult(intent, 300)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_OK -> {
                when (requestCode) {
                    300 -> {
                        latitud = data?.getDoubleExtra("latitud", 0.0)!!
                        longitud = data.getDoubleExtra("longitud", 0.0)
                    }
                }
            }
        }
    }


    fun setearCheckbox() {
//        ayuda_check.setText("0")
        check_beca.setOnClickListener {
            val tieneBeca = check_beca.isChecked()
            if (tieneBeca) {
                txt_beca.setText("1")
            } else {
                txt_beca.setText("0")
            }
        }
    }

    fun setearRaddioButton() {
        raddio_button_sexo.setOnCheckedChangeListener { group, checkedId ->
            val seleccionoMasculino = checkedId == R.id.rd_btn_masculino
            val seleccionoFemenino = checkedId == R.id.rd_btn_femenino
            if (seleccionoMasculino) {
                txt_sexo.setText("M")
            } else if (seleccionoFemenino) {
                txt_sexo.setText("F")
            }
        }
    }

    fun obtnerFechaActual() {
        val calendario = Calendar.getInstance()
        dia = calendario.get(Calendar.DAY_OF_MONTH)
        mes = calendario.get(Calendar.MONTH)+1
        anio = calendario.get(Calendar.YEAR)
        Log.i("data", "ano $anio")
        Log.i("data", "mes $mes")
        Log.i("data", "dia $dia")

    }

    fun mostrarDatePicker() {
        btn_calendario.setOnClickListener {
            obtnerFechaActual()

            val datePicker = DatePickerDialog(
                this,
                this,
                anio,
                mes,
                dia
            )
            datePicker.show()
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        if(month >= 10) {
            txt_fecha_nacimiento.setText("${year}-${month}-${dayOfMonth}")

        } else {
            txt_fecha_nacimiento.setText("${year}-0${month}-${dayOfMonth}")
        }
    }

    fun transformarDataString(fecha: Date): String {
        val calendar = Calendar.getInstance()
        calendar.time = fecha
        return "${calendar.get(Calendar.YEAR)}-${calendar.get(Calendar.MONTH)}-${calendar.get(
            Calendar.DAY_OF_MONTH
        )}"
    }

    fun mostrarToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    private sealed class Mode {
        object AddEstudiante : Mode()
        object EditEstudiante : Mode()

    }
}
