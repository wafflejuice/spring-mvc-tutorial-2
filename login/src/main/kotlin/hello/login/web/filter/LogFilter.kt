package hello.login.web.filter

import org.slf4j.LoggerFactory
import java.util.*
import javax.servlet.*
import javax.servlet.http.HttpServletRequest

class LogFilter : Filter {
    private val logger = LoggerFactory.getLogger(LogFilter::class.java)

    override fun init(filterConfig: FilterConfig) {
        logger.info("log filter init")
    }

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        logger.info("log filter doFilter")

        val httpRequest = request as HttpServletRequest
        val requestURI = httpRequest.requestURI

        val uuid = UUID.randomUUID().toString()

        try {
            logger.info("REQUEST [$uuid][$requestURI]")
            chain?.doFilter(request, response)
        } catch (e: Exception) {
            throw e
        } finally {
            logger.info("RESPONSE [$uuid][$requestURI]")
        }
    }

    override fun destroy() {
        logger.info("log filter destroy")
    }
}
