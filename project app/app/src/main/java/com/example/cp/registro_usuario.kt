
package com.example.cp

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.json.JSONObject
import java.io.*
import java.net.HttpURLConnection
import java.net.URL

class registro_usuario : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro_usuario)

        // ----- Vincular vistas -----
        val edtNombreUsuario = findViewById<EditText>(R.id.edtNombreUsuario)
        val edtNombre = findViewById<EditText>(R.id.edtNombre)
        val edtApellido = findViewById<EditText>(R.id.edtApellido)
        val edtEmail = findViewById<EditText>(R.id.edtEmail)
        val edtPassword = findViewById<EditText>(R.id.edtPassword)
        val btnRegistrar = findViewById<Button>(R.id.btnRegistrar)

        // ----- Acción del botón -----
        btnRegistrar.setOnClickListener {
            val nombreUsuario = edtNombreUsuario.text.toString().trim()
            val nombre = edtNombre.text.toString().trim()
            val apellido = edtApellido.text.toString().trim()
            val email = edtEmail.text.toString().trim()
            val contraseña = edtPassword.text.toString().trim()

            // Validación simple
            if (nombreUsuario.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || email.isEmpty() || contraseña.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Ejecutar registro en hilo aparte
            Thread {
                val respuesta = enviarRegistro(nombreUsuario, nombre, apellido, email, contraseña)

                runOnUiThread {
                    // Solo redirigir si es exitoso, sin Toast
                    if (respuesta.contains("Registro exitoso")) {
                        val intent = Intent(this, pag_entrada::class.java)
                        startActivity(intent)
                        finish() // Evita volver a registro al presionar "back"
                    }
                }

            }.start()
        }

        // Ajuste de insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // ----- Enviar datos al registro_usuario.php -----
    private fun enviarRegistro(
        nombreUsuario: String,
        nombre: String,
        apellido: String,
        email: String,
        contraseña: String
    ): String {
        return try {
            val url = URL("https://TU_DOMINIO/registro_usuario.php")  // <- Cambiar por tu URL real
            val conn = url.openConnection() as HttpURLConnection

            conn.requestMethod = "POST"
            conn.doOutput = true
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")

            val parametros = "nombre_usuario=$nombreUsuario&nombre=$nombre&apellido=$apellido&email=$email&contraseña=$contraseña"

            val output = BufferedWriter(OutputStreamWriter(conn.outputStream))
            output.write(parametros)
            output.flush()
            output.close()

            val input = BufferedReader(InputStreamReader(conn.inputStream))
            val respuesta = input.readText()
            input.close()

            // Parsear JSON desde PHP
            val json = JSONObject(respuesta)

            if (json.getBoolean("success")) {
                "Registro exitoso (ID = ${json.getInt("id_usuario")})"
            } else {
                json.getString("message")
            }

        } catch (e: Exception) {
            "Error de conexión: ${e.message}"
        }
    }
}
