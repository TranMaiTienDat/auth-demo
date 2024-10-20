package com.example.booktracker

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EditBookActivityTest {

    private lateinit var bookId: Long

    @Before
    fun setUp() {
        // Book ID Emulation
        bookId = 1L
        val intent = Intent(ApplicationProvider.getApplicationContext(), EditBookActivity::class.java)
        intent.putExtra("BOOK_ID", bookId)
        ActivityScenario.launch<EditBookActivity>(intent)
    }

    @Test
    fun updateBookWithEmptyFields_showsErrorMessage() {
        // Do not enter any information and press the Update button.
        onView(withId(R.id.buttonUpdateBook)).perform(click())

        // Check error messages
        onView(withText("Please fill all fields: Title, Author"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun updateBookSuccessfully_navigatesToBookList() {
        // Enter valid book information
        onView(withId(R.id.editTextBookTitle)).perform(typeText("Clean Code"), closeSoftKeyboard())
        onView(withId(R.id.editTextBookAuthor)).perform(typeText("Robert C. Martin"), closeSoftKeyboard())

        // Select "Completed" status
        onView(withId(R.id.spinnerBookStatus)).perform(click())
        onView(withText("Completed")).perform(click())

        // Click the Update button
        onView(withId(R.id.buttonUpdateBook)).perform(click())

        // Check if switched to BookListActivity screen
        onView(withText("Book List")).check(matches(isDisplayed()))
    }
}
