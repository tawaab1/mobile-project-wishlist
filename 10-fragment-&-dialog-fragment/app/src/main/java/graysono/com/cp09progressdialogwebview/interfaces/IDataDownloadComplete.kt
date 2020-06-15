package graysono.com.cp09progressdialogwebview.interfaces

import graysono.com.cp09progressdialogwebview.enums.DownloadStatus

interface IDataDownloadComplete {
    fun onDownloadComplete(data: String, status: DownloadStatus)
}