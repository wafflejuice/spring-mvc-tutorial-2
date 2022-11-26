package hello.thymeleaf.basic

import org.springframework.stereotype.Component
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import java.time.LocalDateTime
import javax.servlet.http.HttpSession

@Controller
@RequestMapping("/basic")
class BasicController {
    @GetMapping("text-basic")
    fun textBasic(model: Model): String {
        model.addAttribute("data", "Hello <b>Spring!</b>")
        return "basic/text-basic"
    }

    @GetMapping("text-unescaped")
    fun textUnexcaped(model: Model): String {
        model.addAttribute("data", "Hello <b>Spring!</b>")
        return "basic/text-unescaped"
    }

    @GetMapping("/variable")
    fun variable(model: Model): String {
        val userA = User("userA", 10)
        val userB = User("userB", 20)

        val list = listOf(userA, userB)
        val map = mapOf("userA" to userA, "userB" to userB)

        model.addAttribute("user", userA)
        model.addAttribute("users", list)
        model.addAttribute("userMap", map)

        return "basic/variable"
    }

    @GetMapping("/basic-objects")
    fun basicObjects(session: HttpSession): String {
        session.setAttribute("sessionData", "Hello Session")
        return "basic/basic-objects"
    }

    companion object {
        @Component("helloBean")
        class HelloBean {
            fun hello(data: String): String {
                return "Hello " + data
            }
        }
    }

    @GetMapping("/date")
    fun date(model: Model): String {
        model.addAttribute("localDateTime", LocalDateTime.now())
        return "basic/date"
    }

    data class User(
        val username: String,
        val age: Int
    )
}
