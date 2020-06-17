package graysono.com.cp09progressdialogwebview.activities

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.content.res.Resources
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.preference.PreferenceManager
import android.provider.Settings
import android.view.MenuItem
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.maps.android.clustering.ClusterManager
import graysono.com.cp09progressdialogwebview.R
import graysono.com.cp09progressdialogwebview.helpers.MapsData
import kotlinx.android.synthetic.main.activity_map.*
import kotlinx.android.synthetic.main.activity_wishlist.*

class MapActivity : BaseActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
//    private lateinit var sharedPref: SharedPreferences
//    private lateinit var fusedLocClient: FusedLocationProviderClient
//    private lateinit var locReq: LocationRequest
//    private lateinit var locCallback: LocationCallback
//    private lateinit var clusterManager: ClusterManager<MapsData>
//    private lateinit var markerCluster: MarkerCluster
    //private lateinit var customToast: CustomToast
//    private lateinit var recordingStudioData: ArrayList<MapsData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        val mapFragment: SupportMapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

//        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
//        mapFragment.getMapAsync(this)

        bnv4.setOnNavigationItemSelectedListener(OnNavigationItemSelectedListener())
        bnv4.menu.getItem(1).isChecked = true

//        sharedPref = PreferenceManager.getDefaultSharedPreferences(this@MapActivity)
//        fusedLocClient = LocationServices.getFusedLocationProviderClient(this@MapActivity)
//        locReq = LocationRequest()
//        locCallback = LocationCallback()
        //customToast = CustomToast(this@MapActivity)
        //getLastLocation()
    }


    inner class OnNavigationItemSelectedListener :
        BottomNavigationView.OnNavigationItemSelectedListener {
        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            return when (item.itemId) {
                /**
                 * navigates to the MainActivity
                 */
                R.id.navigation_home -> {
                    startActivity(Intent(this@MapActivity, MainActivity::class.java))
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
                    finish()
                    true
                }
                /**
                 * navigates to the About Us activity
                 */
                R.id.navigation_profile ->{
                    startActivity(Intent(this@MapActivity, ProfileActivity::class.java))
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
                    finish()
                    true
                }
                R.id.navigation_wishlist -> {
                    startActivity(Intent(this@MapActivity, WishlistActivity::class.java))
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
                    finish()
                    true
                }
                R.id.navigation_map ->{
                    startActivity(Intent(this@MapActivity, MapActivity::class.java))
                    finish()
                    true
                }
                else -> onNavigationItemSelected(item)
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {

        val data: ArrayList<MapsData> = arrayListOf(
//            MapsData("Sunset Sound", LatLng(34.0977818, -118.3349175),R.drawable.user_profile),
//            MapsData("Sunset Sound", LatLng(34.0977818, -118.3349175),R.drawable.user_profile),
            MapsData("Sunset Sound", LatLng(34.0977818, -118.3349175),R.drawable.user_profile)


        )

        val zoomLevel = 15f
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(data[1].location,zoomLevel))

        for (d: MapsData in data){
            map.addMarker(
                MarkerOptions()
                    .position(d.location)
                    .title((d.name))
            )
            val overlaySize = 100f
            val googleOverLay: GroundOverlayOptions = GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromResource(d.drawable))
                .position(d.location,overlaySize)
            map.addGroundOverlay(googleOverLay)
        }

        setMapLongClick(map)
        setPointOfInterest(map)
        setMapStyle(map)
        enableMyLocation()
    }

    private fun setMapLongClick(map: GoogleMap){
        map.setOnMapClickListener { latLng ->
            val snippet: String =
                "Latitude" + latLng + " , " + "Longitude: " + latLng.longitude
            map.addMarker(
                MarkerOptions()
                    .position(latLng)
                    //.title(getString("Dropped Pin"))
                    .snippet(snippet)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
            )
        }
    }

    private fun setPointOfInterest(map: GoogleMap){
        map.setOnPoiClickListener() { poi ->
            val poiMarker: Marker = map.addMarker(
                MarkerOptions()
                    .position(poi.latLng)
                    .title(poi.name)
            )
            poiMarker.showInfoWindow()
        }
    }

    private fun setMapStyle(map:GoogleMap){
        try {
            val success: Boolean = map.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    this,
                    R.raw.map_light_style
                )
            )
            if (!success){
                Toast.makeText(this@MapActivity,"Error loading map style",Toast.LENGTH_LONG)
                    .show()
            }
        }catch (e: Resources.NotFoundException){
            Toast.makeText(this@MapActivity,"Map style not found",Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun isPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this@MapActivity,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE_PERMISSION){
            if(grantResults.contains(PackageManager.PERMISSION_GRANTED)){
                enableMyLocation()
            }
        }
    }

    private fun enableMyLocation(){
        if(isPermissionGranted()){
            map.isMyLocationEnabled = true
        }else{
            ActivityCompat.requestPermissions(
                this@MapActivity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE_PERMISSION
            )
        }
    }

}