package com.coffeewithme

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@RestController("push")
class NotificationResource(
    private val pushNotificationService: PushNotificationService
) {

    @PostMapping("/subscribe")
    fun subscribe(
        @RequestBody pushSubscription: PushSubscription
    ): ResponseEntity<String> {
        val registrationToken = "token" // TODO: Get token from pushSubscription request body
        pushNotificationService.sendPushNotification(registrationToken)
        return ResponseEntity("Successfully subscribed to push notifications", HttpStatus.ACCEPTED)
    }
}
