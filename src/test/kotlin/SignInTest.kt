
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import pages.MainPage

import pages.fragments.SignInFragment


class SignInTest : BaseTest() {
    private var signInFragment: SignInFragment? = null
    @BeforeEach
    fun setSignInFragment() {
        val mainPage = MainPage(driver)
        mainPage.open()
        signInFragment = mainPage.goToSignIn()
    }

    @Test
    fun testSignInElements() {
        assertTrue(signInFragment!!.isLoginPresence)
        assertTrue(signInFragment!!.isPasswordPresence)
        assertTrue(signInFragment!!.isCaptchaPresence)
    }

    @Test
    fun testErrorOnMissingCaptcha() {
        signInFragment!!.signIn("test_login", "test_password")
        assertEquals(
            "Проверка на бота не пройдена (missing-input-response)",
            signInFragment!!.errorText
        )
    }
}