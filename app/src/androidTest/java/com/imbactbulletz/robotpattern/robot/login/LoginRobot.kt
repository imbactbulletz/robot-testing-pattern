package com.imbactbulletz.robotpattern.robot.login

import androidx.annotation.StringRes
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.*
import com.imbactbulletz.robotpattern.R
import com.imbactbulletz.robotpattern.login.LoginFragment
import org.hamcrest.CoreMatchers

class LoginRobot {

    private val testNavController: TestNavHostController =
        TestNavHostController(ApplicationProvider.getApplicationContext())
    private val scenario =
        launchFragmentInContainer<LoginFragment>(themeResId = R.style.Theme_RobotPattern)

    init {
        scenario.onFragment { fragment ->
            testNavController.setGraph(R.navigation.nav_graph)

            Navigation.setViewNavController(fragment.requireView(), testNavController)
        }
    }

    fun setUsername(value: String) {
        onView(withId(R.id.usernameEditText)).perform(typeText(value))
    }

    fun setPassword(value: String) {
        onView(withId(R.id.passwordEditText)).perform(typeText(value))
    }

    fun clickLogin() {
        onView(withId(R.id.loginButton)).perform(click())
    }

    fun assertLoggedIn() {
        assertThat(testNavController.currentDestination?.id, CoreMatchers.`is`(R.id.DashboardFragment))
    }

    fun assertNotLoggedIn() {
        assertThat(testNavController.currentDestination?.id, CoreMatchers.`is`(R.id.LoginFragment))
    }

    fun assertErrorDisplayed(text: String) {
        onView(withText(text)).check(ViewAssertions.matches(isDisplayed()))
    }
}