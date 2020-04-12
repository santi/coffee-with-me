package chat.letscoffee.user

import chat.letscoffee.security.security.CurrentUser
import chat.letscoffee.security.security.UserPrincipal
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(private val userService: UserService) {

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    fun getCurrentUser(@CurrentUser userPrincipal: UserPrincipal): User {
        return userService.getUserById(userPrincipal.id)
    }

    @GetMapping("/user/friends")
    @PreAuthorize("hasRole('USER')")
    fun getMyFriends(@CurrentUser userPrincipal: UserPrincipal): Set<User> {
        return userService.getUserById(userPrincipal.id).friends
    }
}
