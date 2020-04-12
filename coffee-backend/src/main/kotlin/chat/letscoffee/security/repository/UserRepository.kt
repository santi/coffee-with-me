package chat.letscoffee.security.repository

import chat.letscoffee.security.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<User?, Long?> {
    fun existsByEmail(email: String?): Boolean
    fun findByEmail(email: String): User?
}