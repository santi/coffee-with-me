package chat.letscoffee.notification

import nl.martijndwars.webpush.Notification
import nl.martijndwars.webpush.PushService
import org.apache.http.HttpResponse
import org.springframework.stereotype.Component

@Component
class NotificationService(
    private val pushService: PushService
) {

    fun sendPushNotification(sub: NotificationSubscription): HttpResponse {

        val notification = Notification.builder()
            .endpoint(sub.endpoint)
            .userAuth(sub.auth)
            .userPublicKey(sub.userPublicKey)
            .payload("Hello world!")
            .build()

        return pushService.send(notification)
    }
}
