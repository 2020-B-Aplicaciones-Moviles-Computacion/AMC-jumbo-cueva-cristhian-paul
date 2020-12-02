package com.example.movilescomputacion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val button2 = findViewById<Button>(R.id.btn_ir_list_view)
        button2.setOnClickListener {
            irListView()
        }

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
    fun irListView() {
        val intentExplicito = Intent(
            this,
            BlistView::class.java
        )
        startActivity(intentExplicito)
    }
}