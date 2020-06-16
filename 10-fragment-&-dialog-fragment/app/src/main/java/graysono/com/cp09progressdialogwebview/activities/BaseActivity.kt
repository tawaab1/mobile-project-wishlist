package graysono.com.cp09progressdialogwebview.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_details.*

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {
    internal fun displayToolbar(isHomeEnabled: Boolean, isTitleEnabled: Boolean) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(isHomeEnabled)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayShowTitleEnabled(isTitleEnabled)
    }

    companion object {
        const val REQUEST_CODE_PERMISSION = 1
    }
}