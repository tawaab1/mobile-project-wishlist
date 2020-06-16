package graysono.com.cp09progressdialogwebview.custom

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import graysono.com.cp09progressdialogwebview.R
import graysono.com.cp09progressdialogwebview.helpers.DBHelper
import graysono.com.cp09progressdialogwebview.helpers.Wishlist
import graysono.com.cp09progressdialogwebview.helpers.WishlistRecyclerViewAdapter

class CustomAlertDialog(private val context: Context, layout: Int) {
    private lateinit var alertDialog: AlertDialog
    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private val view: View = inflater.inflate(layout, null)
    private val builder = AlertDialog.Builder(context)

    fun show(title: Int) {
        builder.setTitle(title)
        builder.setIcon(R.drawable.ic_lastfm)
        alertDialog = builder.setView(view).create()
        builder.setPositiveButton(context.getString(R.string.builder_about_us_positive)) { _, _ -> dismiss() }
        alertDialog.setCanceledOnTouchOutside(true)
        builder.show()
    }


    fun show(title: Int, dbHelper: DBHelper, wishlist: Wishlist, wishlistRecyclerViewAdapter: WishlistRecyclerViewAdapter) {
        builder.setTitle(title)
        builder.setIcon(R.drawable.ic_lastfm)
        alertDialog = builder.setView(view).create()
        builder.setPositiveButton(context.getString(R.string.builder_about_us_positive)) { _, _ ->
            dbHelper.delete(wishlist.id.toLong())
            wishlistRecyclerViewAdapter.notifyData(dbHelper.selectAll())
        }
        builder.setNegativeButton(context.getString(R.string.builder_about_us_negative)) { _, _ -> dismiss() }
        alertDialog.setCanceledOnTouchOutside(true)
        builder.show()
    }

    private fun dismiss() {
        if (alertDialog.isShowing) alertDialog.dismiss()
    }
}
