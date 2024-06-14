package team.sparta.onehouronemeal.domain.recipe.dto.v1

import team.sparta.onehouronemeal.domain.recipe.model.v1.Recipe
import java.time.LocalDateTime

data class RecipeResponse(
    val id: Long,
    val courseId: Long,
    val courseTitle: String, // Course의 title 추가
    val title: String,
    val describe: String,
    val videoUrl: String?,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?
) {
    companion object {
        fun from(recipe: Recipe): RecipeResponse {
            return RecipeResponse(
                id = recipe.id!!,
                courseId = recipe.course.id!!,
                courseTitle = recipe.course.title, // Course의 title 매핑
                title = recipe.title,
                describe = recipe.describe,
                videoUrl = recipe.videoUrl,
                createdAt = recipe.createdAt,
                updatedAt = recipe.updatedAt
            )
        }
    }
}