package com.example.appprevenciondeincendios

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class HabitacionesActivity : AppCompatActivity() {
    private val database = Firebase.database
    private val auth = FirebaseAuth.getInstance()
    private lateinit var layoutHabitaciones: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_habitaciones)

        if (auth.currentUser == null) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        layoutHabitaciones = findViewById(R.id.layout_habitaciones)
        val userId = auth.currentUser?.uid

        userId?.let { uid ->
            val myRef = database.getReference("users").child(uid).child("habitaciones")
            myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    layoutHabitaciones.removeAllViews()
                    for (ds in dataSnapshot.children) {
                        val habitacion = ds.getValue(Habitacion::class.java)
                        val habitacionId = ds.key
                        if (habitacion != null && habitacionId != null) {
                            addHabitacionButton(habitacion, habitacionId)
                        }
                    }
                }
                override fun onCancelled(databaseError: DatabaseError) {
                    // handle error
                }
            })
        }
    }

    private fun addHabitacionButton(habitacion: Habitacion, habitacionId: String) {
        val button = Button(this).apply {
            text = habitacion.nombre
            setOnClickListener {
                val intent = Intent(this@HabitacionesActivity, DetalleHabitacionActivity::class.java)
                intent.putExtra("habitacionId", habitacionId)
                startActivity(intent)
            }
        }
        layoutHabitaciones.addView(button)
    }
}
