package graysono.com.cp09progressdialogwebview.activities

import android.app.*
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.*
import com.squareup.picasso.Picasso
import graysono.com.cp09progressdialogwebview.R
import graysono.com.cp09progressdialogwebview.custom.CustomAlertDialog
import graysono.com.cp09progressdialogwebview.fragments.RateUsDialogFragment
import graysono.com.cp09progressdialogwebview.fragments.WebViewFragment
import graysono.com.cp09progressdialogwebview.helpers.Album
import graysono.com.cp09progressdialogwebview.interfaces.IDataReceived
import kotlinx.android.synthetic.main.content_details.*

class DetailsActivity : BaseActivity(), IDataReceived {
    private lateinit var album: Album
    lateinit var notificationManager : NotificationManager
    private lateinit var notificationChannel: NotificationChannel
    private lateinit var builder: Notification.Builder
    private val channelId = "package graysono.com.cp09progressdialogwebview.activities"
    private val description = "test notification"
    private lateinit var sharedPref: SharedPreferences


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

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
        var counter = 1
        val imageView = findViewById(R.id.fav) as ImageView
        val isNotification: Boolean? = sharedPref.getBoolean("notification_on", false)
        imageView.setOnClickListener {
            val intent = Intent(this,DetailsActivity::class.java)

            Log.d("Notifications", isNotification.toString())


            if (counter == 1) {
                imageView.setImageResource(R.drawable.ic_baseline_favorite_24)
                counter = 0
                if (isNotification!!)
                {
                    NotifOn()
                }
            } else {
                imageView.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                counter = 1
                if (isNotification!! && counter == 0)
                {
                    NotifOff()
                }
            }
        }
        btnAlbumUrl.setOnClickListener(WebViewButtonOnClickListener())
    }


    fun NotifOn()
    {
        val contentView = RemoteViews(packageName,R.layout.notification_layout)
        contentView.setTextViewText(R.id.tv_title,"Wishlist Notification")
        contentView.setTextViewText(R.id.tv_content,"An item has been added to your cart.")
        val pendingIntent = PendingIntent.getActivity(this, 0,intent,PendingIntent.FLAG_UPDATE_CURRENT)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = NotificationChannel(channelId,description,NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.BLUE
            notificationChannel.enableVibration(false)
            notificationManager.createNotificationChannel(notificationChannel)

            builder = Notification.Builder(this,channelId)
                .setContent(contentView)
                .setSmallIcon(R.drawable.ic_launcher_round)
                .setLargeIcon(BitmapFactory.decodeResource(this.resources,R.drawable.ic_launcher))
                .setContentIntent(pendingIntent)
        }else{
            builder = Notification.Builder(this)
                .setContent(contentView)
                .setSmallIcon(R.drawable.ic_launcher_round)
                .setLargeIcon(BitmapFactory.decodeResource(this.resources,R.drawable.ic_launcher))
                .setContentIntent(pendingIntent)
        }
        notificationManager.notify(1234,builder.build())
    }


    fun NotifOff()
    {
        val contentView2 = RemoteViews(packageName,R.layout.notification_layout)
        contentView2.setTextViewText(R.id.tv_title,"Wishlist Notification")
        contentView2.setTextViewText(R.id.tv_content,"An item has been removed from your cart.")
        val pendingIntent = PendingIntent.getActivity(this, 0,intent,PendingIntent.FLAG_UPDATE_CURRENT)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = NotificationChannel(channelId,description,NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.BLUE
            notificationChannel.enableVibration(false)
            notificationManager.createNotificationChannel(notificationChannel)

            builder = Notification.Builder(this,channelId)
                .setContent(contentView2)
                .setSmallIcon(R.drawable.ic_launcher_round)
                .setLargeIcon(BitmapFactory.decodeResource(this.resources,R.drawable.ic_launcher))
                .setContentIntent(pendingIntent)
        }else{
            builder = Notification.Builder(this)
                .setContent(contentView2)
                .setSmallIcon(R.drawable.ic_launcher_round)
                .setLargeIcon(BitmapFactory.decodeResource(this.resources,R.drawable.ic_launcher))
                .setContentIntent(pendingIntent)
        }
        notificationManager.notify(1234,builder.build())
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
            R.id.action_settings -> {
                startActivity(Intent(this@DetailsActivity, SettingsActivity::class.java))
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                true
            }
            R.id.action_privacy_policy -> {
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