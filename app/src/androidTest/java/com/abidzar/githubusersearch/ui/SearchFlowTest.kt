package com.abidzar.githubusersearch.ui

import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.abidzar.githubusersearch.MainActivity
import com.abidzar.githubusersearch.R
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchFlowTest {

    @Test
    fun search_and_open_detail() {
        launch(MainActivity::class.java)
        onView(withId(R.id.searchEditText)).perform(click(), typeText("octocat"))
        Thread.sleep(3000) // simplistic wait for debounce + network; replace with Idling in production
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
        onView(withId(R.id.recyclerView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<androidx.recyclerview.widget.RecyclerView.ViewHolder>(0, click())
        )
        onView(withId(R.id.usernameText)).check(matches(isDisplayed()))
    }
}
