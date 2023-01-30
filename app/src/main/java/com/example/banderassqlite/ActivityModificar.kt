package com.example.banderassqlite

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView

class ActivityModificar : AppCompatActivity(), View.OnClickListener {
    private var btnCambiar: Button? = null
    private var btnVolver: Button? = null
    private var etNombre: EditText? = null
    private var idComunidad:String? = null
    private var nombreComunidad: String? = null
    private var imagenComunidad: Int? = 0
    private var nuevoNombre: String? = null

    @SuppressLint("SetTextI18n", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modificar)

        /* Recuperamos vistas */
        btnCambiar = findViewById(R.id.btnCambiar)
        btnVolver = findViewById(R.id.btnCancelar)
        val tvNombreComunidad: TextView? = findViewById(R.id.tvNombre)
        val ivComunidad: ImageView? = findViewById(R.id.ivComunidad)
        etNombre= findViewById(R.id.etNombre)

        /* Recuperamos los valores del Intent y lo asignamos a variables */
        idComunidad = intent.extras?.getString("id")
        nombreComunidad = intent.extras?.getString("nombre")
        imagenComunidad = intent.extras?.get("imagen") as Int?

        /* Establecemos texto e imagen de la comunidad */
        tvNombreComunidad?.text = nombreComunidad
        imagenComunidad?.let { ivComunidad?.setImageResource(it) }

        btnCambiar?.setOnClickListener(this)
        btnVolver?.setOnClickListener(this)

    }

    override fun onClick(vista: View?) {
        val intent = Intent()
        if (vista == btnCambiar) {
            nuevoNombre = etNombre?.text.toString()
            nombreComunidad = nuevoNombre
            println(nombreComunidad)
            intent.putExtra("id", idComunidad)
            intent.putExtra("nombre", nombreComunidad)
            intent.putExtra("imagen", imagenComunidad)
            setResult(RESULT_OK, intent)
            finish()
        } else if (vista == btnVolver) {
            setResult(RESULT_CANCELED, intent)
            finish()
        }
    }
}