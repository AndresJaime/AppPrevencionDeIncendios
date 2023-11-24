package com.example.appprevenciondeincendios

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.content.Intent

import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()
        // Botones
        val btnLogin: Button = findViewById(R.id.btnLogin)
        val btnRegister: Button = findViewById(R.id.btnRegister)
        val btnLogout: Button = findViewById(R.id.btnLogout)
        val btnCreateRoom: Button = findViewById(R.id.btnCreateRoom)
        val btnRooms: Button = findViewById(R.id.btnRooms)
        // Login
        btnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        // Registro
        btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        // Cerrar Sesión
        btnLogout.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        // Crear Habitación
        btnCreateRoom.setOnClickListener {
            val intent = Intent(this, CrearHabitacionActivity::class.java)
            startActivity(intent)
        }
        // Ver Habitaciones
        btnRooms.setOnClickListener {
            val intent = Intent(this, HabitacionesActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onStart() {
        super.onStart()
        // Verifica si hay un usuario logueado al inicio de la actividad
        if (auth.currentUser == null) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}

