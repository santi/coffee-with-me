package chat.letscoffee.notification

import chat.letscoffee.user.User
import nl.martijndwars.webpush.Notification
import nl.martijndwars.webpush.PushService
import org.apache.http.HttpResponse
import org.springframework.stereotype.Component

@Component
class NotificationService(
    private val pushService: PushService,
    private val notificationSubscriptionRepository: NotificationSubscriptionRepository
) {

    fun sendNotifications(users: MutableSet<User>) {
        val userIds = users.mapNotNull { it.id }
        val subscribers = notificationSubscriptionRepository.findByUserIdIn(userIds)
        this.sendNotifications(subscribers)
    }

    fun sendNotifications(subscribers: Collection<NotificationSubscription>) {
        subscribers.forEach(this::sendNotification)
    }

    fun sendNotification(subscriber: NotificationSubscription) {

        val notification = Notification.builder()
            .endpoint(subscriber.endpoint)
            .userAuth(subscriber.auth)
            .userPublicKey(subscriber.userPublicKey)
            .payload("Hello world!")
            .build()

        // TODO: Handle HttpResponse from endpoints, to see if push was actually sent?
        pushService.send(notification)
    }

    fun createSubscription(user: User, request: SubscriptionRequest) {
        val subscription = NotificationSubscription(
            user = user,
            endpoint = request.endpoint,
            //TODO: Should we encrypt auth?
            auth = request.auth,
            publicKey = request.publicKey
        )
        notificationSubscriptionRepository.save(subscription)
    }

}
