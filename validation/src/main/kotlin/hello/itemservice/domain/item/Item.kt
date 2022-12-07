package hello.itemservice.domain.item

import org.hibernate.validator.constraints.Range
import javax.validation.constraints.Max
import javax.validation.constraints.NotBlank

data class Item(
    var id: Long? = null,

    @NotBlank
    var itemName: String? = null,

    @Range(min = 1000, max = 1000000)
    var price: Int? = null,

    @Max(9999)
    var quantity: Int? = null
)
