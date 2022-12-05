package hello.itemservice.validation

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.validation.DefaultMessageCodesResolver
import org.springframework.validation.MessageCodesResolver

internal class MessageCodesResolverTest {
    
    private val codesResolver: MessageCodesResolver = DefaultMessageCodesResolver()

    @Test
    fun messageCodesResolverObject() {
        val messageCodes = codesResolver.resolveMessageCodes("required", "item")
        Assertions.assertThat(messageCodes).contains("required.item", "required")
    }

    @Test
    fun messageCodesResolverField() {
        val messageCodes = codesResolver.resolveMessageCodes("required", "item", "itemName", String::class.java)
        Assertions.assertThat(messageCodes).containsExactly(
            "required.item.itemName",
            "required.itemName",
            "required.java.lang.String",
            "required"
        )
    }
}
