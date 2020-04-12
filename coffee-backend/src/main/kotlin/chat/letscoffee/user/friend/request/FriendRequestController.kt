package chat.letscoffee.user.friend.request

import chat.letscoffee.security.security.CurrentUser
import chat.letscoffee.security.security.UserPrincipal
import chat.letscoffee.user.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/friends/requests")
class FriendRequestController(private val userService: UserService, private val friendRequestService: FriendRequestService) {

    data class FriendRequestPost(val to: String, val workaround: String?)


    @GetMapping("/toMe")
    @PreAuthorize("hasRole('USER')")
    fun getRequestsTo(@CurrentUser userPrincipal: UserPrincipal): List<FriendRequest> {
        val me = userService.getUserById(userPrincipal.id)
        return friendRequestService.getRequestsTo(me)
    }

    @GetMapping("/fromMe")
    @PreAuthorize("hasRole('USER')")
    fun getRequestsFrom(@CurrentUser userPrincipal: UserPrincipal): List<FriendRequest> {
        val me = userService.getUserById(userPrincipal.id)
        return friendRequestService.getRequestsFrom(me)
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    fun createFriendRequest(@CurrentUser userPrincipal: UserPrincipal, @RequestBody friendRequest: FriendRequestPost): FriendRequest {
        val me = userService.getUserById(userPrincipal.id)
        val friend = userService.getUserByEmail(friendRequest.to)
        return friendRequestService.addFriendRequest(me, friend)
    }


    @DeleteMapping("/{friendRequestId}")
    @PreAuthorize("hasRole('USER')")
    fun deleteFriendRequest(@CurrentUser userPrincipal: UserPrincipal, @PathVariable("friendRequestId") id: Long): ResponseEntity<String> {
        val me = userService.getUserById(userPrincipal.id)
        return if (friendRequestService.deleteRequest(me, id)) ResponseEntity.ok("Request deleted") else ResponseEntity("Friend Request not found or not sent by you", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{friendRequestId}/accept")
    @PreAuthorize("hasRole('USER')")
    fun acceptFriendRequest(@CurrentUser userPrincipal: UserPrincipal, @PathVariable("friendRequestId") id: Long): ResponseEntity<String> {
        val me = userService.getUserById(userPrincipal.id)
        friendRequestService.acceptRequest(me, id)
        return ResponseEntity.ok("Request accepted")
    }

    @GetMapping("/{friendRequestId}/reject")
    @PreAuthorize("hasRole('USER')")
    fun rejectFriendRequest(@CurrentUser userPrincipal: UserPrincipal, @PathVariable("friendRequestId") id: Long): ResponseEntity<String> {
        val me = userService.getUserById(userPrincipal.id)
        friendRequestService.rejectRequest(me, id)
        return ResponseEntity.ok("Request rejected")
    }


}
