package team.sparta.onehouronemeal.domain.course.dto.v1

import team.sparta.onehouronemeal.domain.comment.dto.v1.CommentResponse
import team.sparta.onehouronemeal.domain.course.model.v1.Course
import team.sparta.onehouronemeal.domain.recipe.dto.v1.RecipeResponse
import team.sparta.onehouronemeal.domain.user.dto.v1.UserResponse
import java.time.LocalDateTime

data class CourseResponseWithRecipesAndComments(
    val id: Long,
    val user: UserResponse,
    val title: String,
    val describe: String,
    val status: String,
    val thumbsUpCount: Long,
    val recipeList: List<RecipeResponse>,
    val commentList: List<CommentResponse>,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?
) {
    companion object {
        fun from(
            course: Course,
            thumbsUpCount: Long,
            recipeList: List<RecipeResponse>,
            commentList: List<CommentResponse>
        ): CourseResponseWithRecipesAndComments {
            return CourseResponseWithRecipesAndComments(
                id = course.id!!,
                user = UserResponse.from(course.user),
                title = course.title,
                describe = course.describe,
                status = course.status.name,
                thumbsUpCount = thumbsUpCount,
                recipeList = recipeList,
                commentList = commentList,
                createdAt = course.createdAt,
                updatedAt = course.updatedAt
            )
        }
    }
}
