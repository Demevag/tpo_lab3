package pages.fragments

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import java.util.stream.Collectors

class FooterFragment(driver: WebDriver?, private val footer: WebElement?) : AbstractFragment(driver!!) {
    val links: List<String>
        get() {
            val linksElements = footer?.findElements(By.xpath("//ul[@class='footer-menu']/li/a"))
            if (linksElements != null) {
                return linksElements.stream().map { obj: WebElement -> obj.text }.collect(Collectors.toList())
            }
            return ArrayList<String>()
        }
    val staticInfo: List<String>
        get() {
            val textElement1 = footer?.findElement(By.xpath("//div[@id='footer-counters']/div[last()-1]"))
            val textElement2 = footer?.findElement(By.xpath("//div[@id='footer-counters']/div[last()]"))
            var textElements = listOf(textElement1, textElement2)
            if (textElements != null) {
                return textElements.stream().map { obj: WebElement? -> obj!!.text }.collect(Collectors.toList())
            }
            return ArrayList<String>()
        }
}