package chat.letscoffee.security.model

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotNull

@Entity
@Table(name = "users", uniqueConstraints = [UniqueConstraint(columnNames = arrayOf("email"))])
data class User(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,
        @Column(nullable = false)
        var name: String,
        @Column(nullable = false)
        var email: @Email String,
        var imageUrl: String? = null,
        @Column(nullable = false)
        var emailVerified: Boolean = false,
        @JsonIgnore
        var password: String? = null,
        @Enumerated(EnumType.STRING)
        var provider: AuthProvider,
        var providerId: String? = null // Is it used? Do we need it?
)






