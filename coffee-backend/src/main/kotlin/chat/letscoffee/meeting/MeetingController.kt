package chat.letscoffee.meeting

import chat.letscoffee.exception.ResourceNotFoundException
import chat.letscoffee.security.security.CurrentUser
import chat.letscoffee.security.security.UserPrincipal
import chat.letscoffee.user.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/meetings")
class MeetingController(
    private val meetingService: MeetingService,
    private val userService: UserService
) {

    @PostMapping("/")
    @PreAuthorize("hasRole('ROLE')")
    fun create(
        @CurrentUser userPrincipal: UserPrincipal
    ): MeetingResponse {
        val user = userService.getUserById(userPrincipal.id)
        return meetingService.createMeetingRoom(user)
    }

    @DeleteMapping("/{meetingRoomId}")
    @PreAuthorize("hasRole('ROLE')")
    fun delete(
        @CurrentUser userPrincipal: UserPrincipal,
        @PathVariable meetingRoomId: Long
    ): ResponseEntity<String> {
        val meeting = meetingService.getMeetingRoomById(meetingRoomId)
        val user = userService.getUserById(userPrincipal.id)
        if (meeting.owner != user) {
            throw ResourceNotFoundException("Meeting", "id, owner", "$meetingRoomId, ${user.id}")
        }
        meeting.active = false
        meetingService.update(meeting)

        return ResponseEntity.ok("Meeting room $meetingRoomId deleted.")
    }

}
