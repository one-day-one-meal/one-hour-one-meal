package team.sparta.onehouronemeal.domain.recipe.repository.v1

import org.springframework.data.jpa.repository.JpaRepository
import team.sparta.onehouronemeal.domain.course.model.v1.Course
import team.sparta.onehouronemeal.domain.recipe.model.v1.Recipe
import java.util.Optional

interface RecipeRepository : JpaRepository<Recipe, Long>, CustomRecipeRepository {
    fun findByCourse(course: Course): List<Recipe>
    fun findByCourseAndId(course: Course, id: Long): Optional<Recipe>
}
