package chat.letscoffee.security.security

import chat.letscoffee.exception.ResourceNotFoundException
import chat.letscoffee.security.model.User
import chat.letscoffee.security.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class CustomUserDetailsService(var userRepository: UserRepository) : UserDetailsService {

    @Transactional
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(email: String): UserDetails {
        val user = userRepository.findByEmail(email)
                .orElseThrow { UsernameNotFoundException("User not found with email : $email") }
        return UserPrincipal.create(user)
    }

    @Transactional
    fun loadUserById(id: Long): UserDetails {
        val user: User = userRepository.findByIdOrNull(id) ?: throw ResourceNotFoundException("User", "id", id)
        return UserPrincipal.create(user)
    }
}