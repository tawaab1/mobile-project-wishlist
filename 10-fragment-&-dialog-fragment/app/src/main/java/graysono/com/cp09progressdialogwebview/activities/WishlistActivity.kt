package graysono.com.cp09progressdialogwebview.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import graysono.com.cp09progressdialogwebview.R
import kotlinx.android.synthetic.main.activity_wishlist.*
import kotlinx.android.synthetic.main.content_main.*

class WishlistActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wishlist)

        bnv2.setOnNavigationItemSelectedListener(OnNavigationItemSelectedListener())
        bnv2.menu.getItem(1).isChecked = true
    }




    inner class OnNavigationItemSelectedListener :
        BottomNavigationView.OnNavigationItemSelectedListener {
        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            return when (item.itemId) {
                /**
                 * navigates to the MainActivity
                 */
                R.id.navigation_home -> {
                    startActivity(Intent(this@WishlistActivity, MainActivity::class.java))
                    finish()
                    true
                }
                /**
                 * navigates to the About Us activity
                 */
                R.id.navigation_profile ->{
                    startActivity(Intent(this@WishlistActivity, ProfileActivity::class.java))
                    finish()
                    true
                }
                R.id.navigation_wishlist -> {
                    startActivity(Intent(this@WishlistActivity, WishlistActivity::class.java))
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