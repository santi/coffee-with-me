package chat.letscoffee.security

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseToken
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import chat.letscoffee.users.User


@Slf4j
class TokenFilter : OncePerRequestFilter() {
    @Autowired
    var securityUtils: SecurityUtils? = null
    @Autowired
    var restSecProps: RestSecurityProperties? = null

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val path = request.requestURI
        if (!restSecProps!!.allowedpublicapis!!.contains(path)) {
            val idToken = securityUtils!!.getTokenFromRequest(request)
            var decodedToken: FirebaseToken? = null
            if (idToken != null) {
                try {
                    decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken)
                } catch (e: FirebaseAuthException) {
                    // log.error("Firebase Exception:: ", e.localizedMessage)
                }
            }
            if (decodedToken != null) {
                val user = User()
                user.uid = (decodedToken.uid)
                user.name = (decodedToken.name)
                user.email = (decodedToken.email)
    //            user.setPicture(decodedToken.picture)
   //             user.setIssuer(decodedToken.issuer)
   //             user.setEmailVerified(decodedToken.isEmailVerified)
                val authentication = UsernamePasswordAuthenticationToken(user,
                        decodedToken, null)
                authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authentication
            }
        }
        filterChain.doFilter(request, response)
    }
}