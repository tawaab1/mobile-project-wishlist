package graysono.com.cp09progressdialogwebview.activities

import android.app.SearchManager
import android.app.SearchableInfo
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Menu
import android.widget.SearchView
import graysono.com.cp09progressdialogwebview.R

class SearchActivity : BaseActivity() {

    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        displayToolbar(true)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)

        val searchManager: SearchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchableInfo: SearchableInfo = searchManager.getSearchableInfo(componentName)

        searchView = menu.findItem(R.id.action_search).actionView as SearchView
        searchView.setSearchableInfo(searchableInfo)
        searchView.isIconified = false

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val sharedPref: SharedPreferences =
                    PreferenceManager.getDefaultSharedPreferences(applicationContext)
                sharedPref.edit().putString(getString(R.string.album_query), query).apply()
                searchView.clearFocus()
                finish()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        searchView.setOnCloseListener {
            finish()
            false
        }

        return true
    }
}
