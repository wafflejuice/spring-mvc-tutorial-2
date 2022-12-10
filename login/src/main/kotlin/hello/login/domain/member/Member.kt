package hello.login.domain.member

import javax.validation.constraints.NotEmpty

data class Member(
    var id: Long = 0L,

    @field:NotEmpty
    val loginId: String? = null,

    @field:NotEmpty
    val name: String? = null,

    @field:NotEmpty
    val password: String? = null
)
