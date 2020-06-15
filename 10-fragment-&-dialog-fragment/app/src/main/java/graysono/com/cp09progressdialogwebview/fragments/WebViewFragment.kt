package graysono.com.cp09progressdialogwebview.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.fragment.app.DialogFragment
import graysono.com.cp09progressdialogwebview.R
import graysono.com.cp09progressdialogwebview.custom.CustomProgressBar

class WebViewFragment(private val data: String?) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_web, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val wbv: WebView = view.findViewById(R.id.wbv)
        wbv.settings.javaScriptEnabled = true
        wbv.loadUrl(data)
    }
}