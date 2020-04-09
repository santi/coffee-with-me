package chat.letscoffee.notification

import nl.martijndwars.webpush.Notification
import nl.martijndwars.webpush.PushService
import org.apache.http.HttpResponse
import org.springframework.stereotype.Component

@Component
class PushNotificationService(
    private val pushService: PushService
) {

    fun sendPushNotification(sub: Subscription): HttpResponse {

        val notification = Notification.builder()
            .endpoint(sub.endpoint)
            .userAuth(sub.auth)
            .userPublicKey(sub.userPublicKey)
            .payload("Hello world!")
            .build()

        return pushService.send(notification)
    }
}
