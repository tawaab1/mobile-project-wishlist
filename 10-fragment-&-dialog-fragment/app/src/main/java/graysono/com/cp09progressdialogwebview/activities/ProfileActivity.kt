package graysono.com.cp09progressdialogwebview.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.MenuItem
import android.widget.ImageButton
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import graysono.com.cp09progressdialogwebview.BuildConfig
import graysono.com.cp09progressdialogwebview.R
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.content_main.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class ProfileActivity : BaseActivity() {
    private lateinit var imgCapture: ImageCapture
    private lateinit var imvProfile: CircleImageView
    private lateinit var imgBtnProfile: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        bnv1.setOnNavigationItemSelectedListener(OnNavigationItemSelectedListener())
        bnv1.menu.getItem(1).isChecked = true

        imgCapture = ImageCapture(this@ProfileActivity)
        imvProfile = findViewById(R.id.imvProfile)

        Picasso.with(this@ProfileActivity).load(R.drawable.user_profile)
            .error(R.drawable.ic_image_black_48dp)
            .placeholder(R.drawable.user_profile)
            .into(imvProfile)
        imgBtnProfile = findViewById(R.id.imgBtnProfile)
        imgBtnProfile.setOnClickListener { openCamera() }

        requestPermissions()
    }



    private fun requestPermissions()
    {
        ActivityCompat.requestPermissions(
            this@ProfileActivity,
            arrayOf(
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ),
            REQUEST_CODE_PERMISSION
        )
    }

    private fun isPermissionGranted(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this@ProfileActivity,
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this@ProfileActivity,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED){
            return true
        }
        return false
    }
    object Image {
        private lateinit var name: String

        private val timeStamp: String
            get() {
                val outPattern = "yyyyMMddHHmmss"
                val outputFormat = SimpleDateFormat(outPattern, Locale.ENGLISH)
                val currentTime = Date()
                return outputFormat.format(currentTime)
            }
        fun create(): File {
            val rootPath: File =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val storageDir = File(rootPath, "camera")
            if(!storageDir.exists())
                storageDir.mkdirs()
            name = "img_${timeStamp}.jpg"
            return File(storageDir.path + File.separator + name)
        }

    }

    class ImageCapture(private val context: Context)
    {
        lateinit var imgFile: File
        lateinit var imgUri: Uri

        fun prepare(): Uri {
            imgFile = Image.create()
            imgUri = FileProvider.getUriForFile(
                context,
                BuildConfig.APPLICATION_ID + ".provider",
                imgFile
            )
            return imgUri
        }
    }

    private fun openCamera() {
        if (isPermissionGranted())
        {
            val uri: Uri = imgCapture.prepare()
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val newIntent: Intent = intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
            startActivityForResult(newIntent, REQUEST_CODE_PERMISSION)
        } else {
            requestPermissions()
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_CODE_PERMISSION && resultCode == Activity.RESULT_OK) {
            val filePath: String = imgCapture.imgFile.path
            val bitmap: Bitmap = BitmapProcessor.process(filePath)
            val bitmapDrawable: BitmapDrawable = BitmapProcessor.convertBitmapToDrawable(resources, bitmap)
            imvProfile.setImageDrawable(bitmapDrawable)

        }
    }

    object BitmapProcessor{
        fun process(imageFile: String): Bitmap {
            val photoBitmap: Bitmap = BitmapFactory.decodeFile(imageFile)
            return scale(photoBitmap)
        }

        private fun scale(bitmap: Bitmap): Bitmap {
            val matrix = Matrix()
            matrix.postRotate(90F)
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        }

        fun convertBitmapToDrawable(resources: Resources, bitmap: Bitmap): BitmapDrawable {
            return BitmapDrawable(resources, bitmap)
        }
    }

    inner class OnNavigationItemSelectedListener : BottomNavigationView.OnNavigationItemSelectedListener{
        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            return when (item.itemId){
                /**
                 * navigates to the MainActivity
                 */
                R.id.navigation_home -> {
                    startActivity(Intent(this@ProfileActivity, MainActivity::class.java))
                    finish()
                    true
                }
                /**
                 * navigates to the About Us activity
                 */
                R.id.navigation_profile ->{
                    startActivity(Intent(this@ProfileActivity, ProfileActivity::class.java))
                    finish()
                    true
                }
//                R.id.navigation_wishlist ->{
//                    startActivity(Intent(this@MainActivity, WishlistActivity::class.java))
//                    finish()
//                    true
//                }
//                R.id.navigation_map ->{
//                    startActivity(Intent(this@MainActivity, MapActivity::class.java))
//                    finish()
//                    true
//                }
                else -> onNavigationItemSelected(item)
            }

        }

    }


}