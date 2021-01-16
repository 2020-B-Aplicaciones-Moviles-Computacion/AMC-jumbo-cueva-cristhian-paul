package com.example.api_universidad.data.roomdatabase.Entity.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.api_universidad.data.roomdatabase.Entity.EstudianteEntity
import com.example.api_universidad.data.roomdatabase.Entity.MateriaEntity

data class MateriaEstudiantes(
    @Embedded val materia: MateriaEntity,
    @Relation(parentColumn = "id", entityColumn = "materiaId")
    val  estudiantes: List<EstudianteEntity>)