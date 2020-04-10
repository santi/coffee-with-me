package chat.letscoffee.friends

import chat.letscoffee.friends.FriendRequestModel
import chat.letscoffee.security.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FriendRequestRepository : JpaRepository<FriendRequestModel?, Long?> {
    fun findByFromAndAcceptedIsNull(from: User?): List<FriendRequestModel>
    fun findByToAndAcceptedIsNull(to: User?): List<FriendRequestModel>
    fun existsByFromAndTo(from: User, to: User): Boolean
}