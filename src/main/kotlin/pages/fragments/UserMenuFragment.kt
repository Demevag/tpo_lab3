package pages.fragments

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import java.util.stream.Collectors

class UserMenuFragment(driver: WebDriver?, private val userMenu: WebElement?) : AbstractFragment(driver!!) {
    val links: List<String>
        get() {
            val linksElements = userMenu?.findElements(By.xpath("//ul[@id='user-menu']/li/a"))
            if (linksElements != null) {
                return linksElements.stream().map { obj: WebElement -> obj.text }.collect(Collectors.toList())
            }
            return ArrayList<String>()
        }
    val userName: String
        get() {
            return userMenu?.findElement(By.xpath("//div[@class='user-name']/a"))!!.text
        }
    val userTitle: String
        get() {
            return userMenu?.findElement(By.xpath("//div[@class='user-title']"))!!.text
        }
    val userRank: Long
        get() {
            val rankString = userMenu?.findElement(By.xpath("//span[@class='user-rank']"))!!.text
            return rankString.toLong()
        }
}