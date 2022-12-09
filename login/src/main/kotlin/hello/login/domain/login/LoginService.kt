package hello.login.domain.login

import hello.login.domain.member.Member
import hello.login.domain.member.MemberRepository
import org.springframework.stereotype.Service

@Service
class LoginService(
    private val memberRepository: MemberRepository
) {
    fun login(loginId: String, password: String): Member? {
        val findMember = memberRepository.findByLoginId(loginId)
        return if (findMember?.password == password) {
            findMember
        } else {
            null
        }
    }
}
