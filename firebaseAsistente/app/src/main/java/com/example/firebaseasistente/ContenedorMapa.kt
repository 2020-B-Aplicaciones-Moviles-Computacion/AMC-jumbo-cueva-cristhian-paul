package com.example.firebaseasistente

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*


class ContenedorMapa : AppCompatActivity(), OnMapReadyCallback,
        GoogleMap.OnCameraMoveListener,
        GoogleMap.OnCameraMoveStartedListener,
        GoogleMap.OnCameraIdleListener,
        GoogleMap.OnPolygonClickListener,
        GoogleMap.OnPolylineClickListener,
        GoogleMap.OnMarkerClickListener
{

    private lateinit var mMap: GoogleMap
    var tienePermisos = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contenedor_mapa)
        solicitarPermisos()
        val mapFramgent = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment;
        mapFramgent.getMapAsync(this);

        val botonCarolina = findViewById<Button>(R.id.btn_ir_carolina)
        botonCarolina
                .setOnClickListener {
                    val quicentro = LatLng(-0.176125, -78.480208)
                    val zoom = 17f
                    moverCamaraZoom(quicentro,zoom)
                }

    }

    override fun onMapReady(googleMap: GoogleMap?) {
        if (googleMap != null) {
            mMap = googleMap
            mMap
                    .setOnPolygonClickListener {
                        Log.i("mapa", "onPolygonClick")
                    }
            establecerConfiguracionMapa(googleMap)
            val quicentro = LatLng(-0.176125, -78.480208)
            val titulo = "Quicentro"
            val zoom = 17f
            anadirMarcador(quicentro,titulo)
            moverCamaraZoom(quicentro,zoom)

            val poliLinea = googleMap
                .addPolyline(
                    PolylineOptions()
                        .clickable(true)
                        .add(
                            LatLng(-0.17749829434929937, -78.48736461424016),
                            LatLng(-0.19346006481850117, -78.49197716525089),
                            LatLng(-0.1901220531830979, -78.49397998345293)

                        )
                )

            poliLinea.tag = "Linea-1"

            val poligonoUno = googleMap
                .addPolygon(
                    PolygonOptions()
                        .clickable(true)
                        .add(
                            LatLng(-0.18625129772976323, -78.47935270959165),
                            LatLng(-0.17487878822058517, -78.48501753492017),
                            LatLng(-0.17598101505116123, -78.49834976862098)

                        )
                )

            poligonoUno.fillColor = -0xc771c4
            poligonoUno.tag = "poligono-2"

        }
    }

    fun moverCamaraZoom(latLng: LatLng, zoom: Float = 10f) {
        mMap.moveCamera(
                CameraUpdateFactory.newLatLngZoom(latLng, zoom)

        )
    }

    fun anadirMarcador (latLng: LatLng, title: String) {
        mMap.addMarker(
                MarkerOptions()
                        .position(latLng)
                        .title(title)
        )
    }
    fun establecerConfiguracionMapa(mapa: GoogleMap) {
        val context = this.applicationContext
        with(mapa) {
            val permisosFineLocation = ContextCompat
                    .checkSelfPermission(
                            context,
                            android.Manifest.permission.ACCESS_FINE_LOCATION
                    )
            val tienePermisos = permisosFineLocation == PackageManager.PERMISSION_GRANTED
            if(tienePermisos) {
                mapa.isMyLocationEnabled = true
            }
            uiSettings.isZoomControlsEnabled =true
            uiSettings.isMyLocationButtonEnabled = true
        }
    }

    fun solicitarPermisos(){

        val permisosFineLocation = ContextCompat
                .checkSelfPermission(
                        this.applicationContext,
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                )
        val tienePermisos = permisosFineLocation == PackageManager.PERMISSION_GRANTED
        if(tienePermisos) {
            Log.i("mapa", "Tiene permisos Fine Location")
            this.tienePermisos = true
        } else {
            ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                            android.Manifest.permission.ACCESS_FINE_LOCATION
                    ),1
            )
        }

    }

    override fun onCameraMove() {
        Log.i("mapa", "On Camera Move")
    }

    override fun onCameraMoveStarted(p0: Int) {
        Log.i("mapa", "onCameraMoveStarted")
        TODO("Not yet implemented")
    }

    override fun onCameraIdle() {
        Log.i("mapa", "onCameraIdle")
        TODO("Not yet implemented")
    }

    override fun onPolygonClick(p0: Polygon?) {
        Log.i("mapa", "onPolygonClick")

        TODO("Not yet implemented")
    }

    override fun onPolylineClick(p0: Polyline?) {
        Log.i("mapa", "onPolylineClick")

        TODO("Not yet implemented")
    }

    override fun onMarkerClick(p0: Marker?): Boolean {
        Log.i("mapa", "onMarkerClick")

        TODO("Not yet implemented")
    }
}