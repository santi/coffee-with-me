package chat.letscoffee.meeting

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MeetingRepository : JpaRepository<Meeting, Long> {
    fun findByIdAndActiveTrue(id: Long): Meeting?
}
