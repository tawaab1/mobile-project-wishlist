package graysono.com.cp09progressdialogwebview.helpers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import graysono.com.cp09progressdialogwebview.R

class LastFmRecyclerViewAdapter(private var albums: ArrayList<Album>) :
    RecyclerView.Adapter<LastFmViewHolder>() {
    override fun getItemCount(): Int {
        return if (albums.isNotEmpty()) albums.size else 0
    }

    fun loadNewData(newAlbums: ArrayList<Album>) {
        albums = newAlbums
        notifyDataSetChanged()
    }

    fun getAlbum(position: Int): Album? {
        return if (albums.isNotEmpty()) albums[position] else null
    }

    override fun onBindViewHolder(viewHolder: LastFmViewHolder, position: Int) {
        val album: Album = albums[position]
        Picasso.with(viewHolder.imvAlbum.context)
            .load(album.image)
            .placeholder(R.drawable.ic_image_black_48dp)
            .error(R.drawable.ic_image_black_48dp)
            .into(viewHolder.imvAlbum)

        viewHolder.txvName.text = "Album Name: " + album.name
        viewHolder.txvPlayCount.text = "Play Count: " + album.playCount.toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LastFmViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.custom_list_item, parent, false)
        return LastFmViewHolder(view)
    }
}