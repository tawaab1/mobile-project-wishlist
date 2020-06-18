package graysono.com.cp09progressdialogwebview.helpers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import graysono.com.cp09progressdialogwebview.R
import graysono.com.cp09progressdialogwebview.interfaces.IItemClick

class WishlistRecyclerViewAdapter(var listener: IItemClick, private var wishlists: ArrayList<Wishlist>) :
    RecyclerView.Adapter<WishlistViewHolder>() {

    override fun getItemCount(): Int {
        return if (wishlists.isNotEmpty()) wishlists.size else 0
    }

    fun notifyData(newWishlists: ArrayList<Wishlist>) {
        wishlists = newWishlists
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishlistViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.custom_wishlist_list_item, parent, false)
        return WishlistViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: WishlistViewHolder, position: Int) {
        val wishlist: Wishlist = wishlists[position]
        viewHolder.txvName.text = "Name: " + wishlist.name
        viewHolder.txvDateTime.text = "Date: " + DateTime.formatDateTime(wishlist.dateTime!!)
        viewHolder.txvPurchased.text = "Date: " + wishlist.purchased
        viewHolder.imgBtnMenu.setOnClickListener(MenuOnButtonClickListener(wishlist, viewHolder.imgBtnMenu))
    }

    inner class MenuOnButtonClickListener(var wishlist: Wishlist, var imgBtn: ImageButton) : View.OnClickListener {
        override fun onClick(v: View) {
            listener.onItemClick(wishlist, imgBtn)
        }
    }
}