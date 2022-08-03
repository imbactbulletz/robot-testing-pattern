package com.imbactbulletz.robotpattern.regular.dashboard

import android.app.Application
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.imbactbulletz.robotpattern.R
import com.imbactbulletz.robotpattern.dashboard.DashboardFragment
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DashboardFragmentTest {

    private lateinit var testNavController: TestNavHostController

    @Before
    fun setUp() {
        testNavController = TestNavHostController(ApplicationProvider.getApplicationContext())
        val scenario = launchFragmentInContainer<DashboardFragment>(themeResId = R.style.Theme_RobotPattern)

        scenario.onFragment { fragment ->
            testNavController.setGraph(R.navigation.nav_graph)

            Navigation.setViewNavController(fragment.requireView(), testNavController)
        }
    }

    @Test
    fun counterValueIsZeroWhenScreenIsOpened() {
        // Given

        // When

        // Then
        val counterValue = 0
        val counterTextTemplate = ApplicationProvider.getApplicationContext<Application>()
            .getString(R.string.counter_value_template)
        val counterText = String.format(counterTextTemplate, counterValue)
        onView(withText(counterText)).check(matches(isDisplayed()))
    }

    @Test
    fun incrementsCounterWhenCounterIsZeroAndIncrementButtonIsClicked() {
        // Given

        // When
        onView(withId(R.id.incrementCounterButton)).perform(click())

        // Then
        val counterValue = 1
        val counterTextTemplate = ApplicationProvider.getApplicationContext<Application>()
            .getString(R.string.counter_value_template)
        val counterText = String.format(counterTextTemplate, counterValue)

        onView(withText(counterText)).check(matches(isDisplayed()))
    }

    @Test
    fun incrementsCounterWhenCounterIsAboveZeroAndIncrementButtonIsClicked() {
        // Given
        onView(withId(R.id.incrementCounterButton)).perform(click())

        // When
        onView(withId(R.id.incrementCounterButton)).perform(click())

        // Then
        val counterValue = 2
        val counterTextTemplate = ApplicationProvider.getApplicationContext<Application>()
            .getString(R.string.counter_value_template)
        val counterText = String.format(counterTextTemplate, counterValue)

        onView(withText(counterText)).check(matches(isDisplayed()))
    }

    @Test
    fun decrementsCounterWhenCounterAboveZeroAndDecrementButtonClicked() {
        // Given
        onView(withId(R.id.incrementCounterButton)).perform(click())
        onView(withId(R.id.incrementCounterButton)).perform(click())

        // When
        onView(withId(R.id.decrementCounterButton)).perform(click())

        // Then
        val counterValue = 1
        val counterTextTemplate = ApplicationProvider.getApplicationContext<Application>()
            .getString(R.string.counter_value_template)
        val counterText = String.format(counterTextTemplate, counterValue)

        onView(withText(counterText)).check(matches(isDisplayed()))
    }

    @Test
    fun displaysErrorWhenCounterIsZeroAndDecrementButtonClicked() {
        // When
        onView(withId(R.id.decrementCounterButton)).perform(click())

        // Then
        val counterValue = 0
        val counterTextTemplate = ApplicationProvider.getApplicationContext<Application>()
            .getString(R.string.counter_value_template)
        val counterText = String.format(counterTextTemplate, counterValue)

        onView(withText(counterText)).check(matches(isDisplayed()))
        onView(withText(R.string.decrement_error)).check(matches(isDisplayed()))
    }
}