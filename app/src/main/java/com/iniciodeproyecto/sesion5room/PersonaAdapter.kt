package com.iniciodeproyecto.sesion5room

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.iniciodeproyecto.sesion5room.basedatos.Persona

class PersonaAdapter(
    private val onEditClick: (Persona) -> Unit,
    private val onDeleteClick: (Persona) -> Unit
) : RecyclerView.Adapter<PersonaAdapter.PersonaViewHolder>() {
    private var personas: List<Persona> = emptyList()

    fun setPersonas(nuevasPersonas: List<Persona>) {
        personas = nuevasPersonas
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonaViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.lista_persona, parent, false)
        return PersonaViewHolder(itemView, onEditClick, onDeleteClick)
    }

    override fun getItemCount(): Int = personas.size
    override fun onBindViewHolder(holder: PersonaViewHolder, position: Int) {
        val persona = personas[position]
        holder.bind(persona)
    }

    class PersonaViewHolder(
        itemView: View,
        private val onEditClick: (Persona) -> Unit,
        private val onDeleteClick: (Persona) -> Unit,
    ) : RecyclerView.ViewHolder(itemView) {
        private val textViewNombre: TextView = itemView.findViewById(R.id.tv_nombre_completo)
        private val textViewEdad: TextView = itemView.findViewById(R.id.tv_edad)
        private val textViewDirection: TextView = itemView.findViewById(R.id.tv_direccion)
        private val buttonEdit: TextView = itemView.findViewById(R.id.bt_editar)
        private val buttonDelete: TextView = itemView.findViewById(R.id.bt_eliminar)

        fun bind(persona: Persona) {
            textViewNombre.text = persona.nombre
            textViewEdad.text = "Edad: ${persona.edad}"
            textViewDirection.text = "Dirección: ${persona.direccion}"

            buttonEdit.setOnClickListener() {
                onEditClick(persona)
            }

            buttonDelete.setOnClickListener() {
                onDeleteClick(persona)
            }
        }
    }
}

