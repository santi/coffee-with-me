package chat.letscoffee.util

import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest
import org.springframework.util.SerializationUtils
import java.util.*
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CookieUtils {

    companion object {
        fun getCookie(request: HttpServletRequest, name: String): Cookie? {
            return request.cookies?.firstOrNull { it.name == name }
        }

        fun addCookie(response: HttpServletResponse, name: String, value: String, maxAge: Int) {
            val cookie = Cookie(name, value)
            cookie.path = "/"
            cookie.isHttpOnly = true
            cookie.maxAge = maxAge
            // TODO: uncomment secure and httpOnly in production
            //cookie.secure = true
            //cookie.isHttpOnly = true
            response.addCookie(cookie)
        }

        fun deleteCookie(request: HttpServletRequest, response: HttpServletResponse, name: String) {
            val cookie = request.cookies?.firstOrNull{ it.name == name }
            if (cookie != null) {
                cookie.value = null
                cookie.path = "/"
                cookie.maxAge = 0
                response.addCookie(cookie)
            }
        }

        fun serialize(request: OAuth2AuthorizationRequest?): String {
            return Base64.getUrlEncoder()
                    .encodeToString(SerializationUtils.serialize(request))
        }

        fun <T> deserialize(cookie: Cookie, cls: Class<T>): T {
            return cls.cast(SerializationUtils.deserialize(
                    Base64.getUrlDecoder().decode(cookie.value)))
        }
    }
}
