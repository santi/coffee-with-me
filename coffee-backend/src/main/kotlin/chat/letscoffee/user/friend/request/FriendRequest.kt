package chat.letscoffee.user.friend.request

import chat.letscoffee.user.User
import java.time.Instant
import java.time.Instant.now
import javax.persistence.*


@Entity
@Table(
    name = "friend_request",
    indexes = [Index(columnList = "user_id_from,user_id_to", name = "friend_request_user_id_from_user_id_to_uix", unique = true)])
data class FriendRequest(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "user_id_from", foreignKey = ForeignKey(name = "friend_request_user_id_from_fkey"))
    val from: User,

    @ManyToOne
    @JoinColumn(name = "user_id_to", foreignKey = ForeignKey(name = "friend_request_user_id_to_fkey"))
    val to: User,

    @Column(nullable = false)
    val created: Instant = now(),

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "TEXT", nullable = false)
    var status: FriendRequestStatus = FriendRequestStatus.PENDING,

    @Column
    var responded: Instant? = null
)

enum class FriendRequestStatus {
    PENDING, ACCEPTED, REJECTED
}
