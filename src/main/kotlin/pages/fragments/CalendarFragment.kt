package pages.fragments

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

class CalendarFragment(driver: WebDriver?) : AbstractFragment(driver!!) {
    val month: String
        get() {
            val monthAndYear = driver.findElement(By.xpath("//h4[2]")).text

            return monthAndYear.substringBefore(" ")
        }
    val year: Long
        get() {
            val monthAndYear = driver.findElement(By.xpath("//h4[2]")).text

            return monthAndYear.substringAfter(" ").toLong()
        }
    val day: Long
        get() {
            val days = driver.findElements(By.xpath("//td[@class='calendar-day']"))
            for (day in days) {
                val backgroundColor = day.getCssValue("background-color")
                if (backgroundColor == "rgba(252, 162, 162, 1)") {
                    return day.text.toLong()
                }
            }
            return 0
        }
}