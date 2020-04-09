package chat.letscoffee.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.web.util.WebUtils
import javax.servlet.http.HttpServletRequest
import chat.letscoffee.users.User

@Component
class SecurityUtils {
    @Autowired
    var request: HttpServletRequest? = null

    fun getTokenFromRequest(request: HttpServletRequest): String? {
        var token: String? = null
        val cookieToken = WebUtils.getCookie(request, "token")
        if (cookieToken != null) {
            token = cookieToken.value
        } else {
            val bearerToken = request.getHeader("Authorization")
            if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
                token = bearerToken.substring(7, bearerToken.length)
            }
        }
        return token
    }

    val principal: User?
        get() {
            var userPrincipal: User? = null
            val securityContext = SecurityContextHolder.getContext()
            val principal = securityContext.authentication.principal
            if (principal is User) {
                userPrincipal = principal as User
            }
            return userPrincipal
        }
}