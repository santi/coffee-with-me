package com.coffeewithme

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController;


@RestController("/")
class NotificationResource {

    @GetMapping("subscribe")
    fun subscribe(): String {
        return "Hello world!"
    }
}
