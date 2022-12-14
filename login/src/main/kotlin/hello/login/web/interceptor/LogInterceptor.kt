package hello.login.web.interceptor

import hello.login.web.filter.LogFilter
import org.slf4j.LoggerFactory
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

private const val LOG_ID = "logId"

class LogInterceptor : HandlerInterceptor {
    private val logger = LoggerFactory.getLogger(LogFilter::class.java)

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val requestURI = request.requestURI
        val logId = UUID.randomUUID().toString()

        request.setAttribute(LOG_ID, logId)

        if (handler is HandlerMethod) {
            val handlerMethod = handler // 호출할 컨트롤러 메서드의 모든 정보가 포함되어 있다.
        }

        logger.info("REQUEST [$logId][$requestURI][$handler]")

        return true
    }

    override fun postHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        modelAndView: ModelAndView?
    ) {
        logger.info("postHandle [$modelAndView]")
    }

    override fun afterCompletion(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        ex: Exception?
    ) {
        val requestURI = request.requestURI
        val logId = request.getAttribute(LOG_ID).toString()
        logger.info("RESPONSE [$logId][$requestURI][$handler]")

        if (ex != null) {
            logger.error("afterCompletion error", ex)
        }
    }
}
