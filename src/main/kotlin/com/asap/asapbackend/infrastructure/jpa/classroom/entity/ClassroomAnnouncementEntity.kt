package com.asap.asapbackend.infrastructure.jpa.classroom.entity

import com.asap.asapbackend.domain.classroom.domain.model.Classroom
import com.asap.asapbackend.domain.classroom.domain.vo.AnnouncementDescription
import jakarta.persistence.*
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.time.LocalDateTime

@Entity
@Table(
    name = "classroom_announcement",
    indexes = [
        Index(name = "idx_classroom_announcement_classroom_id", columnList = "classroom_id"),
        Index(name = "idx_classroom_announcement_teacher_id", columnList = "teacher_id")
    ]
)
class ClassroomAnnouncementEntity(
    id: Long,
    descriptions: List<AnnouncementDescription>,
    classroomId: Long,
    teacherId: Long,
    createdAt: LocalDateTime,
    updatedAt: LocalDateTime
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = id

    @Column(
        nullable = false,
        columnDefinition = "json"
    )
    @JdbcTypeCode(SqlTypes.JSON)
    var descriptions: List<AnnouncementDescription> = descriptions

    @Column(
        nullable = false,
        name = "classroom_id"
    )
    val classroomId: Long = classroomId

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classroom_id", insertable = false, updatable = false)
    lateinit var classroom: Classroom

    @Column(
        nullable = false,
        name = "teacher_id"
    )
    val teacherId: Long = teacherId

    val createdAt: LocalDateTime = createdAt
    val updatedAt: LocalDateTime = updatedAt

    fun updateDescriptions(descriptions: List<AnnouncementDescription>){
        this.descriptions = descriptions
    }
}