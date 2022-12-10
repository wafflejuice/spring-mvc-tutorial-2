package hello.login.web.session

import hello.login.domain.member.Member
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse

class SessionManagerTest {

    val sessionMamager = SessionManager()

    @Test
    fun sessionTest() {
        val response = MockHttpServletResponse()
        val member = Member()
        sessionMamager.createSession(member, response)

        val request = MockHttpServletRequest()
        request.setCookies(*response.cookies)

        val result = sessionMamager.getSession(request)
        Assertions.assertThat(result).isEqualTo(member)
    }
}
