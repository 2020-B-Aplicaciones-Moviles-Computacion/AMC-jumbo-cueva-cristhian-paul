package com.example.firebase_ejemplo

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.firebase_ejemplo.dto.FirestoreUsuarioDto
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    val CODIGO_INICIO_SESION = 102
    val textoNoLogeado = "Dale clic al boton ingresar"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val botonIngresar = findViewById<Button>(R.id.btn_ingresar)
        botonIngresar.setOnClickListener {
            solicitarIngresarAplicativo()
        }

        val botonSalir = findViewById<Button>(R.id.btn_salir)
                .setOnClickListener {
                    solicitarSalirAplicativo()
                    val texto = findViewById<TextView>(R.id.tv_roles)
                    texto.text = ""
                }


        val texto = findViewById<TextView>(R.id.textView)

        val instanciaAuth = FirebaseAuth.getInstance()
        if (instanciaAuth.currentUser != null){
            texto.text = "Bienvenido ${instanciaAuth.currentUser?.email}"
            setearUsuarioFirebase()
            mostrarbotonesOcultos()
        }
        else{
            texto.text = textoNoLogeado
        }

        val botonFirestore = findViewById<Button>(R.id.btn_firestore)
        botonFirestore.setOnClickListener { irActividad(BFirestore::class.java) }

//christian.alejandro98@hotmail.com
    }

    fun solicitarIngresarAplicativo(){

        val providers = arrayListOf(
                AuthUI.IdpConfig.EmailBuilder().build())

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setLogo(R.drawable.logo_background)
                        .setTosAndPrivacyPolicyUrls("https://example.com/terms.html",
                                "https://example.com/privacy.html")
                        .build(),
                CODIGO_INICIO_SESION)
    }

    fun solicitarSalirAplicativo(){
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener{
                    val texto = findViewById<TextView>(R.id.textView)
                    texto.text = textoNoLogeado

                    BAuthUsuario.usuario = null
                    mostrarbotonesOcultos()
                    Log.i("FIREBASE-login", "Salió del app")
                }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode){
            CODIGO_INICIO_SESION -> {
                if(resultCode == Activity.RESULT_OK){
                    val usuario = IdpResponse.fromResultIntent(data)

                    if (usuario?.isNewUser == true){
                        //logica para crear un nuevo usuario en nuestra colección

                        if (usuario.email != null){
                            val db = Firebase.firestore

                            val rolesUsuario = arrayListOf("usuario")
                            val nuevoUsuario = hashMapOf<String, Any>(
                                "roles" to rolesUsuario
                            )
                            val identificadorUsuario = usuario.email.toString()
                            db.collection("usuario")
                                    .document(identificadorUsuario)
                                    .set(nuevoUsuario)
                                    .addOnSuccessListener {

                                        setearUsuarioFirebase()
                                        mostrarbotonesOcultos()

                                        Log.i("firebase-firestore","Se creo")
                                    }
                                    .addOnFailureListener {
                                        Log.i("firebase-firestore","Fallo")
                                    }

                        }
                    }

                    val texto = findViewById<TextView>(R.id.textView)
                    texto.text = "Bienvenido ${usuario?.email}"

                    setearUsuarioFirebase()
                    mostrarbotonesOcultos()
                }
                else{
                    Log.i("FIREBASE-login", "El usuario canceló")
                }
            }

        }
    }



    fun setearUsuarioFirebase(){
        val instanciaAuth = FirebaseAuth.getInstance()
        val usuarioLocal = instanciaAuth.currentUser

        if(usuarioLocal != null ){
            if(usuarioLocal!=null){
                val usuarioFirebase = BUsuarioFirebase(
                    usuarioLocal.uid, usuarioLocal.email!!, null
                )
                BAuthUsuario.usuario = usuarioFirebase
                cargarRolesUsuario(usuarioFirebase.email)
            }
        }
    }

    fun cargarRolesUsuario(uid:String){
        val db = Firebase.firestore
        val referencia = db.collection("usuario").document(uid)

        referencia.get()
                .addOnSuccessListener {
                    Log.i("firebase-firestore","Datos ${it.data}")

                    val firestoreUsuario = it.toObject(FirestoreUsuarioDto::class.java)
                    BAuthUsuario.usuario?.roles = firestoreUsuario?.roles
                    mostrarRolesEnPantalla()
                }
                .addOnFailureListener {
                    Log.i("firebase-firestore","Fallo ccargar usuario")
                }
    }

    fun mostrarRolesEnPantalla(){
        var cadenaTExtoRoles = ""
        BAuthUsuario.usuario?.roles?.forEach{
            cadenaTExtoRoles = cadenaTExtoRoles + " " + it
        }
        val textoRoles = findViewById<TextView>(R.id.tv_roles)
        textoRoles.text = cadenaTExtoRoles
    }

    fun mostrarbotonesOcultos(){
        val botonEscondidoFirestonre = findViewById<Button>(R.id.btn_firestore)
        if(BAuthUsuario.usuario!= null){
            botonEscondidoFirestonre.visibility = View.VISIBLE
        }
        else{
            botonEscondidoFirestonre.visibility = View.INVISIBLE
        }
    }


    fun irActividad(
        clase: Class<*>,
        parametros: ArrayList<Pair<String, *>>? = null,
        codigo: Int? = null
    ) {
        val intentExplicito = Intent(
            this,
            clase
        )
        if (parametros != null) {
            parametros.forEach {
                val nombreVariable = it.first
                val valorVariable = it.second

                var tipoDato = false

                tipoDato = it.second is String // instanceOf()
                if (tipoDato == true) {
                    intentExplicito.putExtra(nombreVariable, valorVariable as String)
                }

                tipoDato = it.second is Int // instanceOf()
                if (tipoDato == true) {
                    intentExplicito.putExtra(nombreVariable, valorVariable as Int)
                }

                tipoDato = it.second is Parcelable // instanceOf()
                if (tipoDato == true) {
                    intentExplicito.putExtra(nombreVariable, valorVariable as Parcelable)
                }

            }
        }
        if (codigo != null) {
            startActivityForResult(intentExplicito, codigo)
        } else {
            startActivity(intentExplicito)
        }


    }

}