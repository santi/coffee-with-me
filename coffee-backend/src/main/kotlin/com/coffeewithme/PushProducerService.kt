package com.coffeewithme

import com.google.firebase.messaging.*
import org.springframework.stereotype.Component

@Component
class PushProducerService {

    fun sendPushNotification(): String {
        val message: Message = Message.builder()
            .setNotification(Notification("title", "body"))
            .setAndroidConfig(
                AndroidConfig.builder()
                    .setNotification(
                        AndroidNotification.builder()
                            .setBody("Custom Android body")
                            .build()
                    ).build())
            .setWebpushConfig(WebpushConfig.builder()
                .setNotification(
                    WebpushNotification("Web title", "Web Body"))
                .build())
            .setTopic("test-topic")
            .build()

        return FirebaseMessaging.getInstance().sendAsync(message).get()
    }
}
