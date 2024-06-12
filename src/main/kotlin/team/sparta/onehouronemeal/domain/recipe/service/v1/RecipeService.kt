package team.sparta.onehouronemeal.domain.recipe.service.v1

import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import team.sparta.onehouronemeal.domain.course.repository.v1.CourseRepository
import team.sparta.onehouronemeal.domain.recipe.dto.v1.CreateRecipeRequest
import team.sparta.onehouronemeal.domain.recipe.dto.v1.RecipeResponse
import team.sparta.onehouronemeal.domain.recipe.dto.v1.UpdateRecipeRequest
import team.sparta.onehouronemeal.domain.recipe.model.v1.Recipe
import team.sparta.onehouronemeal.domain.recipe.repository.v1.RecipeRepository
import team.sparta.onehouronemeal.exception.ModelNotFoundException

@Service
class RecipeService(
    private val recipeRepository: RecipeRepository,
    private val courseRepository: CourseRepository,
) {
    fun getAllRecipeList(courseId: Long): List<RecipeResponse> {
        if (!courseRepository.existsById(courseId)) throw ModelNotFoundException("Course", courseId)
        return recipeRepository.findAllByCourseId(courseId).map { RecipeResponse.from(it) }
    }

    fun getRecipeById(courseId: Long, recipeId: Long): RecipeResponse {
        if (!courseRepository.existsById(courseId)) throw ModelNotFoundException("Course", courseId)
        val recipe =
            recipeRepository.findByCourseIdAndId(courseId, recipeId) ?: throw ModelNotFoundException("Recipe", recipeId)
        return RecipeResponse.from(recipe)
    }

    fun createRecipe(courseId: Long, request: CreateRecipeRequest): RecipeResponse {
        val course = courseRepository.findByIdOrNull(courseId) ?: throw ModelNotFoundException("Course", courseId)
        val recipe = Recipe(
            course = course,
            title = request.title,
            describe = request.describe,
            videoUrl = request.videoUrl
        )
        return RecipeResponse.from(recipeRepository.save(recipe))
    }

    @Transactional
    fun updateRecipe(courseId: Long, recipeId: Long, request: UpdateRecipeRequest): RecipeResponse {
        if (!courseRepository.existsById(courseId)) throw ModelNotFoundException("Course", courseId)
        val recipe =
            recipeRepository.findByCourseIdAndId(courseId, recipeId) ?: throw ModelNotFoundException("Recipe", recipeId)

        recipe.title = request.title
        recipe.describe = request.describe
        recipe.videoUrl = request.videoUrl

        return RecipeResponse.from(recipe)
    }

    @Transactional
    fun deleteRecipe(courseId: Long, recipeId: Long) {
        if (!courseRepository.existsById(courseId)) throw ModelNotFoundException("Course", courseId)
        val recipe =
            recipeRepository.findByCourseIdAndId(courseId, recipeId) ?: throw ModelNotFoundException("Recipe", recipeId)

        recipeRepository.delete(recipe)
    }
}