package hello.login.domain.member

import javax.validation.constraints.NotEmpty

data class Member(
    var id: Long = 0L,

    @field:NotEmpty
    val loginId: String?,

    @field:NotEmpty
    val name: String?,

    @field:NotEmpty
    val password: String?
)
