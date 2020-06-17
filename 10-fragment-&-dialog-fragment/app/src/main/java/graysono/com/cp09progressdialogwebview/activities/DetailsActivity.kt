package graysono.com.cp09progressdialogwebview.activities

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.widget.PopupMenu
import com.squareup.picasso.Picasso
import graysono.com.cp09progressdialogwebview.R
import graysono.com.cp09progressdialogwebview.custom.CustomAlertDialog
import graysono.com.cp09progressdialogwebview.enums.DatabaseStatus
import graysono.com.cp09progressdialogwebview.fragments.RateUsDialogFragment
import graysono.com.cp09progressdialogwebview.fragments.WebViewFragment
import graysono.com.cp09progressdialogwebview.helpers.Album
import graysono.com.cp09progressdialogwebview.helpers.DBHelper
import graysono.com.cp09progressdialogwebview.helpers.Wishlist
import graysono.com.cp09progressdialogwebview.helpers.WishlistRecyclerViewAdapter
import graysono.com.cp09progressdialogwebview.interfaces.IDataReceived
import kotlinx.android.synthetic.main.content_details.*

class DetailsActivity : BaseActivity(), IDataReceived {
    private lateinit var album: Album
    private lateinit var wishlists: ArrayList<Wishlist>
    private lateinit var dbHelper: DBHelper
    private lateinit var wishlistRecyclerViewAdapter: WishlistRecyclerViewAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        displayToolbar(true, isTitleEnabled = false)

        album = intent.extras.getParcelable("album")

        txvAlbumName.text = getString(R.string.album_name, album.name)
        txvAlbumArtist.text = getString(R.string.artist_name, album.artist)
        txvAlbumPlayCount.text = getString(R.string.play_count, album.playCount.toString())
        Picasso.with(this@DetailsActivity).load(album.image)
            .error(R.drawable.ic_image_black_48dp)
            .placeholder(R.drawable.ic_image_black_48dp)
            .into(imvAlbumImage)

        val boolean = fav
        val imageView = findViewById(R.id.fav) as ImageView
//        imageView.setImageResource(R.drawable.ic_baseline_favorite_24)
        imageView.setOnClickListener {
            imageView.setImageResource(R.drawable.ic_baseline_favorite_24)
//            addNewWishlistDialog(DatabaseStatus.INSERT, 0, "")

        }

    }
    private fun addNewWishlistDialog(status: DatabaseStatus, id: Int, txt: String) {
        val dialog = Dialog(this@DetailsActivity, R.style.DialogFullScreen)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setContentView(R.layout.fragment_add_wishlist)

        val edtAddWishlist: EditText = dialog.findViewById(R.id.edtAddWishlist)
        val btnCloseWishlist: Button = dialog.findViewById(R.id.btnCloseWishlist)
        val btnAddWishlist: Button = dialog.findViewById(R.id.btnAddWishlist)
        val txvAddWishlist: TextView = dialog.findViewById(R.id.txvAddWishlist)

        if (status == DatabaseStatus.UPDATE) {
            edtAddWishlist.setText(txt)
            btnAddWishlist.text = "Update"
            txvAddWishlist.text = "Update Wishlist"
        } else {
            edtAddWishlist.setText("")
            btnAddWishlist.text = "Add"
            txvAddWishlist.text = "Add New Wishlist"
        }

        btnAddWishlist.setOnClickListener {
            if (status == DatabaseStatus.UPDATE) {
                dbHelper.update(id.toLong(), edtAddWishlist.text.toString().trim())
                readDatabase()
            } else if (status == DatabaseStatus.INSERT) {
                dbHelper.insert(edtAddWishlist.text.toString().trim())
                readDatabase()
            }
            dialog.dismiss()
        }

        btnCloseWishlist.setOnClickListener { dialog.dismiss() }

        dialog.show()
    }


    private fun readDatabase() {
        wishlists = dbHelper.selectAll()
        wishlistRecyclerViewAdapter.notifyData(wishlists)
    }




    private fun showDialog() {
        val dialogFragment = RateUsDialogFragment(this)
        dialogFragment.show(supportFragmentManager, null)
    }

    inner class WebViewButtonOnClickListener : View.OnClickListener {
        override fun onClick(view: View) {
            val wbvFrag = WebViewFragment(album.url)
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment, wbvFrag)
                .commit()
            btnAlbumUrl.isEnabled = false
        }
    }




    //fav.setImageResource(R.drawable.ic_baseline_favorite_24)

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_main -> {
                startActivity(Intent(this@DetailsActivity, SearchActivity::class.java))
                finish()
                true
            }
            R.id.action_about -> {
                val aboutUsAlertDialog = CustomAlertDialog(this@DetailsActivity, R.layout.custom_about_us)
                aboutUsAlertDialog.show(
                    R.string.builder_about_us_title
                )
                true
            }
            R.id.action_rate_us -> {
                showDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDataReceived(data: String) {
        val inflater = layoutInflater
        val layout: View = inflater.inflate(
            R.layout.custom_toast,
            findViewById(R.id.layoutRateUs)
        )
        val toast = Toast(this@DetailsActivity)
        val toastTextView = layout.findViewById(R.id.txvRateUsToast) as TextView
        toastTextView.text = data
        toast.duration = Toast.LENGTH_LONG
        toast.view = layout
        toast.show()
    }
}