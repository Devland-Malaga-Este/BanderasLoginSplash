package com.example.banderassqlite

import android.provider.BaseColumns

class BanderasContract {
    companion object {
        const val VERSION = 1
        class Entrada : BaseColumns {
            companion object {
                const val NOMBRE_TABLA = "alumnos"
                const val COLUMNA_ID = "id"
                const val COLUMNA_NOMBRE = "nombre"
                const val COLUMNA_IMAGEN = "imagen"
            }
        }
    }
}

