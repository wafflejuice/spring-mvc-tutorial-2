package hello.login.message

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.MessageSource
import org.springframework.context.NoSuchMessageException
import java.util.*

@SpringBootTest
class MessageSourceTest {
    @Autowired
    var ms: MessageSource? = null

    @Test
    fun helloMessage() {
        val result = ms!!.getMessage("hello", null, Locale.getDefault())
        Assertions.assertThat(result).isEqualTo("안녕")
    }

    @Test
    fun notFoundMessageCode() {
        Assertions.assertThatThrownBy { ms!!.getMessage("no_code", null, Locale.getDefault()) }
            .isInstanceOf(NoSuchMessageException::class.java)
    }

    @Test
    fun notFoundMessageCodeDefaultMessage() {
        val result = ms!!.getMessage("no_code", null, "기본 메시지", Locale.getDefault())
        Assertions.assertThat(result).isEqualTo("기본 메시지")
    }

    @Test
    fun argumentMessage() {
        val result = ms!!.getMessage("hello.name", arrayOf<Any>("Spring"), Locale.getDefault())
        Assertions.assertThat(result).isEqualTo("안녕 Spring")
    }

    @Test
    fun defaultLang() {
        Assertions.assertThat(ms!!.getMessage("hello", null, Locale.KOREA)).isEqualTo("안녕")
    }

    @Test
    fun enLang() {
        Assertions.assertThat(ms!!.getMessage("hello", null, Locale.ENGLISH)).isEqualTo("hello")
    }
}
