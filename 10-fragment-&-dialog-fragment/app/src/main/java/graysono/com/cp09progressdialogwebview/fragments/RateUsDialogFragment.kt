package graysono.com.cp09progressdialogwebview.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import graysono.com.cp09progressdialogwebview.R
import graysono.com.cp09progressdialogwebview.interfaces.IDataReceived
import kotlinx.android.synthetic.main.fragment_rate_us.*

class RateUsDialogFragment(private val listener: IDataReceived) : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        isCancelable = false
        return inflater.inflate(R.layout.fragment_rate_us, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnRateNow.isEnabled = false

        rtbRatingStars.setOnRatingBarChangeListener { _, rating, _ ->
            btnRateNow.isEnabled = rating > 0.0f
        }

        btnNoThanks.setOnClickListener { dismiss() }

        btnRateNow.setOnClickListener {
            listener.onDataReceived("Thank you! Your feedback is very helpful for us.")
            dismiss()
        }
    }
}
