package graysono.com.cp09progressdialogwebview.helpers

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import kotlin.collections.ArrayList

class DBHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(DATABASE_CREATE)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insert(msg: String): Long {
        val db: SQLiteDatabase = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_NAME, msg)
        values.put(COLUMN_DATE_TIME, DateTime.currentDateTime())
        val id: Long = db.insert(TABLE_NAME, null, values)
        db.close()
        return id
    }

    fun selectAll(): ArrayList<Wishlist> {
        val wishlists = ArrayList<Wishlist>()
        val selectQuery = "SELECT * FROM $TABLE_NAME ORDER BY $COLUMN_DATE_TIME ASC"
        val db: SQLiteDatabase = this.writableDatabase
        val cursor: Cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val wishlist = Wishlist()
                wishlist.id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
                wishlist.name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
                wishlist.dateTime = cursor.getString(cursor.getColumnIndex(COLUMN_DATE_TIME))
                wishlists.add(wishlist)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return wishlists
    }

    fun update(id: Long, msg: String): Int {
        val db: SQLiteDatabase = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_NAME, msg)
        return db.update(
            TABLE_NAME, values, "$COLUMN_ID = ?",
            arrayOf(id.toString())
        )
    }

    fun delete(id: Long) {
        val db: SQLiteDatabase = this.writableDatabase
        db.delete(
            TABLE_NAME, "$COLUMN_ID = ?",
            arrayOf(id.toString())
        )
        db.close()
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "db_wishlist"
        const val TABLE_NAME = "wishlists"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_DATE_TIME = "date_time"
        const val DATABASE_CREATE: String =
            "CREATE TABLE $TABLE_NAME($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_NAME TEXT, $COLUMN_DATE_TIME TEXT)"
    }
}

