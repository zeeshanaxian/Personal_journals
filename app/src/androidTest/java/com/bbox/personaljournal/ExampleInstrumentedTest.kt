package com.bbox.personaljournal

import android.os.IBinder
import android.view.WindowManager
import android.widget.Toast
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Root
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.bbox.personaljournal.ui.MainActivity
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule
import java.util.regex.Pattern

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.personaljournal", appContext.packageName)
    }
}


@RunWith(AndroidJUnit4::class)
class MyUITest {

    @get:Rule
    val activityScenarioRule = activityScenarioRule<MainActivity>()

    @Test
    fun testButtonAndText() {
        onView(withId(R.id.btn_next)).perform(click())
        onView(withId(R.id.tv_feeling_msg)).check(matches(withText("How do you right now?")))
    }

    @Test
    fun testSaveButton() {
        onView(withId(R.id.btn_next)).perform(click())
        onView(withId(R.id.btn_save)).check(matches(withText("Save Notes")))
    }


}