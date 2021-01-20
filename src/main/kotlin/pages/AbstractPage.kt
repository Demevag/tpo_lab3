package pages

import org.openqa.selenium.*
import pages.fragments.FooterFragment
import pages.fragments.SearchFragment
import pages.fragments.SignInFragment
import pages.fragments.UserMenuFragment


abstract class AbstractPage(protected val driver: WebDriver, host: String, uri: String) {
    protected val url: String = host + uri

    constructor(driver: WebDriver, uri: String) : this(driver, System.getProperty(HOST_PROPERTY, DEFAULT_HOST), uri)

    fun open() {
        driver[url]
    }

    fun open(sessionId: String?) {
        open(Cookie(SESSION_ID_COOKIE_NAME, sessionId))
    }

    fun open(vararg cookies: Cookie?) {
        driver[url]
        for (cookie in cookies) {
            driver.manage().addCookie(cookie)
        }
        driver[url]
    }

    val footer: FooterFragment
        get() {
            val footer = driver.findElement(By.xpath("//div[@id=\"footer\"]"))
            return FooterFragment(driver, footer)
        }

    val userMenu: UserMenuFragment
        get() {
            val userMenu = driver.findElement(By.id("user-box"))
            return UserMenuFragment(driver, userMenu)
        }
    companion object {
        private const val DEFAULT_HOST = "https://www.yaplakal.com"
        private const val HOST_PROPERTY = "host"
        private const val SESSION_ID_COOKIE_NAME = "SID"
    }

}