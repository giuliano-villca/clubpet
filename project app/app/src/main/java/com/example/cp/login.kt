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

class login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        // ----- Vincular vistas -----
        val edtEmail = findViewById<EditText>(R.id.editTextEmail)
        val edtPassword = findViewById<EditText>(R.id.editTextPassword)
        val btnLogin = findViewById<Button>(R.id.buttonLogin)

        // ----- Acción del botón -----
        btnLogin.setOnClickListener {
            val email = edtEmail.text.toString().trim()
            val password = edtPassword.text.toString().trim()

            // Validación simple
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Ejecutar login en hilo aparte
            Thread {
                val respuesta = enviarLogin(email, password)

                runOnUiThread {
                    try {
                        val json = JSONObject(respuesta)
                        if (json.getBoolean("success")) {
                            // Login correcto: abrir pag_entrada
                            val intent = Intent(this, pag_entrada::class.java)
                            startActivity(intent)
                            finish() // opcional: cerrar la actividad de login
                        } else {
                            // Login fallido: mostrar mensaje
                            Toast.makeText(this, json.getString("message"), Toast.LENGTH_LONG).show()
                        }
                    } catch (e: Exception) {
                        // Error de conexión o parsing
                        Toast.makeText(this, "Error: $respuesta", Toast.LENGTH_LONG).show()
                    }
                }

            }.start()
        }

        // Ajuste insets del layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layout_login)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // ----- Enviar datos al login.php -----
    private fun enviarLogin(email: String, contraseña: String): String {
        return try {
            val url = URL("https://TU_DOMINIO/login.php")  // <- cámbialo por tu URL real
            val conn = url.openConnection() as HttpURLConnection

            conn.requestMethod = "POST"
            conn.doOutput = true
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")

            val parametros = "nombre_usuario=$email&contraseña=$contraseña"

            val output = BufferedWriter(OutputStreamWriter(conn.outputStream))
            output.write(parametros)
            output.flush()
            output.close()

            val input = BufferedReader(InputStreamReader(conn.inputStream))
            val respuesta = input.readText()
            input.close()

            respuesta
        } catch (e: Exception) {
            "{\"success\":false,\"message\":\"Error de conexión: ${e.message}\"}"
        }
    }
}
