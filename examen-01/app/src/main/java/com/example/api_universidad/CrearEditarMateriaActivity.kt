package com.example.api_universidad

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_crear_editar_materia.*


const val EXTRA_ID = " com.example.api_universidad.EXTRA_ID"
const val EXTRA_NOMBRE = " com.example.api_universidad.EXTRA_NOMBRE"
const val EXTRA_CREDITOS = " com.example.api_universidad.EXTRA_CREDITOS"
const val EXTRA_CODIGO = " com.example.api_universidad.EXTRA_CODIGO"
const val EXTRA_ESTADO = " com.example.api_universidad.EXTRA_ESTADO"

class CrearEditarMateriaActivity : AppCompatActivity() {

    private lateinit var mode : Mode

    private var idMateria: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_editar_materia)

        idMateria = intent.getIntExtra(EXTRA_ID, -1)

        mode = if(idMateria == -1) Mode.AddMateria
        else Mode.EditMateria

        when(mode) {
                Mode.AddMateria -> txt_titulo.setText("Agregar Materia")
            Mode.EditMateria -> {
                setearCheckbox(intent.getIntExtra(EXTRA_ESTADO, 1)!!)
                txt_titulo.setText("Editar Materia")
                plain_txt_nombre.setText(intent.getStringExtra(EXTRA_NOMBRE))
                txt_categoria.setText(intent.getStringExtra(EXTRA_CODIGO))
                if (intent.getIntExtra(EXTRA_CREDITOS,0) != null)
                    plain_txt_fundacion.setText(intent.getIntExtra(EXTRA_CREDITOS,0).toString())
                btn_modal_categoria.setText(intent.getStringExtra(EXTRA_CODIGO))
                titulo_check.visibility = View.VISIBLE
                check_estado.visibility = View.VISIBLE

                if (intent.getIntExtra(EXTRA_ESTADO, 1) == 1) {
                    check_estado.setChecked(true)
                }
            }
        }

        btn_modal_categoria.setOnClickListener {
            mostrarModalCategorias()
        }

        btn_guardar.setOnClickListener {
            val nombre = plain_txt_nombre.text.toString()
            val codigo = txt_categoria.text.toString()
            val creditos = plain_txt_fundacion.text.toString()
            val data = Intent(
                this,
                MateriaActivity::class.java
            )
            // only if note ID was provided i.e. we are editing
            if(idMateria != -1)
                data.putExtra(EXTRA_ID, idMateria)
            data.putExtra(EXTRA_NOMBRE, nombre)
            data.putExtra(EXTRA_CODIGO, codigo)
            data.putExtra(EXTRA_CREDITOS, creditos)

            setResult(Activity.RESULT_OK, data)
            finish()
        }

        btn_cancelar.setOnClickListener {
            finish()
        }
    }


    fun mostrarModalCategorias() {
        val arrayCategorias = arrayOf("A", "B", "C", "D")
        val mBuilder = AlertDialog.Builder(
            this
        )
        mBuilder.setTitle("Elija la categoria de materia")
        mBuilder.setSingleChoiceItems(
            arrayCategorias,
            -1
        ) { dialog: DialogInterface?, indice: Int ->
            val cateriaSeleccionada = arrayCategorias[indice]
            txt_categoria.setText(cateriaSeleccionada)
            btn_modal_categoria.text = cateriaSeleccionada
            dialog?.dismiss()
        }
        mBuilder.setNeutralButton("Cancelar") { dialog: DialogInterface?, which: Int ->
            dialog?.cancel()
        }
        val dialog = mBuilder.create()
        dialog.show()
    }

    fun setearCheckbox(estado: Int) {
        if (estado == 1) {
            ayuda_check.setText("1")
        } else {
            ayuda_check.setText("0")
        }
        check_estado.setOnClickListener {
            val estado = check_estado.isChecked()
            if (estado) {
                ayuda_check.setText("1")
            } else {
                ayuda_check.setText("0")
            }
        }
    }
    private sealed class Mode {
        object AddMateria : Mode()
        object EditMateria : Mode()

    }
}