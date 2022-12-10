package hello.login.web.session

import hello.login.domain.member.Member
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse

class SessionManagerTest {

    private val sessionManager = SessionManager()

    @Test
    fun sessionTest() {
        val response = MockHttpServletResponse()
        val member = Member()
        sessionManager.createSession(member, response)

        val request = MockHttpServletRequest()
        request.setCookies(*response.cookies)

        val result = sessionManager.getSession(request)
        Assertions.assertThat(result).isEqualTo(member)

        sessionManager.expire(request)
        val expired = sessionManager.getSession(request)
        Assertions.assertThat(expired).isNull()
    }
}
