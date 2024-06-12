package team.sparta.onehouronemeal.domain.recipe.service.v1

import org.springframework.stereotype.Service
import team.sparta.onehouronemeal.domain.course.repository.v1.CourseRepository
import team.sparta.onehouronemeal.domain.recipe.dto.v1.CreateRecipeRequest
import team.sparta.onehouronemeal.domain.recipe.dto.v1.RecipeResponse
import team.sparta.onehouronemeal.domain.recipe.dto.v1.UpdateRecipeRequest
import team.sparta.onehouronemeal.domain.recipe.model.v1.Recipe
import team.sparta.onehouronemeal.domain.recipe.repository.v1.RecipeRepository

@Service
class RecipeService(
    private val recipeRepository: RecipeRepository,
    private val courseRepository: CourseRepository,
) {
    fun getAllRecipeList(courseId: Long): List<RecipeResponse> {
        val course = courseRepository.findById(courseId)
            .orElseThrow { RuntimeException("Course not found") }
        return recipeRepository.findByCourse(course).map { RecipeResponse.from(it) }
    }

    fun getRecipeById(courseId: Long, recipeId: Long): RecipeResponse {
        val course = courseRepository.findById(courseId).orElseThrow { RuntimeException("Course not found") }
        val recipe =
            recipeRepository.findByCourseAndId(course, recipeId).orElseThrow { RuntimeException("Recipe not found") }
        return RecipeResponse.from(recipe)
    }

    fun createRecipe(courseId: Long, request: CreateRecipeRequest): RecipeResponse {
        val course = courseRepository.findById(courseId)
            .orElseThrow { RuntimeException("Course not found") }
        val recipe = Recipe(
            course = course,
            title = request.title,
            describe = request.describe,
            videoUrl = request.videoUrl
        )
        return RecipeResponse.from(recipeRepository.save(recipe))
    }

    fun updateRecipe(courseId: Long, recipeId: Long, request: UpdateRecipeRequest): RecipeResponse {
        val course = courseRepository.findById(courseId)
            .orElseThrow { RuntimeException("Course not found") }
        val recipe = recipeRepository.findByCourseAndId(course, recipeId)
            .orElseThrow { RuntimeException("Recipe not found") }

        recipe.title = request.title
        recipe.describe = request.describe
        recipe.videoUrl = request.videoUrl

        return RecipeResponse.from(recipeRepository.save(recipe))
    }

    fun deleteRecipe(courseId: Long, recipeId: Long) {
        val course = courseRepository.findById(courseId)
            .orElseThrow { RuntimeException("Course not found") }
        val recipe = recipeRepository.findByCourseAndId(course, recipeId)
            .orElseThrow { RuntimeException("Recipe not found") }

        recipeRepository.delete(recipe)
    }
}