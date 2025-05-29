package com.iniciodeproyecto.sesion5room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.iniciodeproyecto.sesion5room.basedatos.PersonaDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import androidx.lifecycle.asLiveData

class PersonaViewModel(private val personaDao: PersonaDao) : ViewModel() {
    val todasPersonas = flow {
        emit(personaDao.getAll())
    }.asLiveData(Dispatchers.IO)
}

class PersonaViewModelFactory(private val personaDao: PersonaDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(PersonaViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return PersonaViewModel(personaDao) as T
        }
        throw IllegalArgumentException("No se puede crear view model")
    }
}