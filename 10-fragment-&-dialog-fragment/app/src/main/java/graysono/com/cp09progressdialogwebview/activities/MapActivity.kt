package graysono.com.cp09progressdialogwebview.activities

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.preference.PreferenceManager
import android.provider.Settings
import android.view.MenuItem
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Transformations.map
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.maps.android.clustering.ClusterManager
import graysono.com.cp09progressdialogwebview.R
import kotlinx.android.synthetic.main.activity_map.*
import kotlinx.android.synthetic.main.activity_wishlist.*

class MapActivity : BaseActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var sharedPref: SharedPreferences
    private lateinit var fusedLocClient: FusedLocationProviderClient
    private lateinit var locReq: LocationRequest
    private lateinit var locCallback: LocationCallback
    private lateinit var clusterManager: ClusterManager<RecordingStudio>
    private lateinit var markerCluster: MarkerCluster
    private lateinit var customToast: CustomToast
    private lateinit var recordingStudioData: ArrayList<RecordingStudio>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        val mapFragment: SupportMapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this@MapActivity)

        bnv4.setOnNavigationItemSelectedListener(OnNavigationItemSelectedListener())
        bnv4.menu.getItem(1).isChecked = true

        sharedPref = PreferenceManager.getDefaultSharedPreferences(this@RecordingStudioActivity)
        fusedLocClient = LocationServices.getFusedLocationProviderClient(this@RecordingStudioActivity)
        locReq = LocationRequest()
        locCallback = LocationCallback()
        customToast = CustomToast(this@RecordingStudioActivity)
        getLastLocation()
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
        map = googleMap

        recordingStudioData = arrayListOf(
            // Recording studios in the state of California
            RecordingStudio("Sunset Sound", LatLng(34.0977818, -118.3349175), "https://www.sunsetsound.com"),
            RecordingStudio("MIX Recording Studio", LatLng(34.0628191, -118.281333), "https://mixrecordingstudio.com"),
            RecordingStudio("Silverlake Recording Studios", LatLng(34.089849, -118.281676), "http://silverlakerecordingstudios.com"),
            RecordingStudio("Studio City Sound", LatLng(34.1508032, -118.404879), "https://www.studiocitysound.com"),
            RecordingStudio("7th Street Sound", LatLng(34.0352197, -118.2295421), "N/A"),
            RecordingStudio("Prairie Sun Recording Studio", LatLng(38.3298996, -122.7287277), "https://www.prairiesun.com"),
            RecordingStudio("Pus Cavern Recording Studio", LatLng(38.649085, -121.357698), "https://www.puscavern.com"),
            RecordingStudio("The Press Recording Studio", LatLng(37.95287990000001, -121.2872891), "https://www.thepressrecordingstudio.com"),
            RecordingStudio("Red Eye Recording Studios", LatLng(37.3583901, -121.8448954), "https://www.redeyerecordingstudios.com"),
            RecordingStudio("Uprise Recording", LatLng(38.60463439999999, -121.463057), "https://upriserecording.com"),
            // Recording studios in the state of Florida
            RecordingStudio("Clear Track Recording Studios", LatLng(27.9631844, -82.7949981), "https://www.cleartrackstudios.com"),
            RecordingStudio("Phantom City Studio", LatLng(28.4320156, -81.5083995), "https://www.phantomcitystudio.com"),
            RecordingStudio("Phat Planet Recording Studios", LatLng(28.6005069, -81.4202577), "http://phatplanetstudios.com"),
            RecordingStudio("Trunoyz Recording Studios", LatLng(26.1939945, -80.2024307), "http://trunoyz.com"),
            RecordingStudio("Stay Tuned Studios", LatLng(30.18589890000001, -81.5707087), "http://www.staytunedstudios.com"),
            // Recording studios in the state of Illinois
            RecordingStudio("Eclipse Studios", LatLng(40.5470674, -88.99286719999999), "https://www.eclipsestudios.net"),
            RecordingStudio("Earhole Studios", LatLng(41.89067060000001, -87.62849519999999), "http://earholestudios.com"),
            RecordingStudio("Stonecutter Recording Studio", LatLng(41.858128, -87.640474), "http://www.stonecutterstudios.com"),
            RecordingStudio("Barker Recording Studio", LatLng(42.26636899999999, -89.057958), "https://barkerrecordingstudio.com"),
            RecordingStudio("Gremlen Recording Studios", LatLng(41.7586331, -88.3192086), "https://www.gremlenstudios.com")
        )

        clusterManager = ClusterManager(this@MapActivity, map)
        markerCluster = MarkerCluster(this@MapActivity, map, clusterManager)
        clusterManager.renderer = markerCluster
        clusterManager.addItems(recordingStudioData)
        clusterManager.cluster()

        val zoomLevel = 1f
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(recordingStudioData[0].latLng, zoomLevel))
        setMapStyle(map)

        val mapType: Int? = sharedPref.getInt("map_type", 0)
        setMapType(mapType)

        enableMyLocation()
    }

    private fun setMapStyle(map: GoogleMap) {
        val success: Boolean = when (sharedPref.getInt("map_style", 0)) {
            1 -> map.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    this,
                    com.google.android.gms.location.R.raw.map_style
                )
            )
            else -> map.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    this@MapActivity,
                    com.google.android.gms.location.R.raw.map_light_style
                )
            )
        }

        if (!success)
            customToast.show("Map style parsing failed.")

    }

    private fun setMapType(mapType: Int?): Unit =
        when (mapType) {
            1 -> map.mapType = GoogleMap.MAP_TYPE_SATELLITE
            else -> map.mapType = GoogleMap.MAP_TYPE_NORMAL
        }

    private fun isPermissionGranted(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this@MapActivity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this@MapActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun enableMyLocation() {
        if (isPermissionGranted()) {
            map.isMyLocationEnabled = true
        } else {
            ActivityCompat.requestPermissions(
                this@MapActivity,
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),
                REQUEST_LOCATION_PERMISSION
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                enableMyLocation()
            }
        }
    }

    private fun requestNewLocationData() {
        locReq.interval = 1
        locReq.fastestInterval = 1
        locReq.smallestDisplacement = 50F
        locReq.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                if (locationResult.locations.isNotEmpty()) {
                    val lastLoc: Location = locationResult.lastLocation
                    sharedPref.edit().putDouble("recording_lat", lastLoc.latitude).apply()
                    sharedPref.edit().putDouble("recording_lng", lastLoc.longitude).apply()
                }
            }
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun getLastLocation() {
        if (isPermissionGranted()) {
            if (isLocationEnabled()) {
                requestNewLocationData()
            } else {
                customToast.show("Location must be turned on.")
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            enableMyLocation()
        }
    }

    private fun startLocationUpdates() {
        fusedLocClient.requestLocationUpdates(
            locReq,
            locCallback,
            null
        )
    }

    private fun stopLocationUpdates() {
        fusedLocClient.removeLocationUpdates(locCallback)
    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }

    override fun onResume() {
        super.onResume()
        startLocationUpdates()
    }

    companion object {
        private const val REQUEST_LOCATION_PERMISSION = 1
    }
}