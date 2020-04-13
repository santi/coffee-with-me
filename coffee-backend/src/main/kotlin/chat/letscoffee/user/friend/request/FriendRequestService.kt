package chat.letscoffee.user.friend.request

import chat.letscoffee.exception.BadRequestException
import chat.letscoffee.exception.ResourceNotFoundException
import chat.letscoffee.user.User
import chat.letscoffee.user.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class FriendRequestService(private val repository: FriendRequestRepository, private val userRepository: UserRepository) {

    fun addFriendRequest(from: User, to: String): FriendRequest {
        val touser: User = userRepository.findByEmail(to)?: throw ResourceNotFoundException("User", "mail", to);
        // Check if you already have an ongoing request to this user

        if (repository.existsByFromAndTo(from, touser)) {
            throw BadRequestException("A request between these users already exists")
        }
        val request = FriendRequest(from = from, to = touser)

        return repository.save(request)
    }

    fun getMyFriendRequests(to: User): List<FriendRequest> {
        return repository.findByToAndAcceptedIsNull(to);
    }


    fun getMySentFriendRequests(from: User): List<FriendRequest> {
        return repository.findByFromAndAcceptedIsNull(from);
    }

    fun deleteRequest(from: User, id: Long): Boolean {
        val request: FriendRequest = repository.findByIdOrNull(id)?: throw ResourceNotFoundException("FriendRequest", "id", id) ;
        if (from != request.from) {
            return false;
        }
        repository.deleteById(id);

        return true;
    }

    fun acceptRequest(to: User, id: Long): Boolean {
        val request: FriendRequest = repository.findByIdOrNull(id)?: throw ResourceNotFoundException("FriendRequest", "id", id) ;
        if (to != request.to) {
            return false;
        }
        request.accepted = true;
        repository.save(request)
        val from = request.from
        // When accepting a request, must update the followers for each User
        to.addFriend(from)
        from.addFriend(to)


        userRepository.save(to)
        return true;
    }

    fun rejectRequest(to: User, id: Long): Boolean {
        val request: FriendRequest = repository.findByIdOrNull(id)?: throw ResourceNotFoundException("FriendRequest", "id", id);
        if (to != request.to) {
            return false;
        }
        request.accepted = false;
        repository.save(request)
        return true;
    }

}
