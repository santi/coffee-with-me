package chat.letscoffee.users

import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("/users")
class UserController(private val repository: UserRepository) {




    @GetMapping("/friends")
    fun getFriends(): String {
        return "Hey friends";
    }

    @GetMapping("/all")
    fun getAllUsers(): List<User> {
        val users = repository.findAll();

        return users;
    }

    @PostMapping("/signup")
    fun signUp(@RequestBody user: User): String {
        val res = repository.save(user);
        return "ok"
    }

    @GetMapping("/follow")
    fun followUser(@RequestParam(value  = "user") user: String): String {
        val user = repository.findByEmail(user);
        return "Cool";

    }

    @GetMapping("/user")
    fun user(@AuthenticationPrincipal principal: OAuth2User): Map<String?, Any?>? {
        return Collections.singletonMap("name", principal.getAttribute("name"))
    }

}