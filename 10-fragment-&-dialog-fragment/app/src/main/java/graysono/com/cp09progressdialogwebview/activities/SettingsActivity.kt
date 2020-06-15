package graysono.com.cp09progressdialogwebview.activities

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import graysono.com.cp09progressdialogwebview.R
import graysono.com.cp09progressdialogwebview.fragments.WebViewFragment
import kotlinx.android.synthetic.main.content_settings.*

class SettingsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        displayToolbar(true)
    }
}
