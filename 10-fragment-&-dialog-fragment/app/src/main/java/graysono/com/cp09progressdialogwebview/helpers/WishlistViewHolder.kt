package graysono.com.cp09progressdialogwebview.helpers

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import graysono.com.cp09progressdialogwebview.R

class WishlistViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    var txvName: TextView = view.findViewById(R.id.txvWishlistName)
    var txvDateTime: TextView = view.findViewById(R.id.txvWishlistDateTime)
    var imgBtnMenu: ImageButton = view.findViewById(R.id.imgBtnWishlistMenu)
    var txvPurchased: TextView = view.findViewById(R.id.txvWishlistPurchased)
}