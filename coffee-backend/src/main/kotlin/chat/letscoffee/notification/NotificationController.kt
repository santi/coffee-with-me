package chat.letscoffee.notification

import chat.letscoffee.security.security.CurrentUser
import chat.letscoffee.security.security.UserPrincipal
import chat.letscoffee.user.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/notifications")
class NotificationController(
    private val notificationService: NotificationService,
    private val notificationSubscriptionRepository: NotificationSubscriptionRepository,
    private val userService: UserService
) {

    @PostMapping("/subscribe")
    @PreAuthorize("hasRole('USER')")
    fun subscribe(
        @CurrentUser userPrincipal: UserPrincipal,
        @RequestBody subscriptionRequest: SubscriptionRequest
    ): ResponseEntity<String> {
        val user = userService.getUserById(userPrincipal.id)

        if (notificationSubscriptionRepository.existsByUserId(user)) {
            return ResponseEntity.ok("User is already subscribed to push notifications")
        }
        notificationService.createSubscription(user, subscriptionRequest)
        return ResponseEntity.ok("Successfully subscribed to push notifications")
    }

    @PostMapping("/send")
    @PreAuthorize("hasRole('USER')")
    fun notifyFriends(
        @CurrentUser userPrincipal: UserPrincipal
    ): ResponseEntity<String> {
        val friends = userService.getUserById(userPrincipal.id).friends
        notificationService.sendNotifications(friends)


        // TODO: Return the list of users that got the notification?
        return ResponseEntity("Notifications sent", HttpStatus.OK)
    }

    // TODO: remove when application is thoroughly tested
    @PostMapping("/test")
    @PreAuthorize("hasRole('USER')")
    fun sendtestNotification(
        @CurrentUser userPrincipal: UserPrincipal,
        @RequestBody request: SubscriptionRequest
    ): ResponseEntity<String> {
        val user = userService.getUserById(userPrincipal.id)
        val subscription = NotificationSubscription(
            user = user,
            endpoint = request.endpoint,
            auth = request.auth,
            publicKey = request.publicKey
        )
        
        notificationService.sendNotification(subscription)
        return ResponseEntity("Test notifications sent", HttpStatus.OK)
    }

}
