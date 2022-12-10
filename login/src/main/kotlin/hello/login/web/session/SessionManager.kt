package hello.login.web.session

import java.util.*
import java.util.concurrent.ConcurrentHashMap
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

private const val SESSION_COOKIE_NAME = "mySessionId"

class SessionManager(
    private val sessionStore: MutableMap<String, Any> = ConcurrentHashMap()
) {
    fun createSession(value: Any, response: HttpServletResponse) {
        val sessionId = UUID.randomUUID().toString()
        sessionStore[sessionId] = value

        val mySessionCookie = Cookie(SESSION_COOKIE_NAME, sessionId)
        response.addCookie(mySessionCookie)
    }

    fun getSession(request: HttpServletRequest): Any? {
        val sessionCookie = findCookie(request, SESSION_COOKIE_NAME)
            ?: return null
        
        return sessionStore[sessionCookie.value]
    }

    fun expire(request: HttpServletRequest) {
        val sessionCookie = findCookie(request, SESSION_COOKIE_NAME)
        if (sessionCookie != null) {
            sessionStore.remove(sessionCookie.value)
        }
    }

    private fun findCookie(request: HttpServletRequest, cookieName: String) =
        request.cookies.find { it.name == cookieName }
}
