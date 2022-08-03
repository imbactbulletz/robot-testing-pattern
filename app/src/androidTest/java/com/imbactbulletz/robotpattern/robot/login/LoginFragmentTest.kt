package com.imbactbulletz.robotpattern.robot.login

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.imbactbulletz.robotpattern.R
import com.imbactbulletz.robotpattern.util.resolveString
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginFragmentTest {

    @Test
    fun redirectsToDashboardScreenWhenCredentialsAreValidAndLoginIsPressed() = withLoginRobot {
        // Given
        setUsername(VALID_USERNAME)
        setPassword(VALID_PASSWORD)

        // When
        clickLogin()

        // Then
        assertLoggedIn()
    }

    @Test
    fun showsInvalidUsernameToastWhenOnlyUsernameIsEmptyAndLoginIsPressed() = withLoginRobot {
        // Given
        setUsername("")
        setPassword(VALID_PASSWORD)

        // When
        clickLogin()

        // Then
        assertErrorDisplayed(resolveString(R.string.empty_username))
        assertNotLoggedIn()
    }

    @Test
    fun showsInvalidPasswordToastWhenPasswordIsEmptyAndLoginIsPressed() = withLoginRobot {
        // Given
        setUsername(VALID_USERNAME)
        setPassword("")

        // When
        clickLogin()

        // Then
        assertErrorDisplayed(resolveString(R.string.empty_password))
        assertNotLoggedIn()
    }

    @Test
    fun showsInvalidCredentialsErrorWhenCredentialsAreWrongAndLoginIsPressed() = withLoginRobot {
        // Given
        setUsername("asdf")
        setPassword("asdf")

        // When
        clickLogin()

        // Then
        assertErrorDisplayed(resolveString(R.string.invalid_credentials))
        assertNotLoggedIn()
    }

    /**
     * Initializes a robot and performs a set of [func] actions upon it.
     */
    private fun withLoginRobot(func: LoginRobot.() -> Unit) {
        func(LoginRobot())
    }

    companion object {
        private const val VALID_USERNAME = "demo"
        private const val VALID_PASSWORD = "demo"
    }
}