package chat.letscoffee.security.model

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotNull

@Entity
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    @Column(nullable = false)
    var name: String? = null
    @Column(nullable = false)
    var email: @Email String? = null
    var imageUrl: String? = null
    @Column(nullable = false)
    var emailVerified = false
    @JsonIgnore
    var password: String? = null
    @Enumerated(EnumType.STRING)
    var provider: @NotNull AuthProvider? = null
    var providerId: String? = null

}