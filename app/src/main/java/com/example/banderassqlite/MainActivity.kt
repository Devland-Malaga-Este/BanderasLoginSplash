package com.example.banderassqlite

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.AdapterView.AdapterContextMenuInfo
import android.widget.ListView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private var listadoBanderas: MutableList<Bandera> = mutableListOf()
    private var intentLaunch: ActivityResultLauncher<Intent>? = null
    private var idComunidad: String = ""
    private var nombreComunidad: String = ""
    private var imagenComunidad: Int = 0
    private var adapterBandera: BanderaAdapter? = null
    private var nuevoNombre: String? = null
    private var indiceLista: Int? = 0
    private var crud = BanderaCRUD(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.crud = BanderaCRUD(this)

        if (crud.getAlumnos() == null) {
            crud.newBanderaList(cargar_lista())
        }

        //listadoBanderas = crud.getAlumnos()
        adapterBandera = BanderaAdapter(this, R.layout.banderas, crud.getAlumnos())
        this.recarga()
        val lv1 = findViewById<ListView>(R.id.listView1)
        adapterBandera = BanderaAdapter(this, R.layout.banderas, crud.getAlumnos())

        lv1.setOnItemClickListener { parent, view, position, id ->
            if (crud.getAlumnos()[position].nombre == "País Vasco") {
                Toast.makeText(
                    applicationContext, "Soy del " + crud.getAlumnos()[position].nombre,
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    applicationContext, "Soy de " + crud.getAlumnos()[position].nombre,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        intentLaunch = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == RESULT_OK) {
                idComunidad = it.data?.extras?.getString("id").toString()
                nuevoNombre = it.data?.extras?.getString("nombre").toString()
                imagenComunidad = it.data?.extras?.getInt("imagen")!!
                val bandera = Bandera(idComunidad, nuevoNombre!!, imagenComunidad)
                println("$idComunidad -- $nuevoNombre")
                crud.updateAlumno(bandera)
                this.recarga()
            }
        }

        registerForContextMenu(lv1)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_opciones, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.recargar -> {
                crud.deleteListado(crud.getAlumnos())
                crud.newBanderaList(cargar_lista())
                this.recarga()
                true
            }
            R.id.limpiar -> {
                crud.deleteListado(crud.getAlumnos())
                this.recarga()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.menu_contextual, menu)

        /* Obtenemos la posición del elemento que estamos seleccionando */
        val info = menuInfo as AdapterContextMenuInfo
        val posicion = info.position

        /* Establecemos la cabecera del menú contextual */
        menu?.setHeaderTitle(crud.getAlumnos()[posicion].nombre)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterContextMenuInfo
        val posicion = info.position
        idComunidad = crud.getAlumnos()[posicion].id
        nombreComunidad = crud.getAlumnos()[posicion].nombre
        imagenComunidad = crud.getAlumnos()[posicion].imagen

        println(nombreComunidad)

        return when (item.itemId) {
            R.id.eliminar -> {
                val dialogo = AlertDialog.Builder(this)
                    .setTitle("Eliminar $nombreComunidad")
                    .setMessage("¿Estás seguro que quiere eliminar $nombreComunidad?")
                    .setPositiveButton(
                        "Aceptar"
                    ) { dialogInterface, i ->
                        crud.deleteAlumno(crud.getAlumno(posicion.toString()))
                        this.recarga()
                    }.setNegativeButton(
                        "Cerrar"
                    ) { dialogInterface, i ->
                        dialogInterface.cancel()
                    }.create()
                dialogo.show()
                true
            }
            R.id.modificar -> {
                indiceLista = posicion
                val intent = Intent(this, ActivityModificar::class.java)
                intent.putExtra("id", idComunidad)
                intent.putExtra("nombre", nombreComunidad)
                intent.putExtra("imagen", imagenComunidad)
                intentLaunch?.launch(intent)
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun recarga() {
        val adapter = BanderaAdapter(
            this,
            R.layout.banderas, crud.getAlumnos()
        )
        val listView1 = findViewById<ListView>(R.id.listView1)
        listView1.cacheColorHint = 0
        listView1.adapter = adapter
    }

    private fun cargar_lista(): MutableList<Bandera> {
        var recargandoLista: MutableList<Bandera> = mutableListOf()

        val imagenes = intArrayOf(
            R.drawable.andalucia,
            R.drawable.aragon,
            R.drawable.asturias,
            R.drawable.baleares,
            R.drawable.canarias,
            R.drawable.cantabria,
            R.drawable.castillaleon,
            R.drawable.castillamancha,
            R.drawable.catalunya,
            R.drawable.ceuta,
            R.drawable.extremadura,
            R.drawable.galicia,
            R.drawable.larioja,
            R.drawable.madrid,
            R.drawable.melilla,
            R.drawable.murcia,
            R.drawable.navarra,
            R.drawable.paisvasco,
            R.drawable.valencia
        )
        val items = arrayOf(
            "Andalucía", "Aragón", "Asturias", "Baleares",
            "Canarias", "Cantabria", "Castilla y León", "Castilla-La Mancha",
            "Cataluña", "Ceuta", "Extremadura", "Galicia", "La Rioja", "Madrid", "Melilla",
            "Murcia", "Navarra", "País Vasco", "Comunidad Valenciana"
        )
        for (i in items.indices) {
            val bandera = Bandera(i.toString(), items[i], imagenes[i])
            recargandoLista.add(bandera)
        }
        return recargandoLista
    }

    override fun onBackPressed() {
        moveTaskToBack(true)
    }

}