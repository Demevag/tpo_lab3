package pages.fragments

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

abstract class AbstractFragment(driver: WebDriver) {
    protected val driver: WebDriver = driver
    fun waitFor(by: By?): WebElement {
        return waitFor(by, DEFAULT_TIMEOUT_SECONDS)
    }

    fun waitFor(by: By?, timeout: Long): WebElement {
        val wait = WebDriverWait(driver, timeout)
        return wait.until(ExpectedConditions.presenceOfElementLocated(by))
    }

    @JvmOverloads
    fun waitForTitle(title: String?, timeout: Long = DEFAULT_TIMEOUT_SECONDS): Boolean {
        val wait = WebDriverWait(driver, timeout)
        return wait.until(ExpectedConditions.titleContains(title))
    }

    companion object {
        private const val DEFAULT_TIMEOUT_SECONDS = 10L
    }

}