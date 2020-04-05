package com.coffeewithme

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController;


@RestController("/")
class NotificationResource(private val service: GetMeetingRoomService) {

    @GetMapping("subscribe")
    fun subscribe(): String {
        return "Hello world!"
    }

    @GetMapping("create")
    fun create(): String {
        return service.getMeetingRoom();
    }
}
