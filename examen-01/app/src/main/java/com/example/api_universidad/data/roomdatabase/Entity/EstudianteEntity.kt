package com.example.api_universidad.data.roomdatabase.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "estudiante")
data class EstudianteEntity(val nombre: String,
                            val fechaNacimiento: String,
                            val sexo: String,
                            val estatura: Double,
                            val tieneBeca: Int,
                            val latitud: Double,
                            val longitud: Double,
                            val urlImagen: String,
                            val urlRedSocial: String,
                            val materiaId: Int,
                            @PrimaryKey(autoGenerate = true) val id: Int? = null
)