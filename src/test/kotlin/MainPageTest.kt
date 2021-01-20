import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.StaleElementReferenceException
import org.openqa.selenium.WebElement
import pages.MainPage
import pages.fragments.CalendarFragment
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import java.util.logging.Logger
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue


class MainPageTest : BaseTest() {
    private lateinit var mainPage: MainPage

    @BeforeEach
    fun openMainPage() {
        mainPage = MainPage(driver)
        mainPage.open()
    }

    @Test
    fun testNewsFeed() {
        val allNews = mainPage.news
        assertEquals(25, allNews.size)
        for (news in mainPage.news) {
            assertFalse(news.header.isEmpty())
            if (news.rating == null) {
                val msg = String.format("news without rating: id=%s", news.id)
                logger.info(msg)
            }
        }
    }

    @Test
    fun testFooter() {
        val footer = mainPage.footer
        assertEquals(FOOTER_LINKS, footer.links)
        assertEquals(FOOTER_STATIC_INFO, footer.staticInfo)
    }

    @ParameterizedTest
    @ValueSource(strings = ["cat-2", "cat-5", "cat-3"])
    fun testDropDownMenu(menuId: String) {
        val dropDownMenu = driver?.findElement(By.id(menuId))
        val hiddenMenu = driver?.findElement(By.id("menu-$menuId"))

        assertFalse(hiddenMenu?.isDisplayed == true)

        dropDownMenu?.click()

        assertTrue(hiddenMenu?.isDisplayed == true)
    }

    @Test
    fun checkAllLinks() {
        val links = driver!!.findElements(By.tagName("a"))

        val it: Iterator<WebElement> = links.iterator()

        while (it.hasNext()) {

            val url: String
            try {
                url = it.next().getAttribute("href")
            }
            catch (ex: StaleElementReferenceException) {
                //logger.info("URL '${it.next().text}' removed from DOM")
                continue
            }

            if (url == null || url.isEmpty()) {
                logger.info("URL '$url' is either not configured for anchor tag or it is empty")
                continue
            }
            if (!url.contains(SITE_NAME)) {
                logger.info("URL '$url' belongs to another domain, skipping it.")
                continue
            }
            assertDoesNotThrow {
                val huc = URL(url).openConnection()
                if (huc !is HttpURLConnection) {
                    return@assertDoesNotThrow
                }
                huc.requestMethod = "HEAD"
                huc.connect()
                val respCode = huc.responseCode
                assertTrue(respCode < 400)
            }
        }
    }

    @Test
    fun testScrollUpButton() {
        val upButton = driver!!.findElement(By.id("scrollTop"))
        val js = driver as JavascriptExecutor

        js.executeScript("window.scrollBy(0,1000)");

        var value = js.executeScript("return window.pageYOffset;") as Long
        assertNotEquals(0, value)

        upButton.click()

        value = js.executeScript("return window.pageYOffset;") as Long
        assertEquals(0, value)
    }

    @Test
    fun testCalendar() {
        val calendar = CalendarFragment(driver)

        val monthNumToName = mapOf(
            "1" to "Январь",
            "2" to "Февраль",
            "3" to "Март",
            "4" to "Апрель",
            "5" to "Май",
            "6" to "Июнь",
            "7" to "Июль",
            "8" to "Август",
            "9" to "Сентябрь",
            "10" to "Октябрь",
            "11" to "Ноябрь",
            "12" to "Декабрь"
        )

        val sdf = SimpleDateFormat("dd/M/yyyy")
        val currentDate = sdf.format(Date())

        val (day, month, year) = currentDate.split("/")

        assertEquals(monthNumToName[month], calendar.month)
        assertEquals(year.toLong(), calendar.year)
        assertEquals(day.toLong(), calendar.day)
    }

    companion object {
        private val logger: Logger = Logger.getLogger(MainPage::class.java.name)
    }
}