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
import team.sparta.onehouronemeal.exception.AccessDeniedException
import team.sparta.onehouronemeal.exception.ModelNotFoundException
import team.sparta.onehouronemeal.infra.security.UserPrincipal

@Service
class RecipeService(
    private val recipeRepository: RecipeRepository,
    private val courseRepository: CourseRepository,
) {
    companion object {
        private const val COURSE_NOT_FOUND = "Course"
        private const val RECIPE_NOT_FOUND = "Recipe"
    }

    fun searchRecipeList(title: String): List<RecipeResponse> {
        return recipeRepository.searchRecipeListByTitle(title).map { RecipeResponse.from(it) }
    }

    fun getAllRecipeList(courseId: Long): List<RecipeResponse> {
        validateCourseExists(courseId)
        return recipeRepository.findAllByCourseId(courseId).map { RecipeResponse.from(it) }
    }

    fun getRecipeById(courseId: Long, recipeId: Long, principal: UserPrincipal): RecipeResponse {
        validateCourseExists(courseId)
        val recipe = recipeRepository.findByCourseIdAndRecipeId(courseId, recipeId)
            ?: throw ModelNotFoundException(RECIPE_NOT_FOUND, recipeId)
        checkPermission(recipe, principal)
        return RecipeResponse.from(recipe)
    }

    fun createRecipe(courseId: Long, request: CreateRecipeRequest): RecipeResponse {
        val course =
            courseRepository.findByIdOrNull(courseId) ?: throw ModelNotFoundException(COURSE_NOT_FOUND, courseId)
        val recipe = Recipe(
            course = course,
            title = request.title,
            describe = request.describe,
            videoUrl = request.videoUrl
        )
        return RecipeResponse.from(recipeRepository.save(recipe))
    }

    @Transactional
    fun updateRecipe(
        courseId: Long,
        recipeId: Long,
        request: UpdateRecipeRequest,
        principal: UserPrincipal
    ): RecipeResponse {
        validateCourseExists(courseId)
        val recipe = recipeRepository.findByCourseIdAndId(courseId, recipeId)
            ?: throw ModelNotFoundException(RECIPE_NOT_FOUND, recipeId)
        checkPermission(recipe, principal)
        recipe.title = request.title
        recipe.describe = request.describe
        recipe.videoUrl = request.videoUrl

        return RecipeResponse.from(recipe)
    }

    @Transactional
    fun deleteRecipe(courseId: Long, recipeId: Long, principal: UserPrincipal) {
        validateCourseExists(courseId)
        val recipe = recipeRepository.findByCourseIdAndId(courseId, recipeId)
            ?: throw ModelNotFoundException(RECIPE_NOT_FOUND, recipeId)
        checkPermission(recipe, principal)
        recipeRepository.delete(recipe)
    }

    private fun validateCourseExists(courseId: Long) {
        if (!courseRepository.existsById(courseId)) throw ModelNotFoundException(COURSE_NOT_FOUND, courseId)
    }

    private fun checkPermission(recipe: Recipe, principal: UserPrincipal) {
        if (!recipe.checkPermission(principal.id, principal.role)) {
            throw AccessDeniedException("You do not have permission to access this recipe")
        }
    }
}
