package com.example.movilescomputacion

import android.content.ClipDescription
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class ESqliteHelperUsuario (contexto: Context?): SQLiteOpenHelper(
        contexto,
        "moviles",
        null,
        1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptCrearTablaUsuario =
                """
                   CREATE TABLE USUARIO (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        nombre VARCHAR(50),
                        descripcion VARCHAR (50)
                   )
               """.trimIndent()
        Log.i("bbd", "Creando la tabla usuario")
        db?. execSQL(scriptCrearTablaUsuario)


    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
    fun consultarUsuarioPorId(id: Int): EUsuarioBDD{
        // si se quiere una inyecsion SQL
        val scriptConsultarUsuario = "SELECT * FROM USUARIO WHERE id = ${id}"
        val dbReadable = readableDatabase

        val resultado = dbReadable.rawQuery(
                scriptConsultarUsuario, null
        )

        val exisiteUsuario = resultado.moveToFirst() // iterable

        val usuarioEncontrado = EUsuarioBDD(0, "", "")

        if(exisiteUsuario) {
            do {

                val id = resultado.getInt(0) //id
                val nombre =  resultado.getString(1) // nombre
                val descripcion = resultado.getString(2) // descripcion
                if(id != null) {
                    usuarioEncontrado.id = id
                    usuarioEncontrado.nombre = nombre
                    usuarioEncontrado.descripcion = descripcion
                }
            } while (resultado.moveToNext())
        }
        resultado.close()
        dbReadable.close()
        return usuarioEncontrado
    }

    fun crearUsarioFormulario(nombre: String, descripcion: String): Boolean {
        val conexionEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombre", nombre)
        valoresAGuardar.put("descripcion", descripcion)

        val resultadoEscritura: Long = conexionEscritura
                .insert(
                        "USUARIO",
                        null,
                        valoresAGuardar
                )
        conexionEscritura.close()
        return if(resultadoEscritura.toInt() == -1 ) false else true
    }

}