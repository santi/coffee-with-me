package chat.letscoffee.security.model

import com.fasterxml.jackson.annotation.*
import javax.persistence.*
import javax.validation.constraints.Email


@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator::class, property = "id")
@Table(name = "users", uniqueConstraints = [UniqueConstraint(columnNames = arrayOf("email"))])
class User(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,
        @Column(nullable = false)
        var name: String,
        @Column(nullable = false)
        var email: @Email String,
        var imageUrl: String? = null,
        @Column(nullable = false)
        @JsonIgnore
        var emailVerified: Boolean = false,
        @JsonIgnore
        var password: String? = null,
        @Enumerated(EnumType.STRING)
        @JsonIgnore
        var provider: AuthProvider,
        @JsonIgnore
        var providerId: String? = null, // Is it used? Do we need it?

        @ManyToMany(cascade = [CascadeType.ALL])
        @JsonIgnore
        val friends: MutableSet<User> = mutableSetOf(),

        @JsonIgnore
        @ManyToMany(mappedBy="friends")
        val friendOf: MutableSet<User> = mutableSetOf()
) {
        fun addFriend(user: User) {
                friends.add(user)
        }
}





