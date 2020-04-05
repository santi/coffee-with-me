package chat.letscoffee.meetings

import chat.letscoffee.meetings.GetMeetingRoomService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/meetings")
class MeetingController(private val service: GetMeetingRoomService) {


    @GetMapping("/create")
    fun create(): String {
        return service.getMeetingRoom();
    }
}
