package graysono.com.cp09progressdialogwebview.interfaces

import android.view.View

interface IRecyclerViewItem {
    fun onItemClick(view: View, position: Int)
}