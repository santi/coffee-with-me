package chat.letscoffee.user

import chat.letscoffee.security.model.AuthProvider
import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.ObjectIdGenerators
import java.time.Instant
import javax.persistence.*
import javax.validation.constraints.Email


@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator::class, property = "id")
@Table(name = "user_account", uniqueConstraints = [UniqueConstraint(columnNames = arrayOf("email"), name = "user_account_email_uix")])
class User(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(columnDefinition = "TEXT", nullable = false)
    var name: String,

    @Column(columnDefinition = "TEXT", nullable = false)
    var email: @Email String,

    @JsonIgnore
    @Column(nullable = false)
    var emailVerified: Boolean = false,

    @JsonIgnore
    @Column(columnDefinition = "TEXT")
    var password: String? = null,

    @Column(columnDefinition = "TEXT")
    var imageUrl: String? = null,

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "TEXT", nullable = false)
    var provider: AuthProvider,

    @JsonIgnore
    @Column(columnDefinition = "TEXT")
    var providerId: String? = null, // TODO: Is it used? Do we need it?

    @JsonIgnore
    @ManyToMany(cascade = [CascadeType.ALL])
    @JoinTable(name = "user_friend",
        joinColumns = [JoinColumn(name = "user_id_from", foreignKey = ForeignKey(name = "user_friend_user_id_from_fkey"))],
        inverseJoinColumns = [JoinColumn(name = "user_id_to", foreignKey = ForeignKey(name = "user_friend_user_id_to_fkey"))])
    val friends: MutableSet<User> = mutableSetOf(),

    @JsonIgnore
    @ManyToMany(mappedBy = "friends")
    val friendOf: MutableSet<User> = mutableSetOf(),

    @JsonIgnore
    @Column(nullable = false)
    val created: Instant = Instant.now()

) {
    fun addFriend(user: User) {
        friends.add(user)
    }
}





