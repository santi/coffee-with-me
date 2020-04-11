package chat.letscoffee.friends

import chat.letscoffee.exception.BadRequestException
import chat.letscoffee.exception.ResourceNotFoundException
import chat.letscoffee.security.model.User
import chat.letscoffee.security.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class FriendRequestService(private val repository: FriendRequestRepository, private val userRepository: UserRepository) {

    fun addFriendRequest(from: User, to: String): FriendRequestModel {
        val touser: User = userRepository.findByNameOrEmail(to, to)?: throw ResourceNotFoundException("User", "username", to);
        // Check if you already have an ongoing request to this user

        if (repository.existsByFromAndTo(from, touser)) {
            throw BadRequestException("A request between these users already exists")
        }
        val request = FriendRequestModel(from=from, to=touser)

        return repository.save(request)
    }

    fun getMyFriendRequests(to: User): List<FriendRequestModel> {
        return repository.findByToAndAcceptedIsNull(to);
    }


    fun getMySentFriendRequests(from: User): List<FriendRequestModel> {
        return repository.findByFromAndAcceptedIsNull(from);
    }

    fun deleteRequest(from: User, id: Long): Boolean {
        val request: FriendRequestModel = repository.findByIdOrNull(id)?: throw ResourceNotFoundException("FriendRequest", "id", id) ;
        if (from != request.from) {
            return false;
        }
        repository.deleteById(id);

        return true;
    }

    fun acceptRequest(to: User, id: Long): Boolean {
        val request: FriendRequestModel? = repository.findById(id).orElseThrow{ResourceNotFoundException("FriendRequest", "id", id)} ;
        if (to != request!!.to) {
            return false;
        }
        request.accepted = true;
        // When accepting a request, must update the followers for each User
        to.addFriend(request.from)

        request.from.addFriend(to)

        repository.save(request)
        userRepository.save(to)
        userRepository.save(request.from)
        return true;
    }

    fun rejectRequest(to: User, id: Long): Boolean {
        val request: FriendRequestModel? = repository.findById(id).orElseThrow{ResourceNotFoundException("FriendRequest", "id", id)} ;
        if (to != request!!.to) {
            return false;
        }
        request.accepted = false;
        repository.save(request)
        return true;
    }

}