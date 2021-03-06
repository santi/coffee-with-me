package chat.letscoffee.meeting

import chat.letscoffee.exception.ResourceNotFoundException
import chat.letscoffee.exception.ServiceUnavailableException
import chat.letscoffee.user.User
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.gson.responseObject
import org.springframework.stereotype.Component


@Component
class MeetingService(
    private val meetingRepository: MeetingRepository
) {

    fun createMeetingRoom(user: User): Meeting {

        val bodyJson = "{\"Title\" : \"Coffee With Me\"}"
        val (request, response, result) = Fuel
            .post("https://api.join.skype.com/v1/meetnow/createjoinlinkguest")
            .header("Content-Type" to "application/json")
            .body(bodyJson)
            .responseObject<MeetingResponse>()

        return result.fold(
            {
                var meeting = Meeting(
                    owner = user,
                    link = it.joinLink,
                    provider = MeetingProviderType.SKYPE
                )
                meeting = meetingRepository.save(meeting)
                return@fold meeting
            },
            { throw ServiceUnavailableException("Could not create Skype meeting room.", it) }
        )
    }

    fun getMeetingRoomById(id: Long): Meeting {
        return meetingRepository.findByIdAndActiveTrue(id) ?: throw ResourceNotFoundException("Meeting", "id", id)
    }

    fun deactivateMeeting(meeting: Meeting): Meeting {
        meeting.active = false
        return meetingRepository.save(meeting)
    }
}
