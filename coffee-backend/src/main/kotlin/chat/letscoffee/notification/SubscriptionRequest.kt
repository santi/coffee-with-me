package chat.letscoffee.notification

data class SubscriptionRequest (
    var endpoint: String,
    val auth: String,
    val publicKey: String
)
