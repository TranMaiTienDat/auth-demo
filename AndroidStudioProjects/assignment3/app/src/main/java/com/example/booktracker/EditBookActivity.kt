package com.example.booktracker

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class EditBookActivity : AppCompatActivity() {
    private lateinit var editTextBookTitle: EditText
    private lateinit var editTextBookAuthor: EditText
    private lateinit var spinnerBookStatus: Spinner
    private lateinit var buttonUpdateBook: Button
    private lateinit var databaseHelper: DatabaseHelper
    private var bookId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_book)

        editTextBookTitle = findViewById(R.id.editTextBookTitle)
        editTextBookAuthor = findViewById(R.id.editTextBookAuthor)
        spinnerBookStatus = findViewById(R.id.spinnerBookStatus)
        buttonUpdateBook = findViewById(R.id.buttonUpdateBook)

        databaseHelper = DatabaseHelper(this)

        // Get Book ID from Intent
        bookId = intent.getLongExtra("BOOK_ID", -1)

        // Set Spinner for book state
        val statusOptions = arrayOf("Reading", "Completed", "Want to Read")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, statusOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerBookStatus.adapter = adapter

        if (bookId != -1L) {
            loadBookDetails(bookId)
        }

        buttonUpdateBook.setOnClickListener {
            val title = editTextBookTitle.text.toString().trim()
            val author = editTextBookAuthor.text.toString().trim()
            val status = spinnerBookStatus.selectedItem?.toString() ?: ""

            // Reset background color for fields
            editTextBookTitle.setBackgroundResource(android.R.color.transparent)
            editTextBookAuthor.setBackgroundResource(android.R.color.transparent)

            val missingFields = mutableListOf<String>()
            if (title.isEmpty()) {
                missingFields.add("Title")
                editTextBookTitle.setBackgroundResource(R.drawable.error_background)
            }
            if (author.isEmpty()) {
                missingFields.add("Author")
                editTextBookAuthor.setBackgroundResource(R.drawable.error_background)
            }
            if (status.isEmpty()) {
                missingFields.add("Status")
            }

            if (missingFields.isNotEmpty()) {
                val message = "Please fill all fields: ${missingFields.joinToString(", ")}"
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val book = Book(bookId, title, author, status)

            try {
                databaseHelper.updateBook(book)
                startActivity(Intent(this, BookListActivity::class.java))
                finish() // End Activity and return
            } catch (e: Exception) {
                Toast.makeText(this, "Error updating book: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadBookDetails(bookId: Long) {
        val book = databaseHelper.getBookById(bookId)
        book?.let {
            editTextBookTitle.setText(it.title)
            editTextBookAuthor.setText(it.author)
            // Select the corresponding state for the spinner
            val statusPosition = when (it.status) {
                "Reading" -> 0
                "Completed" -> 1
                "Want to Read" -> 2
                else -> 0 // Default select first state
            }
            spinnerBookStatus.setSelection(statusPosition)
        }
    }
}
