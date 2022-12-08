package hello.login.web.item.form

import org.hibernate.validator.constraints.Range
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class ItemUpdateForm(
    @field:NotNull
    val id: Long = 0L,

    @field:NotBlank
    val itemName: String?,

    @field:NotNull
    @field:Range(min = 1000, max = 1000000)
    val price: Int?,

    //수정에서는 수량은 자유롭게 변경할 수 있다.
    val quantity: Int?
)
