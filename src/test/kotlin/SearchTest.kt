import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import pages.MainPage
import java.util.*


class SearchTest : BaseTest() {
    @Test
    fun testPositive() {
        val mainPage = MainPage(driver)
        mainPage.open()
        val search = mainPage.search("Java")
        val columns = search.tableColumns
        val expectedColumns = Arrays.asList(
            "Тема", "Форум", "Автор", "Ответов", "Просмотров",
            "Оценка", "Обновление"
        )
        assertEquals(expectedColumns, columns)
    }

    @Test
    fun testErrorOnShortSearchText() {
        // Minimum text length is 3 symbols
        val mainPage = MainPage(driver)
        mainPage.open()
        val search = mainPage.search("42")
        assertEquals("Обнаружена ошибка:", search.errorText)
    }
}