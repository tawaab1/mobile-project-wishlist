package graysono.com.cp09progressdialogwebview.helpers

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

class MapsStudio(
    var name: String,
    var latLng: LatLng,
    var website: String
) : ClusterItem {

    override fun getSnippet(): String {
        return ""
    }

    override fun getTitle(): String {
        return name
    }

    override fun getPosition(): LatLng {
        return latLng
    }
}