package chat.letscoffee.meeting

import chat.letscoffee.user.User
import java.time.Instant
import java.time.Instant.now
import javax.persistence.*

@Entity
@Table(
    name = "meeting",
    indexes = [Index(columnList = "owner", name = "meeting_owner_ix", unique = false)])
data class Meeting(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val created: Instant = now(),

    @Column(nullable = false)
    var active: Boolean = true,

    @OneToOne
    @JoinColumn(name = "owner", foreignKey = ForeignKey(name = "meeting_owner_fkey"), nullable = false)
    val owner: User,

    @Column(columnDefinition = "TEXT", nullable = false)
    val link: String,

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "TEXT", nullable = false)
    val provider: MeetingProviderType
)

enum class MeetingProviderType {
    SKYPE
}
