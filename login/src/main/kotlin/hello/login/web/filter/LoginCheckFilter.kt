package hello.login.web.filter

import hello.login.web.session.LOGIN_MEMBER
import org.slf4j.LoggerFactory
import org.springframework.util.PatternMatchUtils
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class LoginCheckFilter : Filter {
    private val logger = LoggerFactory.getLogger(LogFilter::class.java)

    private val whitelist = arrayOf("/", "/members/add", "/login", "/logout", "/css/*")

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        val httpRequest = request as HttpServletRequest
        val requestURI = httpRequest.requestURI

        val httpResponse = response as HttpServletResponse

        try {
            logger.info("인증 체크 필터 시작 $requestURI")

            if (isLoginCheckPath(requestURI)) {
                logger.info("인증 체크 로직 실행 $requestURI")
                val session = httpRequest.getSession(false)

                if (session?.getAttribute(LOGIN_MEMBER) == null) {
                    logger.info("미인증 사용자 요청 $requestURI")
                    httpResponse.sendRedirect("/login?redirectURL=$requestURI")
                }
            }

            chain?.doFilter(request, response)
        } catch (e: Exception) {
            throw e
        } finally {
            logger.info("인증 체크 필터 종료 $requestURI")
        }
    }

    private fun isLoginCheckPath(requestURI: String): Boolean {
        return PatternMatchUtils.simpleMatch(whitelist, requestURI).not()
    }
}
