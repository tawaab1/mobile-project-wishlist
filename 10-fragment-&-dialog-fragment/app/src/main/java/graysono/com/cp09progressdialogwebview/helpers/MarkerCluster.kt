package graysono.com.cp09progressdialogwebview.helpers

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener
import com.google.android.gms.maps.model.*
import com.google.maps.android.SphericalUtil
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager.OnClusterClickListener
import com.google.maps.android.ui.IconGenerator
import graysono.com.cp09progressdialogwebview.R

class MarkerCluster(
    private val context: Context,
    private val map: GoogleMap,
    clusterManager: ClusterManager<MapsStudio>
) :
    DefaultClusterRenderer<MapsStudio>(context, map, clusterManager),
    OnClusterClickListener<MapsStudio>,
    OnInfoWindowClickListener {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)
    private val iconGen = IconGenerator(context)
    private val item: View = layoutInflater.inflate(R.layout.custom_single_marker, null)

    init {
        val drawable: Drawable? = ContextCompat.getDrawable(context, android.R.color.transparent)
        iconGen.setBackground(drawable)
        iconGen.setContentView(item)
        clusterManager.setOnClusterClickListener(this)
        map.setInfoWindowAdapter(clusterManager.markerManager)
        map.setOnInfoWindowClickListener(this)
        clusterManager.markerCollection.setOnInfoWindowAdapter(CustomInfoWindowAdapter())
        map.setOnCameraIdleListener(clusterManager)
        map.setOnMarkerClickListener(clusterManager)
    }

    override fun onBeforeClusterItemRendered(item:MapsStudio, markerOptions: MarkerOptions) {
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
    }

    override fun onBeforeClusterRendered(
        cluster: Cluster<MapsStudio>,
        markerOptions: MarkerOptions
    ) {
        val txvCluster: TextView = item.findViewById(R.id.txvCluster)
        txvCluster.text = cluster.size.toString()

        val icon: Bitmap = iconGen.makeIcon()
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon))
    }

    override fun onClusterItemRendered(clusterItem: MapsStudio, marker: Marker) {
        marker.tag = clusterItem
    }

    override fun onClusterClick(cluster: Cluster<MapsStudio>): Boolean {
        val builder = LatLngBounds.Builder()
        for (data: MapsStudio in cluster.items)
            builder.include(data.position)

        try {
            map.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 100))
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return true
    }

    override fun onInfoWindowClick(marker: Marker) {}

    inner class CustomInfoWindowAdapter : GoogleMap.InfoWindowAdapter {
        private val item: View = layoutInflater.inflate(R.layout.custom_single_marker_info, null)

        override fun getInfoWindow(marker: Marker): View {
            val recordingStudio: MapsStudio = marker.tag as MapsStudio
            val txvItemName: TextView = item.findViewById(R.id.txvItemName)
            val txvItemWebsite: TextView = item.findViewById(R.id.txvItemWebsite)
            txvItemName.text = "Name: " + marker.title
            txvItemWebsite.text = "Website: " + recordingStudio.website
            return item
        }

        override fun getInfoContents(marker: Marker): View? {
            return null
        }
    }
}