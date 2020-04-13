package chat.letscoffee.meeting

import chat.letscoffee.security.security.CurrentUser
import chat.letscoffee.security.security.UserPrincipal
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/meetings")
class MeetingController(private val service: MeetingService) {

    @PostMapping("/")
    @PreAuthorize("hasRole('ROLE')")
    fun create(
        @CurrentUser userPrincipal: UserPrincipal
    ): MeetingResponse {
        return service.createMeetingRoom()
    }
}
