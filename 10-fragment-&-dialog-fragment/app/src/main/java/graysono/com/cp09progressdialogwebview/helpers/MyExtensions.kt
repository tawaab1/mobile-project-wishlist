package graysono.com.cp09progressdialogwebview.helpers

import android.content.SharedPreferences

fun SharedPreferences.Editor.putDouble(key: String, double: Double): SharedPreferences.Editor =
    putLong(key, java.lang.Double.doubleToRawLongBits(double))