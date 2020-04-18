package chat.letscoffee.notification

import chat.letscoffee.user.User
import org.springframework.data.jpa.repository.JpaRepository

interface NotificationSubscriptionRepository : JpaRepository<NotificationSubscription, Long> {
    fun findByUserIdIn(userIds: Collection<Long>): Collection<NotificationSubscription>
    fun existsByUserId(userId: User): Boolean
}
