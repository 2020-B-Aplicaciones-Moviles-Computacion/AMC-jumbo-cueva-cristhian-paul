package com.example.api_universidad.data.roomdatabase.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "materia")
data class MateriaEntity(val nombre: String,
                val codigo: String,
                val creditos: Int,
                val estado: Int,
                @PrimaryKey(autoGenerate = true) val id: Int? = null)