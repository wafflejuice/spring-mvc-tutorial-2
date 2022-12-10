package hello.login.web.login

import hello.login.domain.login.LoginService
import org.springframework.stereotype.Controller
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse
import javax.validation.Valid
import kotlin.concurrent.thread

@Controller
class LoginController(
    private val loginService: LoginService
) {
    @GetMapping("/login")
    fun loginForm(@ModelAttribute("loginForm") form: LoginForm): String {
        thread {

        }
        return "login/loginForm"
    }

    @PostMapping("/login")
    fun login(
        @Valid @ModelAttribute("loginForm") form: LoginForm,
        bindingResult: BindingResult,
        response: HttpServletResponse
    ): String {
        if (bindingResult.hasErrors()) {
            return "login/loginForm"
        }

        val loginMember = loginService.login(form.loginId!!, form.password!!)

        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.")
            return "login/loginForm"
        }

        // 로그인 성공 처리

        // 쿠키에 시간 정보를 주지 않으면 세션 쿠키(브라우저 종료시 모두 종료)
        val idCookie = Cookie("memberId", loginMember.id.toString())
        response.addCookie(idCookie)

        return "redirect:/"
    }

    @PostMapping("/logout")
    fun logout(response: HttpServletResponse): String {
        extractCookie(response, "memberId")
        return "redirect:/"
    }

    private fun extractCookie(response: HttpServletResponse, cookieName: String) {
        val cookie = Cookie(cookieName, null)
        cookie.maxAge = 0
        response.addCookie(cookie)
    }
}
