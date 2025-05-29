package com.iniciodeproyecto.sesion5room.basedatos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface PersonaDao {
    @Query("SELECT * FROM Persona ORDER BY id_persona DESC")
    suspend fun getAll(): List<Persona>

     @Query("SELECT * FROM Persona ORDER BY id_persona DESC")
     fun getAllLiveData(): LiveData<List<Persona>>

    @Query("SELECT * FROM Persona WHERE id_persona = :id")
    suspend fun getById(id: Int): Persona?

    @Insert
    suspend fun insert(persona: Persona)

    @Update
    suspend fun update(persona: Persona)

    @Delete
    suspend fun delete(persona: Persona)

    @Query("DELETE FROM Persona WHERE id_persona = :id")
    suspend fun deleteById(id: Int)


}