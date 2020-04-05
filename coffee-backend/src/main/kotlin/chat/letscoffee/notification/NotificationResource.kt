package chat.letscoffee.notification

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/push")
class NotificationResource(
    private val pushNotificationService: PushNotificationService
) {

    @PostMapping("/subscribe")
    fun subscribe(
        @RequestBody subscription: Subscription
    ): ResponseEntity<String> {

        val response = pushNotificationService.sendPushNotification(subscription)
        // TODO: Handle response from PUSH API server gracefully

        return ResponseEntity("Successfully subscribed to push notifications", HttpStatus.ACCEPTED)
    }
}
