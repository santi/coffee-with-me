package chat.letscoffee.user.friend.request

import chat.letscoffee.user.User
import javax.persistence.*


@Entity
@Table(name = "friend_request")
data class FriendRequest(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @ManyToOne
    val from: User,
    @ManyToOne
    val to: User,
    @Column
    var accepted: Boolean? = null
)
