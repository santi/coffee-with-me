package chat.letscoffee.user.friend.request

import chat.letscoffee.user.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FriendRequestRepository : JpaRepository<FriendRequest?, Long?> {
    fun findByFromAndAcceptedIsNull(from: User?): List<FriendRequest>
    fun findByToAndAcceptedIsNull(to: User?): List<FriendRequest>
    fun existsByFromAndTo(from: User, to: User): Boolean
}
