package com.example.booktracker

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.hamcrest.Matchers.matches
import org.hamcrest.Matchers.isDisplayed

@RunWith(AndroidJUnit4::class)
class AddBookActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(AddBookActivity::class.java)

    @Test
    fun addBookWithEmptyFields_showsErrorMessage() {
        onView(withId(R.id.buttonSaveBook)).perform(click())
        onView(withText("Please fill all fields: Title, Author"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun addBookSuccessfully_navigatesToBookList() {
        onView(withId(R.id.editTextBookTitle))
            .perform(typeText("Clean Code"), closeSoftKeyboard())
        onView(withId(R.id.editTextBookAuthor))
            .perform(typeText("Robert C. Martin"), closeSoftKeyboard())

        onView(withId(R.id.spinnerBookStatus)).perform(click())
        onView(withText("Completed")).perform(click())

        onView(withId(R.id.buttonSaveBook)).perform(click())
        onView(withText("Book List")).check(matches(isDisplayed()))
    }
}
