package team.sparta.onehouronemeal.domain.course.model.v1

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import team.sparta.onehouronemeal.domain.common.BaseTimeEntity
import team.sparta.onehouronemeal.domain.user.model.v1.User

@Entity
class Course(
    @Column var title: String,

    @Column var describe: String,

    @Enumerated(EnumType.STRING) var status: CourseStatus = CourseStatus.PENDING,

    @ManyToOne(fetch = FetchType.LAZY) var user: User

) : BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}