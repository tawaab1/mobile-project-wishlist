package graysono.com.cp09progressdialogwebview.custom

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import graysono.com.cp09progressdialogwebview.R

class CustomProgressBar(context: Context) {
    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private val view: View = inflater.inflate(R.layout.custom_progress_bar, null)
    private val dialog = Dialog(context, R.style.CustomProgressBarTheme)

    fun show() {
        dialog.setContentView(view)
        dialog.show()
    }

    fun dismiss() {
        dialog.dismiss()
    }
}

