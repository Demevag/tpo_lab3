
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import pages.MainPage
import pages.elements.Post

import pages.fragments.PostFragment


class LoggedInTest : BaseTest() {
    private lateinit var mainPage: MainPage
    @BeforeEach
    fun setUp() {
        mainPage = MainPage(driver)
        mainPage.open(System.getProperty(SESSION_ID_PROPERTY, DEFAULT_SESSION_ID))
    }

    @Test
    fun testCreatePostPreview() {
        val postFragment = mainPage.writePost()
        val text = "Test text"
        val post: Post = postFragment!!.builder()
            .setTitle("Test title")
            .setText(text)
            .setTags("t1", "t2")
            .build()
        postFragment!!.writePost(post)
        postFragment!!.preview()
        assertEquals(text, postFragment!!.previewText)
    }

    @Test
    fun testUserInfoSection() {
        val userMenuFragment = mainPage.userMenu

        val links = listOf("Настройки", "Почта", "Закладки", "Мои темы")

        assertEquals("Demevag", userMenuFragment.userName)
        assertEquals("Шутник", userMenuFragment.userTitle)
        assertEquals(0, userMenuFragment.userRank)
        assertEquals(links, userMenuFragment.links)
    }

    companion object {
        private const val SESSION_ID_PROPERTY = "lab3.session.id"
        private const val DEFAULT_SESSION_ID = "bb0e2bc48d19861f9c8dcf0e638434cc"
    }
}