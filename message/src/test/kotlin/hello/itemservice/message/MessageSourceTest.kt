package hello.itemservice.message

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.MessageSource
import org.springframework.context.NoSuchMessageException
import java.util.*

@SpringBootTest
internal class MessageSourceTest {

    @Autowired
    private lateinit var messageSource: MessageSource

    @Test
    fun helloMessage() {
        val result = messageSource.getMessage("hello", null, Locale.KOREAN)
        Assertions.assertThat(result).isEqualTo("안녕")
    }

    @Test
    fun notFoundMessageCode() {
        Assertions.assertThatThrownBy {
            messageSource.getMessage("no_code", null, Locale.KOREAN)
        }
            .isInstanceOf(NoSuchMessageException::class.java)
    }

    @Test
    fun notFoundMessageCodeDefaultMessage() {
        val result = messageSource.getMessage("no_code", null, "default message", Locale.KOREAN)
        Assertions.assertThat(result).isEqualTo("default message")
    }

    @Test
    fun argumentMessage() {
        val message = messageSource.getMessage("hello.name", arrayOf<Any>("Spring"), Locale.KOREAN)
        Assertions.assertThat(message).isEqualTo("안녕 Spring")
    }

    @Test
    fun defaultLang() {
        Assertions.assertThat(messageSource.getMessage("hello", null, Locale.KOREAN)).isEqualTo("안녕")
    }

    @Test
    fun enLang() {
        Assertions.assertThat(messageSource.getMessage("hello", null, Locale.ENGLISH)).isEqualTo("hello")
    }
}
