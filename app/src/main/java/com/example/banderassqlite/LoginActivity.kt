package com.example.banderassqlite

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast

class LoginActivity : AppCompatActivity() {
    private var etEmail: EditText? = null
    private var etPassword: EditText? = null

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private var swRecordar: CheckBox? = null
    private var prefs: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        val btLogin: Button = findViewById(R.id.btLogin)
        swRecordar = findViewById(R.id.swRecordar)
        prefs = getSharedPreferences("app", MODE_PRIVATE)
        establecerValoresSiExisten()
        btLogin.setOnClickListener {
            val email = etEmail!!.text.toString()
            val password = etPassword!!.text.toString()
            if (login(email, password)) goToMain()
            guardarPreferencias(email, password)
        }
    }

    private fun eMailValido(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun passwordValida(password: String): Boolean {
        return !TextUtils.isEmpty(password) && password.length > 7
    }

    private fun login(email: String, password: String): Boolean {
        var valido = false
        if (!eMailValido(email)) {
            Toast.makeText(
                this,
                "Correo no válido. Introduzca un correo correcto",
                Toast.LENGTH_SHORT
            ).show()
        } else if (!passwordValida(password)) {
            Toast.makeText(
                this,
                "Contraseña no válida. Debe tener al menos 8 caracteres",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            valido = true
        }
        return valido
    }

    private fun goToMain() {
        val intent = Intent(this, MainActivity::class.java)
        //Evita que pasemos de nuevo a la activity login
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun guardarPreferencias(email: String, password: String) {
        val editor = prefs!!.edit()
        if (swRecordar!!.isChecked) {
            editor.putString("email", email)
            editor.putString("password", password)
            editor.putBoolean("recordar", true)
            editor.apply()
        } else {
            editor.clear()
            editor.putBoolean("recordar", false)
            editor.apply()
        }
    }

    private fun establecerValoresSiExisten() {
        val email = prefs!!.getString("email", "")
        val password = prefs!!.getString("password", "")
        val recordar = prefs!!.getBoolean("recordar", false)
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            etEmail!!.setText(email)
            etPassword!!.setText(password)
            swRecordar!!.isChecked = recordar
        }
    }
}