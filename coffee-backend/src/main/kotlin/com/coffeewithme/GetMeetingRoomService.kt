package com.coffeewithme
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.gson.responseObject
import org.springframework.stereotype.Component


@Component
class GetMeetingRoomService {

    fun getMeetingRoom(): String {

        val bodyJson = """
  { "Title" : "Coffee With Me",
  }
"""

        val (request, response, result) = Fuel.post("https://api.join.skype.com/v1/meetnow/createjoinlinkguest").header("Content-Type" to "application/json").body(bodyJson).responseObject<MeetingResponse>();


        return result.get().joinLink;


    }
}
