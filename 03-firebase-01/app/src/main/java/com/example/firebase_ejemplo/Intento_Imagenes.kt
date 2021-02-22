package com.example.firebase_ejemplo

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream
import java.io.File
import java.net.URI

class Intento_Imagenes : AppCompatActivity() {

    lateinit var photofile: File
    var PhotoNombre = "img1.jpg"
    var imgUri: URI? = null
    var bitmapImagen: Bitmap?  = null
    var bitmapPhoto: Bitmap?  = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intento__imagenes)

        val btnImagen = findViewById<Button>(R.id.btn_obtenerImagen)
                .setOnClickListener { obtenerImagen() }

        val btnFoto = findViewById<Button>(R.id.btn_tomarFoto)
                .setOnClickListener { tomarfoto() }

        val btnSubirImagen = findViewById<Button>(R.id.btn_upload_imagen)
            .setOnClickListener { subirImagen() }

        val btnSubirFoto = findViewById<Button>(R.id.btn_subir_foto)
            .setOnClickListener { subirFoto() }

        val btnDescargar = findViewById<Button>(R.id.btn_descargar_img)
            .setOnClickListener { descargarImagen() }


    }

    fun obtenerImagen(){
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type = "image/*"
        startActivityForResult(intent, 104)
    }

    fun tomarfoto(){
        val intentFoto = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intentFoto, 103)
    }

    fun subirImagen() {
        val storage = Firebase.storage

        val storageRef = storage.reference

        // Create a reference to "imagen.jpg"
        val mountainsRef = storageRef.child("imagen.jpg")

        if(bitmapImagen != null) {
          //  imageView.isDrawingCacheEnabled = true
          //  imageView.buildDrawingCache()
            val bitmap = bitmapImagen as Bitmap
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val data = baos.toByteArray()

            var uploadTask = mountainsRef.putBytes(data)
            uploadTask
                .addOnSuccessListener {
                    Log.i("fire-img", "Imagen subida correctamente")
                }
                .addOnFailureListener {
                        exception -> Log.w("fire-img", "Error", exception)
                }
        }

    }
    fun subirFoto() {
        val storage = Firebase.storage

        val storageRef = storage.reference

        // Create a reference to "imagen.jpg"
        val mountainsRef = storageRef.child("foto.jpg")

        if(bitmapPhoto != null) {
            //  imageView.isDrawingCacheEnabled = true
            //  imageView.buildDrawingCache()
            val bitmap = bitmapPhoto as Bitmap
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val data = baos.toByteArray()

            var uploadTask = mountainsRef.putBytes(data)
            uploadTask
                .addOnSuccessListener {
                    Log.i("fire-img", "Foto subida correctamente")
                }
                .addOnFailureListener {
                        exception -> Log.w("fire-img", "Error", exception)
                }
        }

    }
    fun descargarImagen() {
        val storage = Firebase.storage

        val editTextNombreProducto = findViewById<EditText>(R.id.txt_edit_path)
        val storageRef = storage.reference
        var islandRef = storageRef.child(editTextNombreProducto.text.toString())



        val ONE_MEGABYTE: Long = 1024 * 1024
        islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener {
            val imagen=findViewById<ImageView>(R.id.im_mostrarImagen)
            val btmap: Bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
            imagen.setImageBitmap(btmap)
            Log.i("fire-img", "Imagen descargada correctamente")
        }.addOnFailureListener {
                exception -> Log.w("fire-img", "Error", exception)
        }

    }



    override fun onActivityResult(
        requestCode: Int,  // Codigo de petición  - Codigo: 102
        resultCode: Int,  //  Codigo de Resultado - RESULT_OK o RESULT_CANCELED
        data: Intent?  // Datos (opcionales)
    ) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            103 -> { // FOTOGRAFIA
                val takenimage = data?.extras?.get("data") as Bitmap

                val imagen=findViewById<ImageView>(R.id.im_mostrarImagen)
                    .setImageBitmap(takenimage)

                bitmapPhoto = takenimage
            }
            104 -> { // IMAGEN ALMACENADA
                var uri: Uri? = data?.data
                var imaguri: Uri? = uri
                /*if(imguri!=nu){

                }*/
                val imagen=findViewById<ImageView>(R.id.im_mostrarImagen)
                //imagen.setImageURI(imguri)
                val btmap: Bitmap =
                        MediaStore.Images.Media.getBitmap(this.contentResolver, imaguri)
                imagen.setImageBitmap(btmap)
                bitmapImagen = btmap
            }
        }
    }

}

