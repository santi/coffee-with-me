package chat.letscoffee.notification

data class PushSubscription(
    val endpoint: String,
    val expirationTime: String?,
    val options: PushSubscriptionOptions
)

data class PushSubscriptionOptions(
    val userVisibleOnly: Boolean,
    val applicationServerKey: String
)
