package hello.login.web.session

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*
import javax.servlet.http.HttpServletRequest

@RestController
class SessionController {
    @GetMapping("/session-info")
    fun sessionInfo(request: HttpServletRequest): String {
        val session = request.getSession(false)
            ?: return "no session"

        session.attributeNames.asIterator()
            .forEachRemaining { println("session name=$it, value=${session.getAttribute(it)}") }

        println("sessionId=${session.id}")
        println("maxInactiveInterval=${session.maxInactiveInterval}")
        println("creationTime=${Date(session.creationTime)}")
        println("lastAccessedTime=${Date(session.lastAccessedTime)}")
        println("isNew=${session.isNew}")

        return "print session"
    }
}
