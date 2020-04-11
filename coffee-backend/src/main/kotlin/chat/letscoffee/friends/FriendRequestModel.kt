package chat.letscoffee.friends

import chat.letscoffee.security.model.User
import javax.persistence.*


@Entity
@Table(name = "friendrequest")
class FriendRequestModel(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,
        @ManyToOne
        val from: User,
        @ManyToOne
        val to: User,
        @Column
        var accepted: Boolean? = null) {




}