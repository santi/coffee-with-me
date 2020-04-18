package chat.letscoffee.meeting

import chat.letscoffee.security.model.AuthProvider
import chat.letscoffee.user.User
import chat.letscoffee.JpaSpecification
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager

import javax.persistence.EntityManagerFactory
import javax.sql.DataSource
import java.time.Instant

class MeetingRepositorySpec extends JpaSpecification {

    @Autowired
    DataSource dataSource

    @Autowired
    private TestEntityManager entityManager

    @Autowired
    private EntityManagerFactory emFactory

    @Autowired
    private MeetingRepository meetingRepository

    def user = new User(
        null,
        "test",
        "test@example.com",
        false,
        null,
        null,
        AuthProvider.LOCAL,
        null,
        [] as Set,
        [] as Set,
        Instant.now()
    )

    def activeMeeting = new Meeting(
        null,
        Instant.now(),
        true,
        user,
        "http://example.com",
        MeetingProviderType.SKYPE
    )

    def inactiveMeeting = new Meeting(
        null,
        Instant.now(),
        false,
        user,
        "http://example.com",
        MeetingProviderType.SKYPE
    )

    def cleanup(){
        entityManager.getEntityManager().createNativeQuery("TRUNCATE TABLE meeting RESTART IDENTITY CASCADE")
        entityManager.getEntityManager().createNativeQuery("TRUNCATE TABLE user_account RESTART IDENTITY CASCADE")
    }

    def "finding an active meeting should return the meeting successfully"() {
        given: "an inactive meeting belonging to a user"
        entityManager.persist(user)
        def storedMeeting = entityManager.persist(activeMeeting)

        when: "getting the meeting for the user"
        def meeting = meetingRepository.findByIdAndActiveTrue(storedMeeting.id)

        then: "the returned meeting should be correct"
        meeting == storedMeeting
    }

    def "finding an inactive meeting should return null"() {
        given: "an inactive meeting belonging to a user"
        entityManager.persist(user)
        def storedMeeting = entityManager.persist(inactiveMeeting)

        when: "getting the meeting"
        def meeting = meetingRepository.findByIdAndActiveTrue(storedMeeting.id)

        then: "the returned meeting should be null"
        meeting == null
    }
}
