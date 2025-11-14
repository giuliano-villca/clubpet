package com.example.cp

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton

class pag_entrada : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pag_entrada)

        // Ajuste de insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // FAB para abrir formulario_adopcion
        val btnAgregar = findViewById<FloatingActionButton>(R.id.btnAgregar)
        btnAgregar.setOnClickListener {
            startActivity(Intent(this, formulario_adopcion::class.java))
        }

        // Tarjeta de ejemplo que abre detalle_animal
        val tarjetaAnimal = findViewById<LinearLayout>(R.id.cardAnimal1) // Ponele un id a tu LinearLayout
        tarjetaAnimal.setOnClickListener {
            startActivity(Intent(this, detalle_animal::class.java))
        }
    }
}
