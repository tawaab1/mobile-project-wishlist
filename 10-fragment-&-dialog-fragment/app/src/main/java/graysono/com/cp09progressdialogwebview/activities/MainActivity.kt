package graysono.com.cp09progressdialogwebview.activities

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import graysono.com.cp09progressdialogwebview.R
import graysono.com.cp09progressdialogwebview.custom.CustomAlertDialog
import graysono.com.cp09progressdialogwebview.enums.DownloadStatus
import graysono.com.cp09progressdialogwebview.fragments.RateUsDialogFragment
import graysono.com.cp09progressdialogwebview.helpers.*
import graysono.com.cp09progressdialogwebview.interfaces.IDataDownloadAvailable
import graysono.com.cp09progressdialogwebview.interfaces.IDataDownloadComplete
import graysono.com.cp09progressdialogwebview.interfaces.IDataReceived
import graysono.com.cp09progressdialogwebview.interfaces.IRecyclerViewItem
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : BaseActivity(), IDataDownloadAvailable,
    IDataDownloadComplete, IRecyclerViewItem, IDataReceived {

    private lateinit var rawDataAsyncTask: RawDataAsyncTask
    private lateinit var lastFmRecyclerViewAdapter: LastFmRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        displayToolbar(false, isTitleEnabled = false)

        bnv.setOnNavigationItemSelectedListener(OnNavigationItemSelectedListener())
        bnv.menu.getItem(1).isChecked = true

        lastFmRecyclerViewAdapter = LastFmRecyclerViewAdapter(ArrayList())

        revAlbums.layoutManager = LinearLayoutManager(this@MainActivity)
        revAlbums.addOnItemTouchListener(
            RecyclerItemOnClickListener(
                this@MainActivity,
                revAlbums,
                this
            )
        )
        revAlbums.adapter = lastFmRecyclerViewAdapter

        val url: String = createURI(
            getString(R.string.base_url), getString(R.string.method),
            "cher", getString(R.string.api_key), getString(R.string.format)
        )
        rawDataAsyncTask = RawDataAsyncTask(this, this@MainActivity)
        rawDataAsyncTask.execute(url)
    }

    /*
       Transition Animation, fragment and activities

     */
    fun openProfileActivity(view: View?) {
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }
    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    private fun createURI(
        baseURL: String, method: String, artist: String,
        apiKey: String, format: String
    ): String {
        return Uri.parse(baseURL)
            .buildUpon()
            .appendQueryParameter("method", method)
            .appendQueryParameter("artist", artist)
            .appendQueryParameter("api_key", apiKey)
            .appendQueryParameter("format", format)
            .build().toString()
    }

    private fun showDialog() {
        val dialogFragment = RateUsDialogFragment(this)
        dialogFragment.show(supportFragmentManager, null)
    }

    override fun onResume() {
        super.onResume()
        val sharedPref: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val queryResult: String? = sharedPref.getString(getString(R.string.album_query), "")

        if (queryResult!!.isNotEmpty()) {
            val url: String = createURI(
                getString(R.string.base_url), getString(R.string.method),
                queryResult, getString(R.string.api_key), getString(R.string.format)
            )
            rawDataAsyncTask = RawDataAsyncTask(this, this@MainActivity)
            rawDataAsyncTask.execute(url)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_main -> {
                startActivity(Intent(this@MainActivity, SearchActivity::class.java))
                true
            }
            R.id.action_about -> {
                val aboutUsAlertDialog = CustomAlertDialog(this@MainActivity, R.layout.custom_about_us)
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
                startActivity(Intent(this@MainActivity, SettingsActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDataAvailable(data: ArrayList<Album>) {
        Log.d(getString(R.string.TAG), getString(R.string.on_data_available, data))
        lastFmRecyclerViewAdapter.loadNewData(data)
    }

    override fun onError(e: Exception) {
        Log.d(getString(R.string.TAG), getString(R.string.on_error, e.message))
    }

    override fun onDownloadComplete(data: String, status: DownloadStatus) {
        if (status == DownloadStatus.OK) {
            val lastFmAsyncTask = LastFmAsyncTask(this)
            lastFmAsyncTask.execute(data)
        }
    }

    override fun onDataReceived(data: String) {
        val inflater = layoutInflater
        val layout: View = inflater.inflate(
            R.layout.custom_toast,
            findViewById(R.id.layoutRateUs)
        )
        val toast = Toast(this@MainActivity)
        val toastTextView = layout.findViewById(R.id.txvRateUsToast) as TextView
        toastTextView.text = data
        toast.duration = Toast.LENGTH_LONG
        toast.view = layout
        toast.show()
    }

    override fun onItemClick(view: View, position: Int) {
        val album: Album? = lastFmRecyclerViewAdapter.getAlbum(position)
        if (album != null) {
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("album", album)
            startActivity(intent)
        }
    }

    /*
    exit alert dialog on back pressed, when user presses back button
    gives user option to to exit "yes" or stay "no"
    */
    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setMessage("Are You Sure You want To Exit")
            .setCancelable(false)
            .setPositiveButton("Yes", { dialog, id-> super@MainActivity.onBackPressed() })
            .setNegativeButton("No", null)
            .show()
    }

    inner class OnNavigationItemSelectedListener : BottomNavigationView.OnNavigationItemSelectedListener{
        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            return when (item.itemId){
                /**
                 * navigates to the MainActivity
                 */
                R.id.navigation_home -> {
                    startActivity(Intent(this@MainActivity, MainActivity::class.java))
                    finish()
                    true
                }
                /**
                 * navigates to the About Us activity
                 */
                R.id.navigation_profile ->{
                    startActivity(Intent(this@MainActivity, ProfileActivity::class.java))
                    finish()
                    true
                }
                R.id.navigation_wishlist ->{
                    startActivity(Intent(this@MainActivity, WishlistActivity::class.java))
                    finish()
                    true
                }
//                R.id.navigation_map ->{
//                    startActivity(Intent(this@MainActivity, MapActivity::class.java))
//                    finish()
//                    true
//                }
                else -> onNavigationItemSelected(item)
            }

        }

    }
}


