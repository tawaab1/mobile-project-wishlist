package graysono.com.cp09progressdialogwebview.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.fragment.app.DialogFragment
import graysono.com.cp09progressdialogwebview.R
import graysono.com.cp09progressdialogwebview.custom.CustomProgressBar

class PrivacyPolicyWebViewFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_privacy_policy, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val wbvPrivacy: WebView = view.findViewById(R.id.webPrivacy)
        wbvPrivacy.settings.javaScriptEnabled = true
        wbvPrivacy.loadUrl("www.google.com")
    }
}