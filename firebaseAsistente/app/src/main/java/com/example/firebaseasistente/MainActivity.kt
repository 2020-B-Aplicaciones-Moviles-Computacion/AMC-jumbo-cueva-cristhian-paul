package com.example.firebaseasistente

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.firebaseasistente.dto.FirestoreUsuarioDto
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    val CODIGO_INICIO_SESION = 102
    val textoNoLoegeado = "Dale Clicl en el boton ingresar"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val botonIngresar = findViewById<Button>(R.id.btn_ingresar)
        botonIngresar.setOnClickListener {
            solicitarIngresarApp()
        }

        val botonSalir = findViewById<Button>(R.id.btn_salir
        )
        botonSalir.setOnClickListener {
            solicitarSalirApp()
        }

        val texto = findViewById<TextView>(R.id.textView)
        val instanciaAuth = FirebaseAuth.getInstance()
        if(instanciaAuth.currentUser != null){
            texto.text = "bienvenido ${instanciaAuth.currentUser?.email}"
            setearUsuarioFire()
            mostrarBotonesOcultos()
        }else {
            texto.text = textoNoLoegeado

        }

        val botoFirestore = findViewById<Button>(R.id.btn_firestore)
        botoFirestore.setOnClickListener {
            irActividad(
                BFirestore::class.java
            )
        }

        val botonMapa  = findViewById<Button>(R.id.btn_mapa)
        botonMapa.setOnClickListener {
            irActividad(
                ContenedorMapa::class.java
            )
        }

    }


    fun solicitarSalirApp() {
        // [START auth_fui_signout]
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                val texto = findViewById<TextView>(R.id.textView)
                texto.text = textoNoLoegeado

                BAuthUsuario.usuario = null
                mostrarBotonesOcultos()
                Log.i("login", "salio de la app")
            }
        // [END auth_fui_signout]
    }
    fun solicitarIngresarApp() {
        // [START auth_fui_create_intent]
        // Choose authentication providers
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build())

        // [START auth_fui_theme_logo]
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setLogo(R.drawable.programmer) // Set logo drawable
                .setTheme(R.style.MySuperAppTheme) // Set theme
                .setTosAndPrivacyPolicyUrls(
                    "https://example.com/terms.html",
                    "https://example.com/privacy.html")
                .build(),
            RC_SIGN_IN)
        // [END auth_fui_theme_logo]
    }

    // [START auth_fui_result]
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (requestCode == RC_SIGN_IN) {
//            val response = IdpResponse.fromResultIntent(data)
//
//            if (resultCode == Activity.RESULT_OK) {
//                // Successfully signed in
//                val user = FirebaseAuth.getInstance().currentUser
//                // ...
//            } else {
//                // Sign in failed. If response is null the user canceled the
//                // sign-in flow using the back button. Otherwise check
//                // response.getError().getErrorCode() and handle the error.
//                // ...
//            }
//        }
//    }
    // [END auth_fui_result]

    private fun signOut() {
        // [START auth_fui_signout]
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                // ...
            }
        // [END auth_fui_signout]
    }

    private fun delete() {
        // [START auth_fui_delete]
        AuthUI.getInstance()
            .delete(this)
            .addOnCompleteListener {
                // ...
            }
        // [END auth_fui_delete]
    }

    private fun themeAndLogo() {
        val providers = emptyList<AuthUI.IdpConfig>()

        // [START auth_fui_theme_logo]
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setLogo(R.drawable.programmer) // Set logo drawable
                .setTheme(R.style.MySuperAppTheme) // Set theme
                .build(),
            RC_SIGN_IN)
        // [END auth_fui_theme_logo]
    }

    private fun privacyAndTerms() {
        val providers = emptyList<AuthUI.IdpConfig>()
        // [START auth_fui_pp_tos]
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setTosAndPrivacyPolicyUrls(
                    "https://example.com/terms.html",
                    "https://example.com/privacy.html")
                .build(),
            RC_SIGN_IN)
        // [END auth_fui_pp_tos]
    }

    override  fun onActivityResult (
        requestCode: Int, resultCode: Int, data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            CODIGO_INICIO_SESION -> {
                if (resultCode == Activity.RESULT_OK) {
                    val usuario = IdpResponse.fromResultIntent(data)
                    if(usuario?.isNewUser == true){
                        // Logica para crear el usuario en una coleción
                        if(usuario.email != null){
                            val db = Firebase.firestore
                            val rolesUsuario = arrayListOf("usuario")
                            val nuevoUsuario = hashMapOf<Any, Any>(
                                    "roles" to rolesUsuario
                            )
                            val identicadorUsuario = usuario.email.toString()
                            db.collection("usuario")
                                    .document(identicadorUsuario)
                                    .set(nuevoUsuario)
                                    .addOnSuccessListener {
                                        setearUsuarioFire()
                                        mostrarBotonesOcultos()
                                        BAuthUsuario.usuario?.roles = rolesUsuario
                                        Log.i("firestore", "Se creo")
                                    }
                                    .addOnFailureListener {
                                        Log.i("firestore", "Fallo")

                                    }
                        }
                    }
                    val texto = findViewById<TextView>(R.id.textView)
                    texto.text = "Bienvenido ${usuario?.email}"
                    setearUsuarioFire()
                    mostrarBotonesOcultos()

                } else {
                    Log.i("login", "el usuario cancelo")
                }
            }
        }
    }

    fun setearUsuarioFire () {
        val instanciaAuth = FirebaseAuth.getInstance()
        val usuarioLocal = instanciaAuth.currentUser

        if(usuarioLocal != null){
            if (usuarioLocal.email != null) {
                val usuarioFirebase = BUsuarioFirebase(
                    usuarioLocal.uid,
                    usuarioLocal.email!!,
                        null
                )
                BAuthUsuario.usuario = usuarioFirebase
                cargarRolesUsuarios(usuarioFirebase.email)
            }
        }
    }

    fun cargarRolesUsuarios(uid: String) {
        val db = Firebase.firestore


        val referencia = db
                .collection("usuario")
                .document(uid)
        referencia
                .get()
                .addOnSuccessListener {
                    Log.i("firestore", "Datos ${it.data}")
                    val firestoreUsuario = it.toObject(FirestoreUsuarioDto::class.java)
                    BAuthUsuario.usuario?.roles = firestoreUsuario?.roles
                    mostrarRolesEnPantalla()
                }
                .addOnFailureListener{
                    Log.i("firestore", "Fallo cargar usuario")
                }
    }
    fun mostrarBotonesOcultos() {
        val botonEscondigoFirestore = findViewById<Button>(R.id.btn_firestore)
        if ( BAuthUsuario.usuario != null) {
            botonEscondigoFirestore.visibility = View.VISIBLE
        } else {
            botonEscondigoFirestore.visibility = View.INVISIBLE
        }

    }

    fun mostrarRolesEnPantalla () {
        var cadenaTextoRoles = ""
        BAuthUsuario.usuario?.roles?.forEach{
            cadenaTextoRoles = cadenaTextoRoles + " " + it
        }
        val textoRoles = findViewById<TextView>(R.id.tv_roles)
        textoRoles.text = cadenaTextoRoles
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
        if (parametros != null){
            parametros.forEach{
                var nombreVariables = it.first
                var valorVariable =  it.second
                var tipoDatos = false

                tipoDatos = it.second is String // instnceOf()
                if(tipoDatos == true){

                    intentExplicito.putExtra(nombreVariables, valorVariable as String)
                }
                tipoDatos = it.second is Int // instnceOf()
                if(tipoDatos == true){

                    intentExplicito.putExtra(nombreVariables, valorVariable as Int)
                }
                tipoDatos = it.second is Parcelable // instnceOf()
                if(tipoDatos == true){

                    intentExplicito.putExtra(nombreVariables, valorVariable as Parcelable)
                }
            }
        }
        if (codigo != null) {
            startActivityForResult(intentExplicito,codigo)
        } else {
            startActivity(intentExplicito)
        }
        intentExplicito.putExtra("nombre", "Adrian")
        startActivity(intentExplicito)
    }

    companion object {

        private const val RC_SIGN_IN = 123
    }
}