package com.example.cp

import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class formulario_adopcion : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_formulario_adopcion)

        // ---- Vincular vistas ----
        val edtNombre = findViewById<EditText>(R.id.edtNombreAnimal)
        val edtRaza = findViewById<EditText>(R.id.edtRaza)
        val rgSexo = findViewById<RadioGroup>(R.id.rgSexo)
        val edtDireccion = findViewById<EditText>(R.id.edtDireccion)
        val dateNacimiento = findViewById<DatePicker>(R.id.dateNacimiento)
        val edtColor = findViewById<EditText>(R.id.edtColor)
        val edtCaracteristicas = findViewById<EditText>(R.id.edtCaracteristicas)
        val chkCondiciones = findViewById<CheckBox>(R.id.chkCondiciones)
        val btnPublicar = findViewById<Button>(R.id.btnPublicar)

        // ---- Acción del botón ----
        btnPublicar.setOnClickListener {

            // Validar checkbox
            if (!chkCondiciones.isChecked) {
                Toast.makeText(this, "Debes aceptar las condiciones", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Obtener valores
            val nombre = edtNombre.text.toString()
            val raza = edtRaza.text.toString()
            val direccion = edtDireccion.text.toString()
            val color = edtColor.text.toString()
            val caracteristicas = edtCaracteristicas.text.toString()

            // Sexo
            val sexo = when (rgSexo.checkedRadioButtonId) {
                R.id.rbMacho -> "Macho"
                R.id.rbHembra -> "Hembra"
                else -> ""
            }

            // Fecha nacimiento
            val dia = dateNacimiento.dayOfMonth
            val mes = dateNacimiento.month + 1  // +1 porque empieza en 0
            val año = dateNacimiento.year
            val fechaNacimiento = "$año-$mes-$dia"

            // Mostrar datos enviados
            Toast.makeText(
                this,
                "ENVIADO:\n$nombre\n$raza\n$sexo\n$direccion\n$fechaNacimiento\n$color\n$caracteristicas",
                Toast.LENGTH_LONG
            ).show()

            // Aquí iría tu fetch/Volley/Retrofit para enviar a PHP
        }

        // Ajuste de insets (ya estaba en tu código original)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.scrollContenido)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
