package com.example.banderassqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(
        context, BanderasContract.Companion.Entrada.NOMBRE_TABLA, null,
        BanderasContract.VERSION
    ) {
    companion object {
        const val CREATE_BANDERAS_TABLA =
            "CREATE TABLE " + BanderasContract.Companion.Entrada.NOMBRE_TABLA +
                    " (" + BanderasContract.Companion.Entrada.COLUMNA_ID + " TEXT PRIMARY KEY, " +
                    BanderasContract.Companion.Entrada.COLUMNA_NOMBRE + " TEXT," +
                    BanderasContract.Companion.Entrada.COLUMNA_IMAGEN + " INT)"

        const val REMOVE_BANDERAS_TABLA =
            "DROP TABLE IF EXISTS " + BanderasContract.Companion.Entrada.NOMBRE_TABLA
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_BANDERAS_TABLA)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL(REMOVE_BANDERAS_TABLA)
        onCreate(db)
    }
}

