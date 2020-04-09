package chat.letscoffee.security
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.sql.Timestamp
import java.util.*
import kotlin.collections.HashMap


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
class RestSecurityConfig : WebSecurityConfigurerAdapter() {
    @Autowired
    var objectMapper: ObjectMapper? = null
    @Autowired
    var restSecProps: RestSecurityProperties? = null

    @Bean
    fun tokenAuthenticationFilter(): TokenFilter {
        return TokenFilter()
    }

    @Bean
    fun restAuthenticationEntryPoint(): AuthenticationEntryPoint {
        return AuthenticationEntryPoint { httpServletRequest, httpServletResponse, e ->
            val errorObject: MutableMap<String, Any> = HashMap()
            val errorCode = 401
            errorObject["message"] = "Access Denied"
            errorObject["error"] = HttpStatus.UNAUTHORIZED
            errorObject["code"] = errorCode
            errorObject["timestamp"] = Timestamp(Date().time)
            httpServletResponse.contentType = "application/json;charset=UTF-8"
            httpServletResponse.status = errorCode
            httpServletResponse.writer.write(objectMapper!!.writeValueAsString(errorObject))
        }
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.cors().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().csrf()
                .disable().formLogin().disable().httpBasic().disable().exceptionHandling()
                .authenticationEntryPoint(restAuthenticationEntryPoint()).and().authorizeRequests()
                .antMatchers("/public/data", "/create/token").permitAll()
                .anyRequest().authenticated()
        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter::class.java)
    }
}