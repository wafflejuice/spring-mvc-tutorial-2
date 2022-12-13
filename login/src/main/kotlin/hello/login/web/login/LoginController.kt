package hello.login.web.login

import hello.login.domain.login.LoginService
import hello.login.web.session.LOGIN_MEMBER
import hello.login.web.session.SessionManager
import org.springframework.stereotype.Controller
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.validation.Valid
import kotlin.concurrent.thread

@Controller
class LoginController(
    private val loginService: LoginService,
    private val sessionManager: SessionManager
) {
    @GetMapping("/login")
    fun loginForm(@ModelAttribute("loginForm") form: LoginForm): String {
        thread {

        }
        return "login/loginForm"
    }

    //    @PostMapping("/login")
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

    //    @PostMapping("/login")
    fun loginV2(
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

        // 세션 관리자를 통해 세션을 생성하고, 회원 데이터 보관
        sessionManager.createSession(loginMember, response)

        return "redirect:/"
    }

    //    @PostMapping("/login")
    fun loginV3(
        @Valid @ModelAttribute("loginForm") form: LoginForm,
        bindingResult: BindingResult,
        request: HttpServletRequest
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

        // 세션이 있으면 반환, 없으면 신규 세션을 반환
        // 세션에 로그인 회원 정보 보관
        request.session.setAttribute(LOGIN_MEMBER, loginMember)

        return "redirect:/"
    }

    @PostMapping("/login")
    fun loginV4(
        @Valid @ModelAttribute("loginForm") form: LoginForm,
        bindingResult: BindingResult,
        @RequestParam(defaultValue = "/") redirectURL: String,
        request: HttpServletRequest
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

        // 세션이 있으면 반환, 없으면 신규 세션을 반환
        // 세션에 로그인 회원 정보 보관
        request.session.setAttribute(LOGIN_MEMBER, loginMember)

        return "redirect:$redirectURL"
    }

    //    @PostMapping("/logout")
    fun logout(response: HttpServletResponse): String {
        extractCookie(response, "memberId")
        return "redirect:/"
    }

    //    @PostMapping("/logout")
    fun logoutV2(request: HttpServletRequest): String {
        sessionManager.expire(request)
        return "redirect:/"
    }

    @PostMapping("/logout")
    fun logoutV3(request: HttpServletRequest): String {
        request.getSession(false)?.invalidate()
        return "redirect:/"
    }

    private fun extractCookie(response: HttpServletResponse, cookieName: String) {
        val cookie = Cookie(cookieName, null)
        cookie.maxAge = 0
        response.addCookie(cookie)
    }
}
