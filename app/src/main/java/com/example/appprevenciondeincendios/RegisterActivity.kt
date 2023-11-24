package com.example.appprevenciondeincendios
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import android.content.Intent
class RegisterActivity : AppCompatActivity() {
    private lateinit var autenticacion: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        // Inicializa la instancia de FirebaseAuth
        autenticacion = FirebaseAuth.getInstance()
        // Referencias a las vistas
        val etCorreo: EditText = findViewById(R.id.etCorreo)
        val etContrasena: EditText = findViewById(R.id.etContrasena)
        val btnRegistrar: Button = findViewById(R.id.btnRegistrar)
        val btnIrALogin: Button = findViewById(R.id.btnIrALogin)
        btnRegistrar.setOnClickListener {
            val correo = etCorreo.text.toString().trim()
            val contrasena = etContrasena.text.toString().trim()
            if (correo.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(this, "Por favor, introduce un correo y contraseña.", Toast.LENGTH_SHORT).show()
            } else {
                // Registrar al usuario en Firebase
                autenticacion.createUserWithEmailAndPassword(correo, contrasena)
                    .addOnCompleteListener(this) { tarea ->
                        if (tarea.isSuccessful) {
                            // El registro fue exitoso
                            Toast.makeText(this, "Registro exitoso.", Toast.LENGTH_SHORT).show()
                            // No redirige automáticamente a ninguna parte
                            // El usuario puede decidir ir a la pantalla de inicio de sesión manualmente
                        } else {
                            // Si el registro falla, entrega un mensaje al usuario
                            Toast.makeText(this, "Registro fallido: ${tarea.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
        // Listener para el botón que lleva al usuario a la pantalla de inicio de sesión
        btnIrALogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}