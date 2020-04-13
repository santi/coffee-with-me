package chat.letscoffee.user

import chat.letscoffee.exception.ResourceNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {

    fun getUserByEmail(email: String): User {
        return userRepository.findByEmail(email) ?: throw ResourceNotFoundException("User", "email", email)
    }

    fun getUserById(id: Long): User {
        return userRepository.findByIdOrNull(id) ?: throw ResourceNotFoundException("User", "id", id)
    }

}
