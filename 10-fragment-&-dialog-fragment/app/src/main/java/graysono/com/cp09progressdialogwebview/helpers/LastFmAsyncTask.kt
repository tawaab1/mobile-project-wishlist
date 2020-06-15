package graysono.com.cp09progressdialogwebview.helpers

import android.os.AsyncTask
import graysono.com.cp09progressdialogwebview.interfaces.IDataDownloadAvailable
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

class LastFmAsyncTask(private val listener: IDataDownloadAvailable) :
    AsyncTask<String, Void, ArrayList<Album>>() {

    override fun onPostExecute(result: ArrayList<Album>) {
        super.onPostExecute(result)
        listener.onDataAvailable(result)
    }

    override fun doInBackground(vararg url: String?): ArrayList<Album> {
        val albums = ArrayList<Album>()
        try {
            val jsonData = JSONObject(url[0])
            val topAlbumsObj: JSONObject = jsonData.getJSONObject("topalbums")
            val albumItems: JSONArray = topAlbumsObj.getJSONArray("album")

            for (albumItem: Int in 0 until albumItems.length()) {
                val albumObj: JSONObject = albumItems.getJSONObject(albumItem)
                val albumName: String = albumObj.getString("name")
                val albumPlayCount: Int = albumObj.getInt("playcount")
                val albumUrl: String = albumObj.getString("url")
                val artist: JSONObject = albumObj.getJSONObject("artist")
                val artistName: String = artist.getString("name")
                val imageItems: JSONArray = albumObj.getJSONArray("image")
                val imageObj: JSONObject = imageItems.getJSONObject(3)
                val imageText: String = imageObj.getString("#text")
                if (imageText.isNotEmpty()) {
                    val album = Album(albumName, albumPlayCount, artistName, albumUrl, imageText)
                    albums.add(album)
                }
            }
        } catch (e: Exception) {
            cancel(true)
            listener.onError(e)
        }
        return albums
    }
}
