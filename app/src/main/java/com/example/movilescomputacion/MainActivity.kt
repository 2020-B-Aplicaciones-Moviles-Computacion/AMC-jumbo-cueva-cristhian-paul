package com.example.movilescomputacion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button_ir_ciclo_vida)
        button.setOnClickListener {
            irCicliVida()
        }

    }

    fun irCicliVida() {
        val intentExplicito = Intent(
            this,
            ACicloVida::class.java
        )
        startActivity(intentExplicito)
    }
}