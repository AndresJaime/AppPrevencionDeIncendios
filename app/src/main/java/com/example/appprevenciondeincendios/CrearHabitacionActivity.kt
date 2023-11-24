package com.example.appprevenciondeincendios

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
class CrearHabitacionActivity : AppCompatActivity() {
    private val database = Firebase.database
    private val auth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_habitacion)
        if (auth.currentUser == null) {
            finish() // Cierra la actividad si no hay usuario logueado
            return
        }
        val editTextNombreHabitacion: EditText = findViewById(R.id.editTextNombreHabitacion)
        val editTextDescripcionHabitacion: EditText = findViewById(R.id.editTextDescripcionHabitacion)
        val buttonCrearHabitacion: Button = findViewById(R.id.buttonCrearHabitacion)
        buttonCrearHabitacion.setOnClickListener {
            val nombre = editTextNombreHabitacion.text.toString().trim()
            val descripcion = editTextDescripcionHabitacion.text.toString().trim()

            if (nombre.isNotEmpty() && descripcion.isNotEmpty()) {
                val userId = auth.currentUser?.uid
                val habitacion = Habitacion(nombre, descripcion)
                userId?.let { uid ->
                    val key = database.reference.child("users").child(uid).child("habitaciones").push().key
                    if (key != null) {
                        database.reference.child("users").child(uid).child("habitaciones").child(key).setValue(habitacion)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    // Notifica al usuario que la habitación fue creada
                                    finish() // Cierra la actividad
                                } else {
                                    // Notifica al usuario que la creación de la habitación falló
                                }
                            }
                    }
                }
            }
        }
    }
}
