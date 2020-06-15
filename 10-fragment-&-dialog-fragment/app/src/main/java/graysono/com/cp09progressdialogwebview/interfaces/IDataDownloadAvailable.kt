package graysono.com.cp09progressdialogwebview.interfaces

import graysono.com.cp09progressdialogwebview.helpers.Album

interface IDataDownloadAvailable {
    fun onDataAvailable(data: ArrayList<Album>)
    fun onError(e: Exception)
}