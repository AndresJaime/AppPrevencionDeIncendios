package com.example.appprevenciondeincendios

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()
        val etEmail: EditText = findViewById(R.id.etEmail)
        val etPassword: EditText = findViewById(R.id.etPassword)
        val btnLogin: Button = findViewById(R.id.btnLogin)
        val btnGoToRegister: Button = findViewById(R.id.btnGoToRegister)
        btnGoToRegister.setOnClickListener {
            // Inicia la actividad RegisterActivity
            val registerIntent = Intent(this, RegisterActivity::class.java)
            startActivity(registerIntent)
        }
        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor, ingresa el correo electrónico y la contraseña.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Inicio de sesión exitoso, actualiza la UI con la información del usuario
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        // Si el inicio de sesión falla, muestra un mensaje al usuario
                        Toast.makeText(this, "Autenticación fallida: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}