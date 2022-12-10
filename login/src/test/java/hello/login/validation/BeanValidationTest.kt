package hello.login.validation

import hello.login.domain.item.Item
import org.junit.jupiter.api.Test
import javax.validation.Validation

class BeanValidationTest {
    @Test
    fun beanValidation() {
        val factory = Validation.buildDefaultValidatorFactory()
        val validator = factory.validator
        val item = Item()
        item.itemName = "  "
        item.price = 0
        item.quantity = 10000
        val violations = validator.validate(item)
        for (violation in violations) {
            println("violation=$violation")
            println("violation.message=" + violation.message)
        }
    }
}
