package graysono.com.cp09progressdialogwebview.interfaces

import android.widget.ImageButton
import graysono.com.cp09progressdialogwebview.helpers.Wishlist

interface IItemClick {
    fun onItemClick(wishlist: Wishlist, imgBtn: ImageButton)
}