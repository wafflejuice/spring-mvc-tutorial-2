package hello.login.web.interceptor

import hello.login.web.session.LOGIN_MEMBER
import org.slf4j.LoggerFactory
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class LoginCheckInterceptor : HandlerInterceptor {
    private val logger = LoggerFactory.getLogger(LoginCheckInterceptor::class.java)

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val requestURI = request.requestURI

        logger.info("인증 체크 인터셉터 실행 $requestURI")

        val session = request.session

        if (session?.getAttribute(LOGIN_MEMBER) == null) {
            logger.info("미인증 사용자 요청")

            response.sendRedirect("/login?redirectURL=$requestURI")
            return false
        }

        return true
    }
}
