package chat.letscoffee.meeting

import chat.letscoffee.security.security.CurrentUser
import chat.letscoffee.security.security.UserPrincipal
import chat.letscoffee.user.UserService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


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
}
