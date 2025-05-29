package com.iniciodeproyecto.sesion5room

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iniciodeproyecto.sesion5room.basedatos.Persona
import com.iniciodeproyecto.sesion5room.basedatos.PersonaApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var app: PersonaApp
    private lateinit var personaAdapter: PersonaAdapter
    private var editandoPersona: Persona? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = applicationContext as PersonaApp
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.listViewLista)
        val editTextNombre = findViewById<EditText>(R.id.editTextNombre)
        val editTextEdad = findViewById<EditText>(R.id.editTextEdad)
        val editTextDireccion = findViewById<EditText>(R.id.editTextDireccion)
        val btnAdicionar = findViewById<Button>(R.id.buttonAdicionar)

        // Inicializando el adaptador de persona

        personaAdapter = PersonaAdapter(
            onEditClick = { persona ->
                editTextNombre.setText(persona.nombre)
                editTextEdad.setText(persona.edad.toString())
                editTextDireccion.setText(persona.direccion)
                btnAdicionar.setText("Actualizar persona")
                editandoPersona = persona
                Log.e("Sesion6", "Editando")
            },
            onDeleteClick = { persona ->
                lifecycleScope.launch {
                    try {
                        val personaDao = app.room.personaDao()
                        personaDao.delete(persona)
                        Toast.makeText(
                            this@MainActivity,
                            "Persona eliminada",
                            Toast.LENGTH_SHORT
                        ).show()
                        cargarPersonas()
                    } catch (e: Exception) {
                        Toast.makeText(
                            this@MainActivity,
                            "Error al eliminar ${e.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = personaAdapter

        cargarPersonas()

        btnAdicionar.setOnClickListener() {
            val nombre = editTextNombre.text.toString().trim()
            val edad = editTextEdad.text.toString().toInt()
            val direccion = editTextDireccion.text.toString().trim()

            lifecycleScope.launch {
                try {
                    val personaDao = app.room.personaDao()
                    if (editandoPersona == null) {
                        personaDao.insert(Persona(0, nombre, edad, direccion))
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                this@MainActivity,
                                "Persona adicionada",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        val personaActualizada = editandoPersona!!.copy(
                            nombre = nombre,
                            edad = edad,
                            direccion = direccion
                        )
                        personaDao.update(personaActualizada)
                        editandoPersona = null
                        btnAdicionar.setText("Adicionar persona")
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                this@MainActivity,
                                "Persona actualizada",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    reiniciarPersona(editTextNombre, editTextEdad, editTextDireccion)
                    cargarPersonas()
                } catch (e: Exception) {
                    Toast.makeText(
                        this@MainActivity,
                        "Error al guardar los datos ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

    }

    private fun cargarPersonas() {
        lifecycleScope.launch {
            try {
                val personaDao = app.room.personaDao()
                val personas = personaDao.getAll()
                withContext(Dispatchers.Main) {
                    personaAdapter.setPersonas(personas)
                }
            } catch (e: Exception) {
                Toast.makeText(
                    this@MainActivity,
                    "Error al cargar los datos ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun reiniciarPersona(etNombre:EditText, etEdad:EditText, etDireccion:EditText){
        etNombre.setText("")
        etEdad.setText("")
        etDireccion.setText("")
    }
}