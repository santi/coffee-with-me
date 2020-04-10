package chat.letscoffee.friends

import chat.letscoffee.security.model.User
import javax.persistence.*


@Entity
@Table(name = "friendrequest")
class FriendRequestModel() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @ManyToOne
    var from: User? = null
    @ManyToOne
    var to: User? = null

    @Column
    var accepted: Boolean? = null
}