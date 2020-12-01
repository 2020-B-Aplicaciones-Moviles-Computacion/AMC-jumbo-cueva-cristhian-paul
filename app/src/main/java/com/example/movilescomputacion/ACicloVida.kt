package com.example.movilescomputacion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import org.w3c.dom.Text

class ACicloVida : AppCompatActivity() {
    var total = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a_ciclo_vida)
        val botonSumar = findViewById<Button>(R.id.btn_ciclo_vida)
        val textoTotal = findViewById<TextView>(R.id.txv_ciclo_vida)

        botonSumar.setOnClickListener {
            total = total + 1
            textoTotal.text = total.toString()
        }
        Log.i("Ciclo vida", "onCreate")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.i("Ciclo vida", "onSaveInstanceState")
        if(outState != null) {
            outState.run {
                // Aqui se guarda se guardan cosas como un bundle
                // cualquier primitivos, enteros, booleanos, string, chars
                putInt("totalGuardadio", total)
            }
        }
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(
        savedInstanceState: Bundle?

    ) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.i("Ciclo vida", "onRestoreInstanceState")
        val totalRecuperado: Int? = savedInstanceState?.getInt("tatalGuardado")
        if(totalRecuperado != null){
            this.total = totalRecuperado
            val textoTotal = findViewById<TextView>(R.id.txv_ciclo_vida)
            textoTotal.text = total.toString()
        }
    }

    override fun onStart() {
        super.onStart()
        Log.i("Ciclo vida", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.i("Ciclo vida", "onResume")

    }

    override fun onPause() {
        super.onPause()
        Log.i("Ciclo vida", "onPause")

    }

    override fun onRestart() {
        super.onRestart()
        Log.i("Ciclo vida", "onRestart")

    }

    override fun onStop() {
        super.onStop()
        Log.i("Ciclo vida", "onStop")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("Ciclo vida", "onDestroy")

    }
}