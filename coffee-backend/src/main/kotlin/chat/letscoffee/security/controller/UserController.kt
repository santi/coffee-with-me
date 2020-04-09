package chat.letscoffee.security.controller

import chat.letscoffee.exception.ResourceNotFoundException
import chat.letscoffee.security.model.User
import chat.letscoffee.security.repository.UserRepository
import chat.letscoffee.security.security.CurrentUser
import chat.letscoffee.security.security.UserPrincipal
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(private val userRepository: UserRepository) {

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    fun getCurrentUser(@CurrentUser userPrincipal: UserPrincipal): User? {
        return userRepository.findById(userPrincipal.getId())
               .orElseThrow({ ResourceNotFoundException("User", "id", userPrincipal.getId()) })
    }
}