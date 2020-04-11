package chat.letscoffee.friends

import chat.letscoffee.exception.ResourceNotFoundException
import chat.letscoffee.security.model.User
import chat.letscoffee.security.repository.UserRepository
import chat.letscoffee.security.security.CurrentUser
import chat.letscoffee.security.security.UserPrincipal
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/friendrequest")
class FriendRequestController(private val userRepository: UserRepository, private val service: FriendRequestService) {

    data class FriendRequestPost(val to: String, val workaround: String?);


    @GetMapping("/toMe")
    @PreAuthorize("hasRole('USER')")
    fun getMyFriendRequests(@CurrentUser userPrincipal: UserPrincipal): List<FriendRequestModel> {
        val me: User = userRepository.findByIdOrNull(userPrincipal.id)?: throw ResourceNotFoundException("User", "id", userPrincipal.id!!)
        return service.getMyFriendRequests(me)
    }
    @GetMapping("/fromMe")
    @PreAuthorize("hasRole('USER')")
    fun getMySentFriendRequests(@CurrentUser userPrincipal: UserPrincipal): List<FriendRequestModel> {
        val me: User = userRepository.findByIdOrNull(userPrincipal.id)?: throw ResourceNotFoundException("User", "id", userPrincipal.id!!)
        return service.getMySentFriendRequests(me)
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('USER')")
    fun getMySentFriendRequests(@CurrentUser userPrincipal: UserPrincipal, @RequestBody friendRequest: FriendRequestPost): FriendRequestModel {
        val me: User = userRepository.findByIdOrNull(userPrincipal.id)?: throw ResourceNotFoundException("User", "id", userPrincipal.id!!)
        return service.addFriendRequest(me, friendRequest.to);
    }


    @DeleteMapping("delete")
    @PreAuthorize("hasRole('USER')")
    fun deleteFriendRequest(@CurrentUser userPrincipal: UserPrincipal, @RequestParam("id") id: Long): ResponseEntity<String> {
        val me: User = userRepository.findByIdOrNull(userPrincipal.id)?: throw ResourceNotFoundException("User", "id", userPrincipal.id!!)
        return if (service.deleteRequest(me, id))  ResponseEntity.ok("Request deleted") else ResponseEntity("Friend Request not found or not sent by you", HttpStatus.NOT_FOUND);
    }

}