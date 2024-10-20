package com.example.booktracker
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class BookDetailActivity : AppCompatActivity() {

    private lateinit var textViewBookTitle: TextView
    private lateinit var textViewBookAuthor: TextView
    private lateinit var textViewBookStatus: TextView
    private lateinit var buttonEditBook: Button
    private lateinit var buttonDeleteBook: Button

    private lateinit var databaseHelper: DatabaseHelper
    private var bookId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_detail)

        // Mapping the views
        textViewBookTitle = findViewById(R.id.textViewBookTitle)
        textViewBookAuthor = findViewById(R.id.textViewBookAuthor)
        textViewBookStatus = findViewById(R.id.textViewBookStatus)
        buttonEditBook = findViewById(R.id.buttonEditBook)
        buttonDeleteBook = findViewById(R.id.buttonDeleteBook)

        // Initialize DatabaseHelper
        databaseHelper = DatabaseHelper(this)

        // Get book data from Intent
        bookId = intent.getLongExtra("BOOK_ID", -1)
        if (bookId != -1L) {
            loadBookDetails(bookId)
        }

        // Event handling for Edit button
        buttonEditBook.setOnClickListener {
            val intent = Intent(this, EditBookActivity::class.java)
            intent.putExtra("BOOK_ID", bookId)
            startActivity(intent)
        }

        // Event handling for Delete button
        buttonDeleteBook.setOnClickListener {
            showDeleteConfirmationDialog()
        }
    }

    // Function to load book details from SQLite
    private fun loadBookDetails(bookId: Long) {
        val db = databaseHelper.readableDatabase
        val cursor = db.query(
            "Books",
            arrayOf("title", "author", "status"),
            "id = ?",
            arrayOf(bookId.toString()),
            null, null, null
        )

        if (cursor.moveToFirst()) {
            val title = cursor.getString(cursor.getColumnIndexOrThrow("title"))
            val author = cursor.getString(cursor.getColumnIndexOrThrow("author"))
            val status = cursor.getString(cursor.getColumnIndexOrThrow("status"))

            textViewBookTitle.text = title
            textViewBookAuthor.text = author
            textViewBookStatus.text = status
        }
        cursor.close()
    }

    // Display delete confirmation dialog
    private fun showDeleteConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Delete Book")
            .setMessage("Are you sure you want to delete this book?")
            .setPositiveButton("Yes") { _, _ ->
                deleteBook()
            }
            .setNegativeButton("No", null)
            .show()
    }

    // Delete book from database
    private fun deleteBook() {
        val db = databaseHelper.writableDatabase
        val result = db.delete("Books", "id = ?", arrayOf(bookId.toString()))

        if (result > 0) {
            Toast.makeText(this, "Book deleted successfully", Toast.LENGTH_SHORT).show()
            finish() // Đóng Activity sau khi xóa
        } else {
            Toast.makeText(this, "Failed to delete book", Toast.LENGTH_SHORT).show()
        }
    }
}
