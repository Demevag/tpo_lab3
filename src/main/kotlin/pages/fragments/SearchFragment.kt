package pages.fragments

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import java.util.stream.Collectors


class SearchFragment(driver: WebDriver?) : AbstractFragment(driver!!) {
    val tableColumns: List<String>
        get() {
            val table = waitFor(By.cssSelector("table.tablebasic"))
            val headerElems = table.findElements(By.xpath("(tbody/tr[1]/td | //th)[position() > 1]"))
            return headerElems.stream().map { obj: WebElement -> obj.text }.collect(Collectors.toList())
        }
    val errorText: String
        get() {
            val errorElem = waitFor(By.cssSelector(".tablefill > b:nth-child(1)"))
            return errorElem.text
        }
}