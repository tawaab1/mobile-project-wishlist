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

        var counter = 1


        val imageView = findViewById(R.id.fav) as ImageView
        imageView.setOnClickListener {
            if (counter == 1) {
                imageView.setImageResource(R.drawable.ic_baseline_favorite_24)
                counter = 0
            } else {
                imageView.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                counter = 1
            }
        }


        btnAlbumUrl.setOnClickListener(WebViewButtonOnClickListener())
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




    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true

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