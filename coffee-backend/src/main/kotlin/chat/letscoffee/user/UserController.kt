package chat.letscoffee.user

import chat.letscoffee.exception.ResourceNotFoundException
import chat.letscoffee.security.security.CurrentUser
import chat.letscoffee.security.security.UserPrincipal
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(private val userRepository: UserRepository) {

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    fun getCurrentUser(@CurrentUser userPrincipal: UserPrincipal): User {
        return userRepository.findByIdOrNull(userPrincipal.id)?: throw ResourceNotFoundException("User", "id", userPrincipal.id)
    }

    @GetMapping("/user/friends")
    @PreAuthorize("hasRole('USER')")
    fun getMyFriends(@CurrentUser userPrincipal: UserPrincipal): Set<User> {
        val user: User =  userRepository.findByIdOrNull(userPrincipal.id)?: throw ResourceNotFoundException("User", "id", userPrincipal.id)
        return user.friends
    }
}
