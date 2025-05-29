package com.iniciodeproyecto.sesion5room.basedatos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Persona")
data class Persona(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_persona")
    val id: Int = 0,

    @ColumnInfo(name = "nombre")
    val nombre: String,

    @ColumnInfo(name = "edad")
    val edad: Int,

    @ColumnInfo(name = "direccion")
    val direccion: String

)