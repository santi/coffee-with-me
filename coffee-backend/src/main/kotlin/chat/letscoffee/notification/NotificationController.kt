package chat.letscoffee.notification

import chat.letscoffee.security.security.CurrentUser
import chat.letscoffee.security.security.UserPrincipal
import chat.letscoffee.user.User
import chat.letscoffee.user.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/notifications")
class NotificationController(
    private val notificationService: NotificationService,
    private val userService: UserService
) {

    @PostMapping("/subscribe")
    fun subscribe(
        @RequestBody notificationSubscription: NotificationSubscription
    ): ResponseEntity<String> {

        // TODO: Save user notification subscription for later use
        val response = notificationService.sendPushNotification(notificationSubscription)
        // TODO: Handle response from PUSH API server gracefully

        return ResponseEntity("Successfully subscribed to push notifications", HttpStatus.ACCEPTED)
    }

    @PostMapping
    fun notifyFriends(
        @CurrentUser userPrincipal: UserPrincipal
    ): ResponseEntity<String> {
        val user: User =  userService.getUserByEmail(userPrincipal.email)

        // TODO: Return the list of users that got the notification?
        return ResponseEntity("Notifications sent", HttpStatus.OK)
    }

}
