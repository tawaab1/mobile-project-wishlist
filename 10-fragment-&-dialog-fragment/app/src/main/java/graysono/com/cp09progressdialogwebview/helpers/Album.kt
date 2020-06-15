package graysono.com.cp09progressdialogwebview.helpers

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Album(
    var name: String,
    var playCount: Int,
    var artist: String,
    var url: String,
    var image: String

): Parcelable