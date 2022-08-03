package com.imbactbulletz.robotpattern.robot.dashboard

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

class DashboardRobot {

    private val testNavController: TestNavHostController =
        TestNavHostController(ApplicationProvider.getApplicationContext())
    private val scenario =
        launchFragmentInContainer<DashboardFragment>(themeResId = R.style.Theme_RobotPattern)

    init {
        scenario.onFragment { fragment ->
            testNavController.setGraph(R.navigation.nav_graph)

            Navigation.setViewNavController(fragment.requireView(), testNavController)
        }
    }

    fun clickIncrement() {
        onView(withId(R.id.incrementCounterButton))
            .perform(click())
    }

    fun clickDecrement() {
        onView(withId(R.id.decrementCounterButton)).perform(click())
    }

    fun assertCounterText(text: String) {
        onView(withId(R.id.counterTextView)).check(matches(withText(text)))
    }

    fun assertErrorDisplayed(text: String) {
        onView(withText(text)).check(matches(isDisplayed()))
    }
}