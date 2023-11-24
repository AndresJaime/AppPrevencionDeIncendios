package com.example.appprevenciondeincendios

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase

class DetalleHabitacionActivity : AppCompatActivity() {
    private lateinit var databaseRef: DatabaseReference
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_habitacion)

        if (auth.currentUser == null) {
            finish()
            return
        }

        val habitacionId = intent.getStringExtra("habitacionId")
        val userId = auth.currentUser?.uid

        if (userId == null || habitacionId == null) {
            Log.e("DetalleActivity", "No se pasó el ID de la habitación al intent o el usuario no está logueado.")
            finish()
            return
        }

        val tvNombreHabitacion: TextView = findViewById(R.id.tvNombreHabitacion)
        val tvDescripcionHabitacion: TextView = findViewById(R.id.tvDescripcionHabitacion)
        val tvTemperatura: TextView = findViewById(R.id.tvTemperatura)

        databaseRef = FirebaseDatabase.getInstance().getReference("users").child(userId).child("habitaciones").child(habitacionId)

        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val habitacion = snapshot.getValue(Habitacion::class.java)
                if (habitacion != null) {
                    tvNombreHabitacion.text = habitacion.nombre
                    tvDescripcionHabitacion.text = habitacion.descripcion
                    tvTemperatura.text = habitacion.temperatura?.toString() ?: "Sin datos de temperatura"
                } else {
                    Log.e("DetalleActivity", "Datos de la habitación no encontrados.")
                    tvNombreHabitacion.text = getString(R.string.error_loading_room)
                    tvDescripcionHabitacion.text = ""
                    tvTemperatura.text = "Sin datos de temperatura"
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("DetalleActivity", "Error al cargar datos: ${error.message}")
                tvNombreHabitacion.text = getString(R.string.error_loading_room)
                tvDescripcionHabitacion.text = error.message
                tvTemperatura.text = error.message
            }
        })
    }
}
