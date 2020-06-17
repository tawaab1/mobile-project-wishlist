package graysono.com.cp09progressdialogwebview.custom

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import graysono.com.cp09progressdialogwebview.R

class CustomToast(private val context: Context) {
    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private val view: View = inflater.inflate(R.layout.custom_toast, null)

    fun show(text: String) {
        val toast = Toast(context)
        val toastTextView = view.findViewById(R.id.txvCustomToast) as TextView
        toastTextView.text = text
        toast.duration = Toast.LENGTH_LONG
        toast.view = view
        toast.show()
    }
}