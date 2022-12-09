package hello.login.web.login

import javax.validation.constraints.NotEmpty

data class LoginForm(
    @field:NotEmpty
    val loginId: String? = null,

    @field:NotEmpty
    val password: String? = null
)
