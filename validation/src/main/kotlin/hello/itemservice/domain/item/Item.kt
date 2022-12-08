package hello.itemservice.domain.item

import org.hibernate.validator.constraints.Range
import javax.validation.constraints.Max
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class Item(
    var id: Long? = null,

    @field:NotBlank
    var itemName: String? = null,

    @field:Range(min = 1000, max = 1000000)
    @field:NotNull
    var price: Int? = null,

    @Max(9999)
    @field:NotNull
    var quantity: Int? = null
)
