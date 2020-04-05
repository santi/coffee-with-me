package chat.letscoffee.users

import java.util.*
import javax.persistence.*

@Entity @Table(name = "users")
data class User(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,
        var name: String = "",
        var email: String = "",
        @OneToMany//(mappedBy = "users", cascade = arrayOf(CascadeType.ALL), fetch = FetchType.LAZY)
        var following: List<User> = emptyList(),

        var drinking: Boolean = false

) {
}