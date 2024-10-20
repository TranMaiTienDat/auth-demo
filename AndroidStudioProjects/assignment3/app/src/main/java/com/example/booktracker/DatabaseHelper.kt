package com.example.booktracker
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "books.db"
        private const val DATABASE_VERSION = 1
        const val TABLE_BOOK = "books"
        const val COLUMN_ID = "_id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_AUTHOR = "author"
        const val COLUMN_STATUS = "status"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = ("CREATE TABLE $TABLE_BOOK ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_TITLE TEXT NOT NULL, $COLUMN_AUTHOR TEXT NOT NULL, $COLUMN_STATUS TEXT NOT NULL)")
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_BOOK")
        onCreate(db)
    }

    // Add books
    fun addBook(book: Book) {
        val db = writableDatabase
        val values = ContentValues()
        values.put(COLUMN_TITLE, book.title)
        values.put(COLUMN_AUTHOR, book.author)
        values.put(COLUMN_STATUS, book.status)
        db.insert(TABLE_BOOK, null, values)
        db.close()
    }

    // Get a list of all books
    fun getAllBooks(): MutableList<Book> {
        val bookList = mutableListOf<Book>()
        val db = readableDatabase
        val cursor: Cursor = db.query(TABLE_BOOK, null, null, null, null, null, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
                val author = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AUTHOR))
                val status = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STATUS))

                val book = Book(id, title, author, status)
                bookList.add(book)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return bookList
    }

    // Get books by ID
    fun getBookById(id: Long): Book? {
        val db = readableDatabase
        val cursor: Cursor = db.query(TABLE_BOOK, null, "$COLUMN_ID = ?", arrayOf(id.toString()), null, null, null)

        return if (cursor.moveToFirst()) {
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val author = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AUTHOR))
            val status = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STATUS))
            val book = Book(id, title, author, status)
            cursor.close()
            db.close()
            book
        } else {
            cursor.close()
            db.close()
            null
        }
    }

    // Book Update
    fun updateBook(book: Book) {
        val db = writableDatabase
        val values = ContentValues()
        values.put(COLUMN_TITLE, book.title)
        values.put(COLUMN_AUTHOR, book.author)
        values.put(COLUMN_STATUS, book.status)

        db.update(TABLE_BOOK, values, "$COLUMN_ID = ?", arrayOf(book.id.toString()))
        db.close()
    }

    // Delete book
    fun deleteBook(id: Long) {
        val db = writableDatabase
        db.delete(TABLE_BOOK, "$COLUMN_ID = ?", arrayOf(id.toString()))
        db.close()
    }
}
