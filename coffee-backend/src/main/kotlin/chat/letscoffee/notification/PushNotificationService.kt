package chat.letscoffee.notification

import com.google.firebase.messaging.*
import org.springframework.stereotype.Component

@Component
class PushNotificationService(
    private val firebaseMessaging: FirebaseMessaging
) {

    fun sendPushNotification(registrationToken: String) {
        val message: Message = Message.builder()
            .setNotification(Notification.builder()
                .setTitle("Default title")
                .setBody("Default body")
                .build())
            .setAndroidConfig(AndroidConfig.builder()
                .setNotification(
                    AndroidNotification.builder()
                        .setTitle("Android title")
                        .setBody("Android body")
                        .build()
                ).build())
            .setWebpushConfig(WebpushConfig.builder()
                .setNotification(WebpushNotification.builder()
                    .setTitle("Web title")
                    .setBody("Web body")
                    .build()
                ).build())
            .setToken(registrationToken)
            .build()

        val response = firebaseMessaging.sendAsync(message).get()
        println("Successfully sent message: $response")
    }
}
