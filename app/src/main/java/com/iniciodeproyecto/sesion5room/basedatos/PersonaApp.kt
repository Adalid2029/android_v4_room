package com.iniciodeproyecto.sesion5room.basedatos

import android.app.Application
import androidx.room.Room

class PersonaApp : Application() {
    lateinit var room: PersonaDb

    override fun onCreate() {
        super.onCreate()
        room = Room.databaseBuilder(
            this,
            PersonaDb::class.java,
            "persona"
        ).fallbackToDestructiveMigration()
            .build()
    }
}