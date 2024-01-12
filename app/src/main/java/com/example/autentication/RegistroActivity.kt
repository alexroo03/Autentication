package com.example.autentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.autentication.databinding.ActivityRegistroBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegistroActivity : AppCompatActivity(){
    lateinit var binding: ActivityRegistroBinding
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val db = FirebaseFirestore.getInstance()

        binding.registrarse.setOnClickListener{
            if(binding.nombre.text.isNotEmpty() && binding.apellidos.text.isNotEmpty()
                && binding.correoE.text.isNotEmpty()
                && binding.contrasena.text.isNotEmpty()){
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                        binding.correoE.text.toString(), binding.contrasena.text.toString()
                    ).addOnCompleteListener {
                            //Si el usuario se ha registrado correctamente se muestra la pantalla de bienvenida
                            if(it.isSuccessful){
                                db.collection("usuarios").document(binding.correoE.text.toString())
                                    .set(mapOf(
                                        "nombre" to binding.nombre.text.toString(),
                                        "apellido" to binding.apellidos.text.toString()
                                    ))

                                startActivity(Intent(this, Bienvenida::class.java))
                                
                            } //Si no, mos avisa de un error
                            else{
                                Toast.makeText(this, "No se ha podido registrar el usuario", Toast.LENGTH_LONG).show()
                            }
                        }


            }
            else {
                Toast.makeText(this, "Algun campo esta vacio", Toast.LENGTH_LONG).show()
            }
        }
    }
}