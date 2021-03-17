package com.example.firebase.fragments

import android.content.pm.PackageManager
import com.google.android.gms.maps.*
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.firebase.R
import com.google.android.gms.maps.model.*


class Home_fragment : Fragment(), OnMapReadyCallback,
        GoogleMap.OnCameraMoveListener,
        GoogleMap.OnCameraMoveStartedListener,
        GoogleMap.OnCameraIdleListener,
        GoogleMap.OnPolygonClickListener,
        GoogleMap.OnPolylineClickListener,
        GoogleMap.OnMarkerClickListener
{

    private lateinit var mMap: GoogleMap
    var tienePermisos = false
    companion object {
        var mapFragment : SupportMapFragment?=null
        val TAG: String = com.google.android.gms.maps.MapFragment::class.java.simpleName
        fun newInstance() = MapFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        var rootView = inflater.inflate(R.layout.fragment_home_fragment, container, false)
        mapFragment = childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment?.getMapAsync(this)
        solicitarPermisos()
        return rootView
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
        val context = activity?.applicationContext!!
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
            uiSettings.isMyLocationButtonEnabled = true
            uiSettings.isZoomControlsEnabled = true
        }
    }

    fun solicitarPermisos(){

        val context = activity?.applicationContext!!

        val permisosFineLocation = ContextCompat
                .checkSelfPermission(
                        context,
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                )
        val tienePermisos = permisosFineLocation == PackageManager.PERMISSION_GRANTED
        if(tienePermisos) {
            Log.i("mapa", "Tiene permisos Fine Location")
            this.tienePermisos = true
        } else {
            getActivity()?.let {
                ActivityCompat.requestPermissions(
                        it,
                        arrayOf(
                                android.Manifest.permission.ACCESS_FINE_LOCATION
                        ),1
                )
            }
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