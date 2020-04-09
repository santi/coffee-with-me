package chat.letscoffee.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import java.io.IOException
import javax.servlet.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class RestSecurityFilter : Filter {
    @Autowired
    var restSecProps: RestSecurityProperties? = null

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(req: ServletRequest, res: ServletResponse, chain: FilterChain) {
        val response = res as HttpServletResponse
        val request = req as HttpServletRequest
        val allowedMethods: String = java.lang.String.join(", ", restSecProps?.allowedmethods)
        val allowedDomains: String = java.lang.String.join(", ", restSecProps?.alloweddomains)
        val allowedHeaders: String = java.lang.String.join(", ", restSecProps?.allowedheaders)
        response.setHeader("Access-Control-Allow-Methods", allowedMethods)
        response.setHeader("Access-Control-Allow-Origin", allowedDomains)
        response.setHeader("Access-Control-Allow-Credentials", "true")
        response.setHeader("Access-Control-Allow-Headers", allowedHeaders)
        if ("OPTIONS".equals(request.method, ignoreCase = true)) {
            response.status = HttpServletResponse.SC_OK
        } else {
            chain.doFilter(req, res)
        }
    }
}