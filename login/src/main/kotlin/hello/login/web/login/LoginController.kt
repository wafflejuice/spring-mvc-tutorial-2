package hello.login.web.login

import hello.login.domain.login.LoginService
import org.springframework.stereotype.Controller
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import javax.validation.Valid

@Controller
class LoginController(
    private val loginService: LoginService
) {
    @GetMapping("/login")
    fun loginForm(@ModelAttribute("loginForm") form: LoginForm): String {
        return "login/loginForm"
    }

    @PostMapping("/login")
    fun login(@Valid @ModelAttribute("loginForm") form: LoginForm, bindingResult: BindingResult): String {
        if (bindingResult.hasErrors()) {
            return "login/loginForm"
        }

        val loginMember = loginService.login(form.loginId!!, form.password!!)

        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.")
            return "login/loginForm"
        }

        // 로그인 성공 처리
        return "redirect:/"
    }
}
