package chat.letscoffee.security

import lombok.Data
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component


@Component
@ConfigurationProperties("security")
@Data
class RestSecurityProperties {
    var alloweddomains: List<String>? = null
    var allowedheaders: List<String>? = null
    var allowedmethods: List<String>? = null
    var allowedpublicapis: Array<String>? = emptyArray()
}