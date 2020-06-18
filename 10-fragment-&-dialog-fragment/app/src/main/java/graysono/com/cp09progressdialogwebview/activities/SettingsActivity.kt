package graysono.com.cp09progressdialogwebview.activities

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import android.widget.Switch
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.FragmentManager
import graysono.com.cp09progressdialogwebview.R
import graysono.com.cp09progressdialogwebview.fragments.WebViewFragment
import kotlinx.android.synthetic.main.content_settings.*
import graysono.com.cp09progressdialogwebview.activities.DetailsActivity

class SettingsActivity : BaseActivity() {

    private lateinit var sharedPref: SharedPreferences
    private lateinit var details: DetailsActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        displayToolbar(true, isTitleEnabled = true)

        val switch = findViewById(R.id.switchSettingsFollowers) as Switch
        switch.setOnCheckedChangeListener(SwitchOnCheckChangeListener())

        sharedPref = PreferenceManager.getDefaultSharedPreferences(this@SettingsActivity)




        val notification: Boolean? = sharedPref.getBoolean("Notif on", false)
        switch.isChecked = notification == true
    }


    inner class SwitchOnCheckChangeListener : CompoundButton.OnCheckedChangeListener {
        override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
            when (buttonView.id) {
                R.id.switchSettingsFollowers -> {
                    val isNotifChecked: Boolean = isChecked
                    sharedPref.edit().putBoolean("Notif_on", isNotifChecked).apply()
                    details.NotifOn()
                }


            }
        }
    }
}
