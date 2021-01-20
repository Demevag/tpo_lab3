package pages.fragments

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import pages.elements.Post
import java.util.*


class PostFragment(driver: WebDriver?) : AbstractFragment(driver!!) {
    fun builder(): PostBuilder {
        return PostBuilder()
    }

    fun writePost(post: Post) {
        val titleElem = driver.findElement(By.xpath("//input[@name='TopicTitle']"))
        titleElem.sendKeys(post.title)
        val textElem = driver.findElement(By.xpath("//textarea[@id='Post']"))
        textElem.sendKeys(post.text)
        val tags = java.lang.String.join(",", post.tags)
        val tagsElem = driver.findElement(By.xpath("//input[@id='tags']"))
        tagsElem.sendKeys(tags)
    }

    fun send() {
        val submitButton = driver.findElement(By.xpath("//input[@name='sub']"))
        submitButton.click()
    }

    fun preview() {
        val previewButton = driver.findElement(By.xpath("//input[@name='preview']"))
        previewButton.click()
        waitFor(PREVIEW_TEXT_XPATH)
    }

    val previewText: String
        get() {
            val previewTextElem = driver.findElement(PREVIEW_TEXT_XPATH)
            return previewTextElem.text
        }

    class PostBuilder {
        var title: String? = null
        var text: String? = null
        var tags: List<String>? = null
        fun setTitle(title: String?): PostBuilder {
            this.title = title
            return this
        }

        fun setText(text: String?): PostBuilder {
            this.text = text
            return this
        }

        fun setTags(vararg tags: String?): PostBuilder {
            this.tags = listOf(*tags) as List<String>?
            return this
        }

        fun build(): Post {
            return Post(title!!, text!!, tags!!)
        }
    }

    companion object {
        private val PREVIEW_TEXT_XPATH = By.xpath("//div[@class='tableborder']/div[@class='row1']/div[1]")
    }

    init {
        waitForTitle("Создание новой темы")
    }
}