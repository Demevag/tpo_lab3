package pages

import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import pages.elements.News
import pages.fragments.PostFragment
import pages.fragments.SearchFragment
import pages.fragments.SignInFragment
import java.util.*


class MainPage(driver: WebDriver?) : AbstractPage(driver!!, "/") {
    // find elem by css selector because div has two classes
    val news: List<News>
        get() {
            val allNews = driver.findElements(By.xpath("//td[@class='newshead']"))
            val result = ArrayList<News>(allNews.size)
            for (news in allNews) {
                var rating: Int?
                rating = try {
                    // find elem by css selector because div has two classes
                    val ratingElem = news.findElement(By.cssSelector("div.rating-short-value > a"))
                    ratingElem.text.toInt()
                } catch (exc: NoSuchElementException) {
                    null
                }
                val headerElem = news.findElement(By.xpath("div/h2/a[1]"))
                val header = headerElem.text
                result.add(News(news.getAttribute("id"), header, rating!!))
            }
            return result
        }

    fun search(text: String?): SearchFragment {
        val input = driver.findElement(By.xpath("//form[@name='search_form']/*/input[@type='text']"))
        input.sendKeys(text, Keys.ENTER)
        return SearchFragment(driver)
    }

    fun goToSignIn(): SignInFragment {
        val signInElem = driver.findElement(By.xpath("//div[@id='welcome-box']/a[2]"))
        signInElem.click()
        return SignInFragment(driver)
    }

    fun writePost(): PostFragment {
        val newPostElem = driver.findElement(By.xpath("//a[@id='new-post']"))
        newPostElem.click()
        return PostFragment(driver)
    }
}