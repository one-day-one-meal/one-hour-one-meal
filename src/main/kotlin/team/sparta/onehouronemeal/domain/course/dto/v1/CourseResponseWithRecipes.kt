package team.sparta.onehouronemeal.domain.course.dto.v1

import team.sparta.onehouronemeal.domain.course.model.v1.Course
import team.sparta.onehouronemeal.domain.recipe.dto.v1.RecipeResponse
import team.sparta.onehouronemeal.domain.user.dto.v1.UserResponse
import java.time.LocalDateTime

data class CourseResponseWithRecipes(
    val id: Long,
    val user: UserResponse,
    val title: String,
    val describe: String,
    val status: String,
    val thumbsUpCount: Int,
    val recipeList: List<RecipeResponse>,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?
) {
    companion object {
        fun from(course: Course, thumbsUpCount: Int, recipeList: List<RecipeResponse>): CourseResponseWithRecipes {
            return CourseResponseWithRecipes(
                id = course.id!!,
                user = UserResponse.from(course.user),
                title = course.title,
                describe = course.describe,
                status = course.status.name,
                thumbsUpCount = thumbsUpCount,
                recipeList = recipeList,
                createdAt = course.createdAt,
                updatedAt = course.updatedAt
            )
        }
    }
}
