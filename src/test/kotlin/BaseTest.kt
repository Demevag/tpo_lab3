

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.openqa.selenium.WebDriver

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
open class BaseTest {
    protected var driver: WebDriver? = null
    @BeforeAll
    fun createDriver() {
        val property = System.getProperty("driver")
        driver = if (FIREFOX_DRIVER == property) {
            DriverFactory.createDriver(DriverType.FIREFOX)
        } else {
            DriverFactory.createDriver(DriverType.CHROME)
        }
    }

    @AfterAll
    fun quitDriver() {
        driver!!.quit()
    }

    companion object {
        private const val FIREFOX_DRIVER = "firefox"
        const val SITE_NAME="yaplakal.com"
        val FOOTER_LINKS = listOf(
            "О проекте", "Помощь", "Авторские права",
            "Частые вопросы и ответы", "Размещение рекламы", "Как обойти запрет на ЯП",
            "Как пользоваться поиском", "Как разместить картинки", "Как разместить видео",
            "Разместить новость", "Yaplakal iOS", "Yaplakal Android")
        val FOOTER_STATIC_INFO = listOf(
            "Все материалы добавляются пользователями. При копировании необходимо указывать ссылку на источник.",
            "Yaplakal.com © 2004-2021. Административные вопросы: admyaplakal.com"
        )
    }
}