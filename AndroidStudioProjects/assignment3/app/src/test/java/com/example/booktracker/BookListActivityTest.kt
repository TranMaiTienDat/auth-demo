package com.example.booktracker

import android.content.Intent
import android.view.View
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BookListActivityTest {

    @Test
    fun testAddBookButton() {
        // Initialize Activity
        val scenario = ActivityScenario.launch(BookListActivity::class.java)

        // Check the add book button
        scenario.onActivity { activity ->
            val buttonAddBook: View? = activity.findViewById(R.id.buttonAddBook)
            assertTrue("Button Add Book should be displayed", buttonAddBook?.visibility == View.VISIBLE)

            // Click the add book button
            buttonAddBook?.performClick()
        }

        // Check if the Add Book Activity is open
        val intent = Intent(InstrumentationRegistry.getInstrumentation().targetContext, AddBookActivity::class.java)
        val activityStarted = ActivityScenario.launch<AddBookActivity>(intent)

        activityStarted.onActivity { addBookActivity ->
            //Test a specific View in the Add Book Activity
            val textViewTitle: View? = addBookActivity.findViewById(R.id.textViewTitle)
            assertTrue("TextView in AddBookActivity should be displayed", textViewTitle?.visibility == View.VISIBLE)
        }

        //Close Activity after checking
        activityStarted.close() // Close AddBookActivity
        scenario.close() // Close BookListActivity
    }

    @Test
    fun testRecyclerViewIsDisplayed() {
        // Initialize Activity
        val scenario = ActivityScenario.launch(BookListActivity::class.java)

        // Check if RecyclerView is visible
        scenario.onActivity { activity ->
            val recyclerView: View? = activity.findViewById(R.id.recyclerViewBooks)
            assertTrue("RecyclerView should be displayed", recyclerView?.visibility == View.VISIBLE)
        }

        //Close Activity after checking
        scenario.close() // Close BookListActivity
    }
}
