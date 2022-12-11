package hello.login.web

import hello.login.domain.member.Member
import hello.login.domain.member.MemberRepository
import hello.login.web.session.SessionManager
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.GetMapping
import javax.servlet.http.HttpServletRequest

@Controller
class HomeController(
    private val memberRepository: MemberRepository,
    private val sessionManager: SessionManager
) {
    //    @GetMapping("/")
    fun home(): String {
        return "home"
    }

    //    @GetMapping("/")
    fun homeLogin(
        @CookieValue(name = "memberId", required = false) memberId: Long?,
        model: Model
    ): String {
        memberId ?: return "home"

        // login
        val loginMember = memberRepository.findById(memberId) ?: return "home"

        model.addAttribute("member", loginMember)

        return "loginHome"
    }

    @GetMapping("/")
    fun homeLoginV2(
        request: HttpServletRequest,
        model: Model
    ): String {
        val member = sessionManager.getSession(request) as Member?
            ?: return "home"

        model.addAttribute("member", member)

        return "loginHome"
    }
}
