package chat.letscoffee.users

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import chat.letscoffee.security.SecurityUtils
import chat.letscoffee.users.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController {
    @Autowired
    var securityUtils: SecurityUtils? = null

    @PostMapping("/me")
    fun login(): User? {
        return securityUtils?.principal
    }

    @get:Throws(FirebaseAuthException::class)
    @get:GetMapping("/create/token")
    val customToken: String
        get() = FirebaseAuth.getInstance().createCustomToken(java.lang.String.valueOf(securityUtils?.principal?.uuid))
}