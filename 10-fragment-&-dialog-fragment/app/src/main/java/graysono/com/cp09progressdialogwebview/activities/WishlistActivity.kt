package graysono.com.cp09progressdialogwebview.activities

import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import graysono.com.cp09progressdialogwebview.R
import graysono.com.cp09progressdialogwebview.custom.CustomAlertDialog
import graysono.com.cp09progressdialogwebview.enums.DatabaseStatus
import graysono.com.cp09progressdialogwebview.helpers.DBHelper
import graysono.com.cp09progressdialogwebview.helpers.Wishlist
import graysono.com.cp09progressdialogwebview.helpers.WishlistRecyclerViewAdapter
import graysono.com.cp09progressdialogwebview.interfaces.IItemClick
import kotlinx.android.synthetic.main.content_wishlist.*
import kotlinx.coroutines.delay

class WishlistActivity : BaseActivity(), IItemClick {

    private lateinit var wishlists: ArrayList<Wishlist>
    private lateinit var dbHelper: DBHelper
    private lateinit var btnAdd: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var wishlistRecyclerViewAdapter: WishlistRecyclerViewAdapter
    private lateinit var sortSpinner: Spinner



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wishlist)
        displayToolbar(isHomeEnabled = true, isTitleEnabled = false)



        bnv2.setOnNavigationItemSelectedListener(OnNavigationItemSelectedListener())
        bnv2.menu.getItem(1).isChecked = true

        val sortButton = findViewById<Button>(R.id.sortBtn)
        sortButton.setOnClickListener(SortButtonClickHandler())

        wishlists = ArrayList()
        dbHelper = DBHelper(this@WishlistActivity)
        wishlists = dbHelper.selectAll()

        btnAdd = findViewById(R.id.btnAddWishlist)
        btnAdd.setOnClickListener {
            addNewWishlistDialog(DatabaseStatus.INSERT, 0, "")
        }

        recyclerView = findViewById(R.id.rcvWishlists)
        val layoutManager = LinearLayoutManager(this@WishlistActivity)
        recyclerView.layoutManager = layoutManager
        wishlistRecyclerViewAdapter = WishlistRecyclerViewAdapter(this, wishlists)
        recyclerView.adapter = wishlistRecyclerViewAdapter

        setupSpinner()
    }

    private fun setupSpinner() {
        val sortedSpinner = arrayOf<String?>(
            "Sort by:",
            "Newest",
            "Oldest",
            "Alphabetical"
        )

        sortSpinner = findViewById(R.id.sortingSpinner)
        sortSpinner.adapter = ArrayAdapter<Any?>(
            this@WishlistActivity,
            R.layout.support_simple_spinner_dropdown_item,
            sortedSpinner
        )
    }
    inner class SortButtonClickHandler : View.OnClickListener {
        override fun onClick(v: View) {


            val sortSelected =
                this@WishlistActivity.sortSpinner.selectedItem.toString()

                when (sortSelected) {
                    "Newest" -> {
                        wishlists = dbHelper.newest()
                        wishlistRecyclerViewAdapter.notifyData(wishlists)
                    }
                    "Oldest" -> {
                        wishlists = dbHelper.oldest()
                        wishlistRecyclerViewAdapter.notifyData(wishlists)
                    }
                    "Alphabetical" -> {
                        wishlists = dbHelper.alphabetical()
                        wishlistRecyclerViewAdapter.notifyData(wishlists)
                    }
                    else ->
                    {
                        readDatabase()
                    }
                }

            }
        }

    private fun readDatabase() {
        wishlists = dbHelper.selectAll()
        wishlistRecyclerViewAdapter.notifyData(wishlists)
    }
    private fun addNewWishlistDialog(status: DatabaseStatus, id: Int, txt: String) {
        val dialog = Dialog(this@WishlistActivity, R.style.DialogFullScreen)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setContentView(R.layout.fragment_add_wishlist)

        val edtAddWishlist: EditText = dialog.findViewById(R.id.edtAddWishlist)
        val btnCloseWishlist: Button = dialog.findViewById(R.id.btnCloseWishlist)
        val btnAddWishlist: Button = dialog.findViewById(R.id.btnAddWishlist)
        val txvAddWishlist: TextView = dialog.findViewById(R.id.txvAddWishlist)

        if (status == DatabaseStatus.UPDATE) {
            edtAddWishlist.setText(txt)
            btnAddWishlist.text = "Update"
            txvAddWishlist.text = "Update Wishlist"
        } else {
            edtAddWishlist.setText("")
            btnAddWishlist.text = "Add"
            txvAddWishlist.text = "Add New Wishlist"
        }

        btnAddWishlist.setOnClickListener {
            if (status == DatabaseStatus.UPDATE) {
                dbHelper.update(id.toLong(), edtAddWishlist.text.toString().trim())
                val builder1 = AlertDialog.Builder(this)
                val dialogView1 = layoutInflater.inflate(R.layout.custom_wishlist_bar,null)
                builder1.setView(dialogView1)
                builder1.setCancelable(false)
                val dialog = builder1.create()
                dialog.show()
                Handler().postDelayed({dialog.dismiss()}, 1000)
                readDatabase()

            } else if (status == DatabaseStatus.INSERT) {
                dbHelper.insert(edtAddWishlist.text.toString().trim())
//                val progressDialog = ProgressDialog(this)
//                progressDialog.setMessage("Adding..")
//                progressDialog.setCancelable(false)
//                progressDialog.show()
//                Handler().postDelayed({progressDialog.dismiss()}, 1000)


                val builder = AlertDialog.Builder(this)
                val dialogView = layoutInflater.inflate(R.layout.custom_wishlist_bar,null)
                builder.setView(dialogView)
                builder.setCancelable(false)
                val dialog = builder.create()
                dialog.show()
                Handler().postDelayed({dialog.dismiss()}, 1000)
                readDatabase()

            }
            //dialog.show()

            dialog.dismiss()
        }

        btnCloseWishlist.setOnClickListener { dialog.dismiss() }

        dialog.show()
    }

    override fun onItemClick(wishlist: Wishlist, imgBtn: ImageButton) {
        val popup = PopupMenu(this, imgBtn)
        popup.menuInflater.inflate(R.menu.menu_wishlist, popup.menu)
        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.update -> addNewWishlistDialog(DatabaseStatus.UPDATE, wishlist.id, wishlist.name!!)
                R.id.delete -> {
                    val deleteWishlistAlertDialog = CustomAlertDialog(this@WishlistActivity, R.layout.custom_delete_wishlist_item)
                    deleteWishlistAlertDialog.show(
                        R.string.delete_item, dbHelper, wishlist, wishlistRecyclerViewAdapter
                    )
//                    val builder2 = AlertDialog.Builder(this)
//                    val dialogView2 = layoutInflater.inflate(R.layout.custom_wishlist_bar,null)
//                    builder2.setView(dialogView2)
//                    builder2.setCancelable(false)
//                    val dialog = builder2.create()
//                    dialog.show()
//                    Handler().postDelayed({dialog.dismiss()}, 1000)

                    readDatabase()
                }
            }
            true
        }
        popup.show()
    }



    inner class OnNavigationItemSelectedListener :
        BottomNavigationView.OnNavigationItemSelectedListener {
        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            return when (item.itemId) {
                /**
                 * navigates to the MainActivity
                 */
                R.id.navigation_home -> {
                    startActivity(Intent(this@WishlistActivity, MainActivity::class.java))
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
                    finish()
                    true
                }
                /**
                 * navigates to the About Us activity
                 */
                R.id.navigation_profile ->{
                    startActivity(Intent(this@WishlistActivity, ProfileActivity::class.java))
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    finish()
                    true
                }
                R.id.navigation_wishlist -> {
                    startActivity(Intent(this@WishlistActivity, WishlistActivity::class.java))
                    finish()
                    true
                }
                R.id.navigation_map ->{
                    startActivity(Intent(this@WishlistActivity, MapActivity::class.java))
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
                    finish()
                    true
                }
                else -> onNavigationItemSelected(item)
            }
        }
    }
}