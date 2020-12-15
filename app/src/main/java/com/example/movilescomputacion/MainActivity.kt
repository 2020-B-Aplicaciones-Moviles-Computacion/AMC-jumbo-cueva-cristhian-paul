package com.example.movilescomputacion

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val CODIGO_ACTUALIZAR_DATOS: Int = 102
        BaseDatosMemoria.cargaInicialDatos()

        val button2 = findViewById<Button>(R.id.btn_ir_list_view)
        button2.setOnClickListener {
            irActivida(BlistView::class.java)
        }

        val button = findViewById<Button>(R.id.button_ir_ciclo_vida)
        button.setOnClickListener {
            irActivida( ACicloVida::class.java)
        }

        val buttonIntentExplicitoParametros = findViewById<Button>(R.id.btn_ir_intent_explicito_parametros)
        buttonIntentExplicitoParametros.setOnClickListener {

            val intentExplicito = Intent(
                    this,
                    CIntentExplicitoParametros::class.java
            )
            intentExplicito.putExtra("nombre", "Cristhian")
            intentExplicito.putExtra("apellido", "Jumbo")
            intentExplicito.putExtra("edad", 31)
            startActivityForResult(intentExplicito, 102)


            val liga = DLiga("Kanto", "Pokemon")
            val entrenador = BEntrenador("Ash", "Pieblo Paleta", liga)
            val parametros = arrayListOf<Pair<String, *>>(
                Pair("nombre", "Cristhian"),
                Pair("apellido", "Jumbo"),
                Pair("edad", 23),
                Pair("ash", entrenador)
            )
            irActivida(CIntentExplicitoParametros::class.java, parametros, CODIGO_ACTUALIZAR_DATOS)
        }
        EBaseDeDatos.TablaUsuarioE = ESqliteHelperUsuario(this)
        val usuarioEcontrado = EBaseDeDatos.TablaUsuarioE?.consultarUsuarioPorId(1)
        Log.i("bdd", "ID:${usuarioEcontrado?.id} Nombre: ${usuarioEcontrado?.nombre} Descripcion: ${usuarioEcontrado?.descripcion}")

        if(usuarioEcontrado?.id == 0) {
            val resultado = EBaseDeDatos.TablaUsuarioE?.crearUsarioFormulario("Adrian", "Profe")
            if(resultado != null) {
                Log.i("bdd", "se creo correcatamente")
            } else {
                Log.i("bdd", "hubo Errrores ${resultado}")
            }
        }
    } //Fin on create


    fun irActivida(
        clase: Class<*>,
        parametros: ArrayList<Pair<String, *>>? = null,
        codigo: Int? = null
    ) {
        val intentExplicito = Intent(
            this,
            clase
        )
        if (parametros != null){
            parametros.forEach{
                var nombreVariables = it.first
                var valorVariable =  it.second
                var tipoDatos = false

                tipoDatos = it.second is String // instnceOf()
                if(tipoDatos == true){

                    intentExplicito.putExtra(nombreVariables, valorVariable as String)
                }
                tipoDatos = it.second is Int // instnceOf()
                if(tipoDatos == true){

                    intentExplicito.putExtra(nombreVariables, valorVariable as Int)
                }
                tipoDatos = it.second is Parcelable // instnceOf()
                if(tipoDatos == true){

                    intentExplicito.putExtra(nombreVariables, valorVariable as Parcelable)
                }
            }
        }
        if (codigo != null) {
            startActivityForResult(intentExplicito,codigo)
        } else {
            startActivity(intentExplicito)
        }
        intentExplicito.putExtra("nombre", "Adrian")
        startActivity(intentExplicito)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode){
           102 -> {
               if(requestCode == Activity.RESULT_OK) {
                   Log.i("intent-explicito", "Si actulizo los datos")
                   if(data != null) {
                       val nombre = data.getStringExtra("nombre")
                       val edad = data.getIntExtra("edad", 0)

                       Log.i("intent-explicito", "Nombre: ${nombre} Edad: ${edad}")
                   }

               } else {
                   Log.i("intent-explicito", "Usuario no llego los datos")
               }
           }
        }
    }
}