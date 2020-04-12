package chat.letscoffee.security.security

import chat.letscoffee.user.User
import chat.letscoffee.user.UserService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class CustomUserDetailsService(private val userService: UserService): UserDetailsService {

    @Transactional
    override fun loadUserByUsername(email: String): UserDetails {
        val user: User = userService.getUserByEmail(email)
        return UserPrincipal.create(user)
    }

    @Transactional
    fun loadUserById(id: Long): UserDetails {
        val user: User = userService.getUserById(id)
        return UserPrincipal.create(user)
    }
}
