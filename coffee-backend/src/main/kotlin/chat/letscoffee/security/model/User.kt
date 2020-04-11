package chat.letscoffee.security.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import lombok.EqualsAndHashCode
import lombok.ToString
import javax.persistence.*
import javax.validation.constraints.Email


@ToString(exclude = arrayOf("friends"))
@EqualsAndHashCode(exclude = arrayOf("friends"))
@NamedEntityGraph(name="graph.Person.friends", // Creating a graph to help hibernate to create a query with outer join.
        attributeNodes = arrayOf(NamedAttributeNode("friends")))
@Entity
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
        @JsonIgnore
        @ManyToMany(cascade = arrayOf(CascadeType.ALL))
        @JoinTable(name="friends",
                joinColumns = arrayOf(JoinColumn(name = "friend1_id", referencedColumnName = "id")),
                inverseJoinColumns = arrayOf(JoinColumn(name = "friend2_id", referencedColumnName = "id")))
        val friends: MutableSet<User> = mutableSetOf<User>()


)



{
        fun addFriend(user: User) {
                friends.add(user)
        }
}





