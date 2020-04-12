package chat.letscoffee.user

import chat.letscoffee.exception.ResourceNotFoundException
import chat.letscoffee.security.model.AuthProvider
import spock.lang.Specification

class UserServiceSpec extends Specification {


    def existingEmail = "existingEmail@example.com"
    def nonExistingEmail = "nonExistingEmail@example.com"

    def existingUser = new User(
        0L,
        "name",
        existingEmail,
        null,
        true,
        null,
        AuthProvider.LOCAL,
        null,
        [] as Set,
        [] as Set
    )

    def userRepository = Mock(UserRepository)

    def userService = new UserService(userRepository)

    def "getting a user by an existing email should succeed"() {
        when: "requesting a user by an email that exists"
        def user = userService.getUserByEmail(existingEmail)

        then: "the repository should return the correct user"
        1 * userRepository.findByEmail(existingEmail) >> existingUser

        and: "the returned user should be the correct user"
        user.email == existingUser.email

        and: "no exceptions should be thrown"
        notThrown(ResourceNotFoundException)
    }

    def "getting user by non-existent email should throw exception"() {
        when: "requesting a user by an email that does not exist"
        userService.getUserByEmail(nonExistingEmail)

        then: "the repository should try to fetch the user"
        1 * userRepository.findByEmail(nonExistingEmail) >> null

        and: "a ResourceNotFoundException should be thrown"
        thrown(ResourceNotFoundException)
    }
}
