import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.remote.BrowserType.CHROME
import org.openqa.selenium.remote.BrowserType.FIREFOX
import java.net.URL

enum class DriverType {
    CHROME, FIREFOX
}

object DriverFactory {
    private const val CHROME_DRIVER = "chromedriver"
    private const val CHROME_DRIVER_PROPERTY = "webdriver.chrome.driver"
    private const val FIREFOX_DRIVER = "geckodriver"
    private const val FIREFOX_DRIVER_PROPERTY = "webdriver.gecko.driver"
    fun createDriver(type: DriverType?): WebDriver {
        return when (type) {
            DriverType.CHROME -> chromeDriver
            DriverType.FIREFOX -> firefoxDriver
            else -> throw IllegalArgumentException("unknown driver type")
        }
    }

    private val chromeDriver: WebDriver
        private get() {
            setDriverProperty(CHROME_DRIVER_PROPERTY, CHROME_DRIVER)
            val options = ChromeOptions()
            options.addArguments("--remote-debugging-port=9222")
            return ChromeDriver(options)
        }
    private val firefoxDriver: WebDriver
        private get() {
            setDriverProperty(FIREFOX_DRIVER_PROPERTY, FIREFOX_DRIVER)
            return FirefoxDriver()
        }

    private fun setDriverProperty(property: String, executable: String) {
        val classLoader = DriverFactory::class.java.classLoader
        val resource: URL? = classLoader.getResource(executable)

        if (resource != null) {
            System.setProperty(property, resource.getPath())
        }
        else {
            RuntimeException("driver executable not found")
        }
    }
}