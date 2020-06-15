package graysono.com.cp09progressdialogwebview.helpers

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.hdodenhof.circleimageview.CircleImageView
import graysono.com.cp09progressdialogwebview.R

class LastFmViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var imvAlbum: CircleImageView = view.findViewById(R.id.imvAlbum)
    var txvName: TextView = view.findViewById(R.id.txvName)
    var txvPlayCount: TextView = view.findViewById(R.id.txvPlayCount)
}


