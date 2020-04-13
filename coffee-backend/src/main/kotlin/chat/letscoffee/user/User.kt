package chat.letscoffee.user

import chat.letscoffee.security.model.AuthProvider
import com.fasterxml.jackson.annotation.*
import javax.persistence.*
import javax.validation.constraints.Email


@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator::class, property = "id")
@Table(name = "user_account", uniqueConstraints = [UniqueConstraint(columnNames = arrayOf("email"))])
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

        @JsonIgnore
        @ManyToMany(cascade = [CascadeType.ALL])
        @JoinTable(name = "user_friend",
            joinColumns = [JoinColumn(name = "user_id_from")],
            inverseJoinColumns = [JoinColumn(name = "user_id_to")])
        val friends: MutableSet<User> = mutableSetOf(),

        @JsonIgnore
        @ManyToMany(mappedBy = "friends")
        val friendOf: MutableSet<User> = mutableSetOf()
) {
        fun addFriend(user: User) {
                friends.add(user)
        }
}





