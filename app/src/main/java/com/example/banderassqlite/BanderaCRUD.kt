package com.example.banderassqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

class BanderaCRUD(context: Context) {
    private var helper: DatabaseHelper? = null

    init {
        helper = DatabaseHelper(context)
    }

    fun newAlumno(item: Bandera) {
        //Abrimos la BD en modo escritura
        val db: SQLiteDatabase = helper?.writableDatabase!!
        //mapeo de columnas con valores a insertar en la BD
        val values = ContentValues()
        values.put(BanderasContract.Companion.Entrada.COLUMNA_ID, item.id)
        values.put(BanderasContract.Companion.Entrada.COLUMNA_NOMBRE, item.nombre)
        values.put(BanderasContract.Companion.Entrada.COLUMNA_IMAGEN, item.imagen)
        //insertar una fila
        //db.insert(AlumnosContract.Companion.Entrada.NOMBRE_TABLA,null,values)
        val consulta =
            "INSERT INTO alumnos VALUES('" + item.id + "','" + item.nombre + "'," + item.imagen + ");"
        db.execSQL(consulta)
        db.close()
    }

    fun newBanderaList(listado: MutableList<Bandera>) {
        listado.forEach { bandera ->
            newAlumno(bandera)
        }
    }

    fun getAlumnos(): MutableList<Bandera> {
        val items: MutableList<Bandera> = mutableListOf()
        //Abrimos la BD en modo lectura
        val db: SQLiteDatabase = helper?.readableDatabase!!
        //especificamos las columnas a leer
        val columnas = arrayOf(
            BanderasContract.Companion.Entrada.COLUMNA_ID,
            BanderasContract.Companion.Entrada.COLUMNA_NOMBRE,
            BanderasContract.Companion.Entrada.COLUMNA_IMAGEN

        )
        //creamos un cursor
        val c: Cursor = db.query(
            BanderasContract.Companion.Entrada.NOMBRE_TABLA,
            columnas, null, null, null, null, null
        )
        //recorremos el cursor
        while (c.moveToNext()) {
            items.add(
                Bandera(
                    c.getString(c.getColumnIndexOrThrow(BanderasContract.Companion.Entrada.COLUMNA_ID)),
                    c.getString(c.getColumnIndexOrThrow(BanderasContract.Companion.Entrada.COLUMNA_NOMBRE)),
                    c.getInt(c.getColumnIndexOrThrow(BanderasContract.Companion.Entrada.COLUMNA_IMAGEN))
                )
            )
        }
        c.close()
        db.close()
        return items
    }

    fun getAlumno(id: String): Bandera {
        var item: Bandera? = null
        //Abrimos la BD en modo lectura
        val db: SQLiteDatabase = helper?.readableDatabase!!
        //especificamos las columnas a leer
        val columnas = arrayOf(
            BanderasContract.Companion.Entrada.COLUMNA_ID,
            BanderasContract.Companion.Entrada.COLUMNA_NOMBRE,
            BanderasContract.Companion.Entrada.COLUMNA_IMAGEN
        )
        //creamos un cursor
        //val c: Cursor =db.query(AlumnosContract.Companion.Entrada.NOMBRE_TABLA,
        //   columnas," id=?",arrayOf(id),null,null,null)
        //creamos un cursor
        //Cursor c = db.query("alumnos", columnas, " id=?", id, null, null, null);
        val consulta = "SELECT * FROM alumnos WHERE id='$id';"
        val c = db.rawQuery(consulta, null)
        //recorremos el cursor
        while (c.moveToNext()) {
            item = Bandera(
                c.getString(c.getColumnIndexOrThrow(BanderasContract.Companion.Entrada.COLUMNA_ID)),
                c.getString(c.getColumnIndexOrThrow(BanderasContract.Companion.Entrada.COLUMNA_NOMBRE)),
                c.getInt(c.getColumnIndexOrThrow(BanderasContract.Companion.Entrada.COLUMNA_IMAGEN))

            )
        }
        c.close()
        db.close()
        return item!!
    }

    fun updateAlumno(item: Bandera) {
        val db: SQLiteDatabase = helper?.writableDatabase!!
        //mapeo de columnas con valores a insertar en la BD
        val values = ContentValues()
        values.put(BanderasContract.Companion.Entrada.COLUMNA_ID, item.id)
        values.put(BanderasContract.Companion.Entrada.COLUMNA_NOMBRE, item.nombre)
        values.put(BanderasContract.Companion.Entrada.COLUMNA_IMAGEN, item.imagen)
        //actualizamos la tabla
        db.update(
            BanderasContract.Companion.Entrada.NOMBRE_TABLA,
            values,
            " id=?",
            arrayOf(item.id)
        )
        db.close()
    }

    fun deleteAlumno(item: Bandera) {
        val db: SQLiteDatabase = helper?.writableDatabase!!
        db.delete(BanderasContract.Companion.Entrada.NOMBRE_TABLA, " id=?", arrayOf(item.id))
        db.close()
    }

    fun deleteListado(banderas: MutableList<Bandera>) {
        banderas.forEach {
            bandera -> deleteAlumno(bandera)
        }
    }
}