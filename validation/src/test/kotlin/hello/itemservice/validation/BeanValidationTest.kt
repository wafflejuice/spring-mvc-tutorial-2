package hello.itemservice.validation

import hello.itemservice.domain.item.Item
import org.junit.jupiter.api.Test
import javax.validation.Validation

class BeanValidationTest {

    @Test
    fun beanValidation() {
        val factory = Validation.buildDefaultValidatorFactory()
        val validator = factory.validator

        val item = Item(itemName = " ", price = 0, quantity = 1000)

        val violations = validator.validate(item)
        for (violation in violations) {
            println("violation = $violation")
            println("violation = ${violation.message}")
        }
    }
}
