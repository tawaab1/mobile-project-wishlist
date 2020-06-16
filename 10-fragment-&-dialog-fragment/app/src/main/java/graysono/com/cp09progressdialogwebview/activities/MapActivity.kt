package graysono.com.cp09progressdialogwebview.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import graysono.com.cp09progressdialogwebview.R
import kotlinx.android.synthetic.main.activity_map.*
import kotlinx.android.synthetic.main.activity_wishlist.*

class MapActivity : BaseActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        bnv4.setOnNavigationItemSelectedListener(OnNavigationItemSelectedListener())
        bnv4.menu.getItem(1).isChecked = true
    }




    inner class OnNavigationItemSelectedListener :
        BottomNavigationView.OnNavigationItemSelectedListener {
        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            return when (item.itemId) {
                /**
                 * navigates to the MainActivity
                 */
                R.id.navigation_home -> {
                    startActivity(Intent(this@MapActivity, MainActivity::class.java))
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
                    finish()
                    true
                }
                /**
                 * navigates to the About Us activity
                 */
                R.id.navigation_profile ->{
                    startActivity(Intent(this@MapActivity, ProfileActivity::class.java))
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
                    finish()
                    true
                }
                R.id.navigation_wishlist -> {
                    startActivity(Intent(this@MapActivity, WishlistActivity::class.java))
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
                    finish()
                    true
                }
                R.id.navigation_map ->{
                    startActivity(Intent(this@MapActivity, MapActivity::class.java))
                    finish()
                    true
                }
                else -> onNavigationItemSelected(item)
            }
        }
    }
}