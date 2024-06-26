package team.sparta.onehouronemeal.domain.recipe.model.v1

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import team.sparta.onehouronemeal.domain.common.BaseTimeEntity
import team.sparta.onehouronemeal.domain.course.model.v1.Course

@Entity
@Table(name = "recipe")
class Recipe(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @ManyToOne(fetch = FetchType.LAZY) var course: Course,
    var title: String,
    var describe: String,
    var videoUrl: String?,
) : BaseTimeEntity() {
    init {
        this.validate()
    }
    
    fun checkPermission(userId: Long, role: String): Boolean {
        return this.id == userId || role == "ROLE_ADMIN"
    }

    private fun validate() {
        require(title.length <= 30) { "Title must be less than 30 characters" }
    }
}