package com.imbactbulletz.robotpattern.robot.dashboard

import android.app.Application
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import com.imbactbulletz.robotpattern.R
import com.imbactbulletz.robotpattern.dashboard.DashboardFragment
import com.imbactbulletz.robotpattern.util.resolveString
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
    fun counterValueIsZeroWhenScreenIsOpened() = withDashboardRobot {
        // Given

        // When

        // Then
        val counterValue = 0
        val counterTextTemplate = ApplicationProvider.getApplicationContext<Application>()
            .getString(R.string.counter_value_template)
        val counterText = String.format(counterTextTemplate, counterValue)

        assertCounterText(counterText)
    }

    @Test
    fun incrementsCounterWhenCounterIsZeroAndIncrementButtonIsClicked() = withDashboardRobot{
        // Given

        // When
        clickIncrement()

        // Then
        val counterValue = 1
        val counterTextTemplate = ApplicationProvider.getApplicationContext<Application>()
            .getString(R.string.counter_value_template)
        val counterText = String.format(counterTextTemplate, counterValue)

        assertCounterText(counterText)
    }

    @Test
    fun incrementsCounterWhenCounterIsAboveZeroAndIncrementButtonIsClicked() = withDashboardRobot {
        // Given
        clickIncrement()

        // When
        clickIncrement()

        // Then
        val counterValue = 2
        val counterTextTemplate = ApplicationProvider.getApplicationContext<Application>()
            .getString(R.string.counter_value_template)
        val counterText = String.format(counterTextTemplate, counterValue)

        assertCounterText(counterText)
    }

    @Test
    fun decrementsCounterWhenCounterAboveZeroAndDecrementButtonClicked() = withDashboardRobot {
        // Given
        clickIncrement()
        clickIncrement()

        // When
        clickDecrement()

        // Then
        val counterValue = 1
        val counterTextTemplate = ApplicationProvider.getApplicationContext<Application>()
            .getString(R.string.counter_value_template)
        val counterText = String.format(counterTextTemplate, counterValue)

        assertCounterText(counterText)
    }

    @Test
    fun displaysErrorWhenCounterIsZeroAndDecrementButtonClicked() = withDashboardRobot {
        // When
        clickDecrement()

        // Then
        val counterValue = 0
        val counterTextTemplate = ApplicationProvider.getApplicationContext<Application>()
            .getString(R.string.counter_value_template)
        val counterText = String.format(counterTextTemplate, counterValue)

        assertCounterText(counterText)
        assertErrorDisplayed(resolveString(R.string.decrement_error))
    }

    /**
     * Initializes a Dashboard robot and performs actions of [func] upon it.
     */
    private fun withDashboardRobot(func: DashboardRobot.() -> Unit) {
        func(DashboardRobot())
    }
}