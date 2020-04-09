package chat.letscoffee.users

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.sun.istack.NotNull
import org.hibernate.annotations.NaturalId
import java.util.*
import javax.persistence.*
import javax.validation.constraints.Size

@Entity @Table(name = "users")
data class User(

        var email: String = "",

        @NaturalId
        @Column(name = "username", unique = true, nullable = false)
        @NotNull
        @Size(min = 3, max = 255)
        var username: String = email,

        @Size(min = 8)
        @get:JsonIgnore
        @set:JsonProperty
        var password: String? = null,
        var name: String = "",

        @OneToMany//(mappedBy = "users", cascade = arrayOf(CascadeType.ALL), fetch = FetchType.LAZY)
        var following: List<User> = emptyList(),

        var drinking: Boolean = false

) {

        @Id
        @GeneratedValue
        var id: Long? = null
}