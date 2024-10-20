package com.example.booktracker

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddBookActivity : AppCompatActivity() {
    private lateinit var editTextBookTitle: EditText
    private lateinit var editTextBookAuthor: EditText
    private lateinit var spinnerBookStatus: Spinner
    private lateinit var buttonSaveBook: Button
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_book)

        editTextBookTitle = findViewById(R.id.editTextBookTitle)
        editTextBookAuthor = findViewById(R.id.editTextBookAuthor)
        spinnerBookStatus = findViewById(R.id.spinnerBookStatus)
        buttonSaveBook = findViewById(R.id.buttonSaveBook)

        databaseHelper = DatabaseHelper(this)

        val statuses = arrayOf("Reading", "Completed", "Want to Read")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, statuses)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerBookStatus.adapter = adapter

        buttonSaveBook.setOnClickListener {
            val title = editTextBookTitle.text.toString().trim()
            val author = editTextBookAuthor.text.toString().trim()
            val status = spinnerBookStatus.selectedItem?.toString() ?: ""

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

            val book = Book(0, title, author, status)

            try {
                databaseHelper.addBook(book)
                startActivity(Intent(this, BookListActivity::class.java))
                finish()
            } catch (e: Exception) {
                Toast.makeText(this, "Error adding book: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
