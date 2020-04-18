package chat.letscoffee.user.friend.request

import chat.letscoffee.user.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FriendRequestRepository : JpaRepository<FriendRequest?, Long?> {
    fun findByFromAndStatusIs(from: User, status: FriendRequestStatus = FriendRequestStatus.PENDING): List<FriendRequest>
    fun findByToAndStatusIs(to: User, status: FriendRequestStatus = FriendRequestStatus.PENDING): List<FriendRequest>
    fun existsByFromAndTo(from: User, to: User): Boolean
}
