package hello.login.web

import hello.login.domain.member.MemberRepository
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HomeController(
    private val memberRepository: MemberRepository
) {
    //    @GetMapping("/")
    fun home(): String {
        return "home"
    }

    @GetMapping("/")
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
}
