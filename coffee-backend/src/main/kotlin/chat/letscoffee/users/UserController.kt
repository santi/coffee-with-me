package chat.letscoffee.users
import org.springframework.web.bind.annotation.*


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

}