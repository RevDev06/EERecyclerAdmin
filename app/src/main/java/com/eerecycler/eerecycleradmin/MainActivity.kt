package com.eerecycler.eerecycleradmin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private lateinit var etPet: EditText
    private lateinit var etCarton: EditText
    private lateinit var etBoteAluminio: EditText
    private lateinit var etCobre: EditText
    private lateinit var etChatarra: EditText
    private lateinit var etRadiador: EditText
    private lateinit var etBronce: EditText
    private lateinit var etPlastico: EditText
    private lateinit var etAntimonio: EditText
    private lateinit var etAluminioGrueso: EditText
    private lateinit var etArchivo: EditText
    private lateinit var etChilero: EditText
    private lateinit var etBaterias: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference.child("precios")

        etPet = findViewById(R.id.etPet)
        etCarton = findViewById(R.id.etCarton)
        etBoteAluminio = findViewById(R.id.etBoteAluminio)
        etCobre = findViewById(R.id.etCobre)
        etChatarra = findViewById(R.id.etChatarra)
        etRadiador = findViewById(R.id.etRadiador)
        etBronce = findViewById(R.id.etBronce)
        etPlastico = findViewById(R.id.etPlastico)
        etAntimonio = findViewById(R.id.etAntimonio)
        etAluminioGrueso = findViewById(R.id.etAluminioGrueso)
        etArchivo = findViewById(R.id.etArchivo)
        etChilero = findViewById(R.id.etChilero)
        etBaterias = findViewById(R.id.etBaterias)

        val btnActualizar = findViewById<Button>(R.id.btnActualizar)
        val btnCerrarSesion = findViewById<Button>(R.id.btnCerrarSesion)


        cargarPreciosActuales()


        btnActualizar.setOnClickListener {
            guardarPrecios()
        }

        btnCerrarSesion.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun cargarPreciosActuales() {
        database.get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                etPet.setText(snapshot.child("pet").value?.toString() ?: "")
                etCarton.setText(snapshot.child("carton").value?.toString() ?: "")
                etBoteAluminio.setText(snapshot.child("boteAluminio").value?.toString() ?: "")
                etCobre.setText(snapshot.child("cobre").value?.toString() ?: "")
                etChatarra.setText(snapshot.child("chatarra").value?.toString() ?: "")
                etRadiador.setText(snapshot.child("radiador").value?.toString() ?: "")
                etBronce.setText(snapshot.child("bronce").value?.toString() ?: "")
                etPlastico.setText(snapshot.child("plastico").value?.toString() ?: "")
                etAntimonio.setText(snapshot.child("antimonio").value?.toString() ?: "")
                etAluminioGrueso.setText(snapshot.child("aluminioGrueso").value?.toString() ?: "")
                etArchivo.setText(snapshot.child("archivo").value?.toString() ?: "")
                etChilero.setText(snapshot.child("chilero").value?.toString() ?: "")
                etBaterias.setText(snapshot.child("baterias").value?.toString() ?: "")
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Error al cargar datos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun guardarPrecios() {
        try {
            val preciosMap = mapOf(
                "pet" to (etPet.text.toString().toDoubleOrNull() ?: 0.0),
                "carton" to (etCarton.text.toString().toDoubleOrNull() ?: 0.0),
                "boteAluminio" to (etBoteAluminio.text.toString().toDoubleOrNull() ?: 0.0),
                "cobre" to (etCobre.text.toString().toDoubleOrNull() ?: 0.0),
                "chatarra" to (etChatarra.text.toString().toDoubleOrNull() ?: 0.0),
                "radiador" to (etRadiador.text.toString().toDoubleOrNull() ?: 0.0),
                "bronce" to (etBronce.text.toString().toDoubleOrNull() ?: 0.0),
                "plastico" to (etPlastico.text.toString().toDoubleOrNull() ?: 0.0),
                "antimonio" to (etAntimonio.text.toString().toDoubleOrNull() ?: 0.0),
                "aluminioGrueso" to (etAluminioGrueso.text.toString().toDoubleOrNull() ?: 0.0),
                "archivo" to (etArchivo.text.toString().toDoubleOrNull() ?: 0.0),
                "chilero" to (etChilero.text.toString().toDoubleOrNull() ?: 0.0),
                "baterias" to (etBaterias.text.toString().toDoubleOrNull() ?: 0.0)
            )

            database.setValue(preciosMap).addOnSuccessListener {
                Toast.makeText(this, "¡Precios actualizados en la web!", Toast.LENGTH_LONG).show()
            }.addOnFailureListener {
                Toast.makeText(this, "Error al actualizar la base de datos", Toast.LENGTH_SHORT).show()
            }

        } catch (e: Exception) {
            Toast.makeText(this, "Por favor revisa que todos los campos sean válidos", Toast.LENGTH_LONG).show()
        }
    }
}