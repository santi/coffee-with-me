package chat.letscoffee.meeting

import org.springframework.data.jpa.repository.JpaRepository

interface MeetingRepository: JpaRepository<Meeting, Long> {
    fun findByIdAndActiveTrue(id: Long): Meeting?
}
