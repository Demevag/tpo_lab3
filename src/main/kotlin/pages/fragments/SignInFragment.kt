package pages.fragments

import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver


class SignInFragment(driver: WebDriver?) : AbstractFragment(driver!!) {
    val isLoginPresence: Boolean
        get() = isElementPresence(LOGIN_INPUT_XPATH)
    val isPasswordPresence: Boolean
        get() = isElementPresence(PASSWORD_INPUT_XPATH)
    val isCaptchaPresence: Boolean
        get() = isElementPresence(CAPTCHA_XPATH)

    fun signIn(login: String?, password: String?) {
        val loginElem = driver.findElement(LOGIN_INPUT_XPATH)
        loginElem.sendKeys(login)
        val passwordElem = driver.findElement(PASSWORD_INPUT_XPATH)
        passwordElem.sendKeys(password, Keys.ENTER)
    }

    val errorText: String
        get() {
            val errorElem = driver.findElement(By.xpath("//div[@class='tablepad']/span[@class='postcolor']"))
            return errorElem.text
        }

    private fun isElementPresence(by: By): Boolean {
        return try {
            driver.findElement(by)
            true
        } catch (exc: NoSuchElementException) {
            false
        }
    }

    companion object {
        private const val TITLE = "ЯПлакалъ - Вход"
        private val LOGIN_INPUT_XPATH = By.xpath("//input[@name='UserName']")
        private val PASSWORD_INPUT_XPATH = By.xpath("//input[@name='PassWord']")
        private val CAPTCHA_XPATH = By.xpath("//script[starts-with(@src, 'https://www.google.com/recaptcha')]")
    }

    init {
        waitForTitle(TITLE)
    }
}