package com.example.firebase.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import com.google.android.gms.maps.*
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.contentValuesOf
import androidx.fragment.app.Fragment
import com.example.firebase.R
import com.example.firebase.models.NegocioData
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class Home_fragment : Fragment(), OnMapReadyCallback,
    GoogleMap.OnCameraMoveListener,
    GoogleMap.OnCameraMoveStartedListener,
    GoogleMap.OnCameraIdleListener,
    GoogleMap.OnPolygonClickListener,
    GoogleMap.OnPolylineClickListener,
    GoogleMap.OnMarkerClickListener
{

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var mDatabaseReference: DatabaseReference
    var tienePermisos = false
    var latitudActual:Double = 0.0
    var longituActual: Double = 0.0
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
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity?.applicationContext!!)
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("negocios")
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
    fun anadirTiendasMarcador () {
        mDatabaseReference.get().addOnSuccessListener {
            it.children.forEach{
                Log.i("data", "primer negociod ${it.value}")
                val negocio = it.getValue(NegocioData::class.java)
                Log.i("data", "primer negociod ${negocio?.nombre}")
                val ubicacion = LatLng(negocio?.lat.toString().toDouble(), negocio?.long.toString().toDouble())
                Log.i("data", "primer negociod ${ubicacion}")
                mMap.addMarker(
                    MarkerOptions()
                        .position(ubicacion)
                        .title(negocio?.nombre)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                )
            }
        }.addOnFailureListener{
            Log.e("data", "Error getting data", it)
        }

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
            getLastKnownLocation(activity?.applicationContext!!)
            Log.i("data","adsfd${longituActual}")
            Log.i("data","adfdf${latitudActual}")
            val titulo = "Estas Aqui"
            val zoom = 11f
            val ubicacion = LatLng(latitudActual,longituActual)
            moverCamaraZoom(ubicacion, zoom)
            anadirMarcador(ubicacion, titulo)
            anadirTiendasMarcador()
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
    public fun getLastKnownLocation(context: Context) {
        val locationManager: LocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val providers: List<String> = locationManager.getProviders(true)
        var location: Location? = null
        for (i in providers.size - 1 downTo 0) {
            if (ActivityCompat.checkSelfPermission(
                    activity?.applicationContext!!,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    activity?.applicationContext!!,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            location= locationManager.getLastKnownLocation(providers[i])
            if (location != null)
                break
        }
        val gps = DoubleArray(2)
        if (location != null) {
            gps[0] = location.getLatitude()
            gps[1] = location.getLongitude()
            latitudActual = location.getLatitude() as Double
            longituActual = location.getLongitude() as Double
            Log.i("data",gps[0].toString())
            Log.i("data",gps[1].toString())

        }

    }

}