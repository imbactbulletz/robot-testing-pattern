package com.imbactbulletz.robotpattern.regular.login

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.imbactbulletz.robotpattern.login.LoginFragment
import com.imbactbulletz.robotpattern.R
import org.hamcrest.CoreMatchers.*
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginFragmentTest {

    private lateinit var testNavController: TestNavHostController

    @Before
    fun setUp() {
        testNavController = TestNavHostController(ApplicationProvider.getApplicationContext())
        val scenario = launchFragmentInContainer<LoginFragment>(themeResId = R.style.Theme_RobotPattern)

        scenario.onFragment { fragment ->
            testNavController.setGraph(R.navigation.nav_graph)

            Navigation.setViewNavController(fragment.requireView(), testNavController)
        }
    }

    @Test
    fun redirectsToDashboardScreenWhenCredentialsAreValidAndLoginIsPressed() {
        // Given
        onView(withId(R.id.usernameEditText)).perform(typeText(VALID_USERNAME))
        onView(withId(R.id.passwordEditText)).perform(typeText(VALID_PASSWORD))

        // When
        onView(withId(R.id.loginButton)).perform(click())

        // Then
        assertThat(testNavController.currentDestination?.id, `is`(R.id.DashboardFragment))
    }

    @Test
    fun showsInvalidUsernameToastWhenOnlyUsernameIsEmptyAndLoginIsPressed() {
        // Given
        onView(withId(R.id.usernameEditText)).perform(typeText(""))
        onView(withId(R.id.passwordEditText)).perform(typeText(VALID_PASSWORD))

        // When
        onView(withId(R.id.loginButton)).perform(click())

        // Then
        onView(withText(R.string.empty_username)).check(matches(isDisplayed()))
        assertThat(testNavController.currentDestination?.id, `is`(R.id.LoginFragment))
    }

    @Test
    fun showsInvalidPasswordToastWhenPasswordIsEmptyAndLoginIsPressed() {
        // Given
        onView(withId(R.id.usernameEditText)).perform(typeText(VALID_USERNAME))
        onView(withId(R.id.passwordEditText)).perform(typeText(""))

        // When
        onView(withId(R.id.loginButton)).perform(click())

        // Then
        onView(withText(R.string.empty_password)).check(matches(isDisplayed()))
        assertThat(testNavController.currentDestination?.id, `is`(R.id.LoginFragment))
    }

    @Test
    fun showsInvalidCredentialsErrorWhenCredentialsAreWrongAndLoginIsPressed() {
        // Given
        onView(withId(R.id.usernameEditText)).perform(typeText("asdf"))
        onView(withId(R.id.passwordEditText)).perform(typeText("asdf"))

        // When
        onView(withId(R.id.loginButton)).perform(click())

        // Then
        onView(withText(R.string.invalid_credentials)).check(matches(isDisplayed()))
        assertThat(testNavController.currentDestination?.id, `is`(R.id.LoginFragment))
    }

    companion object {
        private const val VALID_USERNAME = "demo"
        private const val VALID_PASSWORD = "demo"
    }
}