package chat.letscoffee.user.friend.request

import chat.letscoffee.exception.BadRequestException
import chat.letscoffee.exception.ResourceNotFoundException
import chat.letscoffee.user.User
import chat.letscoffee.user.UserService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class FriendRequestService(private val repository: FriendRequestRepository, private val userService: UserService) {

    fun addFriendRequest(from: User, to: User): FriendRequest {
        if (repository.existsByFromAndTo(from, to)) {
            throw BadRequestException("A request between these users already exists")
        }
        val request = FriendRequest(from = from, to = to)

        return repository.save(request)
    }

    fun getRequestsTo(to: User): List<FriendRequest> {
        return repository.findByToAndStatusIs(to)
    }


    fun getRequestsFrom(from: User): List<FriendRequest> {
        return repository.findByFromAndStatusIs(from)
    }

    fun deleteRequest(from: User, id: Long): Boolean {
        val request: FriendRequest = repository.findByIdOrNull(id)?: throw ResourceNotFoundException("FriendRequest", "id", id)
        if (from != request.from) {
            return false
        }
        repository.deleteById(id)

        return true
    }

    fun acceptRequest(to: User, id: Long) {
        val request: FriendRequest = repository.findByIdOrNull(id)?: throw ResourceNotFoundException("FriendRequest", "id", id)
        if (to != request.to) {
            throw ResourceNotFoundException("FriendRequest", "id", id)
        }
        request.status = FriendRequestStatus.ACCEPTED
        request.responded = Instant.now()
        repository.save(request)
        val from = request.from
        // When accepting a request, must update the followers for each User
        to.addFriend(from)
        from.addFriend(to)


        userService.update(to)
    }

    fun rejectRequest(to: User, id: Long) {
        val request: FriendRequest = repository.findByIdOrNull(id)?: throw ResourceNotFoundException("FriendRequest", "id", id)
        if (to != request.to) {
            throw ResourceNotFoundException("FriendRequest", "id", id)
        }
        request.status = FriendRequestStatus.REJECTED
        request.responded = Instant.now()
        repository.save(request)
    }

}
