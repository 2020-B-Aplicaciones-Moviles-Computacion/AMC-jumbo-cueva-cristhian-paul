package com.example.api_universidad

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.activity_mapa.*
import java.net.URL


class MapaActivity :
    AppCompatActivity(),
    OnMapReadyCallback,
    GoogleMap.OnMapClickListener,
    GoogleMap.OnInfoWindowClickListener {

    private lateinit var mMap: GoogleMap
    private var tienePermisos = false
    private lateinit var posicionSeleccionada: LatLng
    private var latitud: Double = 0.0
    private var longitud: Double = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mapa)
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        solicitarPermisos()
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        btn_guardar_ubicacion
            .setOnClickListener {
                val intentRespuesta = Intent()
                intentRespuesta.putExtra("longitud", longitud)
                intentRespuesta.putExtra("latitud", latitud)
                Log.i("latitod", "$longitud, $latitud")
                setResult(
                    Activity.RESULT_OK,
                    intentRespuesta
                )
                finish()
            }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        establecerConfiguracionesMapa(mMap)
        establecerListenersMapa(mMap)
        // Permisos

        val latitudLocal = intent.getDoubleExtra("latitud", 0.0)
        val longitudLocal = intent.getDoubleExtra("longitud", 0.0)
        val urlImangen = intent.getStringExtra("urlImagen")
        val urlRedSocial = "https://www.facebook.com/${intent.getStringExtra("urlRedSocial")}"
        val ubicacion = LatLng(-0.3730, -78.4855)
        val zoom = 17f
        if (latitudLocal != 0.0 && longitudLocal != 0.0) {
            val ubicacionEstudiante = LatLng(latitudLocal, longitudLocal)
            aniadirMarcador(
                ubicacionEstudiante,
                "Ubicación",
                urlImangen,
                urlRedSocial
            )
            moverCamaraPorZoom(ubicacionEstudiante, zoom)
        } else {
            moverCamaraPorZoom(ubicacion, zoom)

        }

        /*
        *-0.3745154,-78.4862549
        * -0.3745154,-78.4867394
        * -0.3746781,-78.4868748
        * -0.3743955,-78.4861527
        * */
//        val polyline = googleMap
//            .addPolyline(
//                PolylineOptions()
//                    .add(
//                        LatLng(-0.3745154, -78.4862549),
//                        LatLng(-0.3745154, -78.4867394),
//                        LatLng(-0.3746781, -78.4868748),
//                        LatLng(-0.3743955, -78.4861527)
//                    )
//            )
//
//        val polygono = googleMap
//            .addPolygon(
//                PolygonOptions()
//                    .add(
//                        LatLng(-0.3745154, -78.4862549),
//                        LatLng(-0.3745154, -78.4867394),
//                        LatLng(-0.3746781, -78.4868748),
//                        LatLng(-0.3743955, -78.4861527)
//                    )
//            )
//        polygono.fillColor = -0xc771c4
//        mMap.addMarker(MarkerOptions().position(ubicacion).title("Mi ubicación"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(ubicacion))
    }


    fun aniadirMarcador(
        latLng: LatLng,
        titulo: String,
        urlImange: String = "",
        urlRedSocial: String = ""
    ) {
        runOnUiThread {
            try {
                val marcador = mMap.addMarker(
                    MarkerOptions()
                        .position(latLng)
                        .title(titulo)
                        .snippet(urlRedSocial)
                )
                if (urlImange != "") {
                    val url = URL(urlImange)
                    var biteMap = BitmapFactory.decodeStream(url.openConnection().getInputStream())
                    biteMap = Bitmap.createScaledBitmap(biteMap, 170, 180, false)
                    marcador
                        .setIcon(BitmapDescriptorFactory.fromBitmap(biteMap))
                }
            } catch (err: Exception) {
                Log.i("errorsito-rico", "$err")
            }
        }
    }


    fun moverCamaraPorZoom(latLng: LatLng, zoom: Float = 10f) {
        mMap.moveCamera(
            CameraUpdateFactory
                .newLatLngZoom(latLng, zoom)
        )

    }

    fun establecerConfiguracionesMapa(mapa: GoogleMap) {
        val contexto = this.applicationContext
        with(mapa) {
            val permisoFineLocation = ContextCompat
                .checkSelfPermission(
                    contexto,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                )
            val tienePermisos = permisoFineLocation == PackageManager.PERMISSION_GRANTED

            if (tienePermisos) {
                mapa.isMyLocationEnabled = true
            }
            uiSettings.isZoomControlsEnabled = true
            uiSettings.isMyLocationButtonEnabled = true
        }
    }

    fun solicitarPermisos() {
        val permisosFineLocation = ContextCompat
            .checkSelfPermission(
                this.applicationContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
        val tienePermisos = permisosFineLocation == PackageManager.PERMISSION_GRANTED
        if (tienePermisos) {
            Log.i("test-mapa", "Tiene permisos")
            this.tienePermisos = true
        } else {
            ActivityCompat
                .requestPermissions(
                    this,
                    arrayOf(
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                    ),
                    1 // codigo que esperamos
                )
            Log.i("test-mapa", "No tienes permisos papu")
            this.tienePermisos = false
        }
    }

    fun establecerListenersMapa(map: GoogleMap) {
        with(map) {
            setOnMapClickListener(this@MapaActivity)
            setOnInfoWindowClickListener(this@MapaActivity)
        }
    }

    override fun onMapClick(latLng: LatLng?) {
        mMap.clear()
        if (latLng != null) {
            latitud = latLng.latitude
            longitud = latLng.longitude
            aniadirMarcador(latLng, "Ubicación seleccionada")
        }
    }


//    override fun onMarkerClick(p0: Marker?): Boolean {
////        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(p0?.snippet))
////        startActivity(browserIntent)
//        return true
//    }

    override fun onInfoWindowClick(p0: Marker?) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(p0?.snippet))
        startActivity(browserIntent)
    }
}