package chat.letscoffee.users

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/public")
class PublicController {
    @get:GetMapping("/data")
    val data: Map<String, Any>
        get() {
            val response: MutableMap<String, Any> = HashMap()
            response["message"] = "Hello there"
            return response
        }
}