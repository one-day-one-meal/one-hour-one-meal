package team.sparta.onehouronemeal.domain.recipe.service.v1

import org.springframework.stereotype.Service
import team.sparta.onehouronemeal.domain.recipe.dto.v1.CreateRecipeRequest
import team.sparta.onehouronemeal.domain.recipe.dto.v1.RecipeResponse
import team.sparta.onehouronemeal.domain.recipe.dto.v1.UpdateRecipeRequest
import team.sparta.onehouronemeal.domain.recipe.repository.v1.RecipeRepository

@Service
class RecipeService(
    private val recipeRepository: RecipeRepository,
) {
    fun getAllRecipeList(courseId: Long): List<RecipeResponse> {
        TODO()
    }

    fun getRecipeById(courseId: Long, recipeId: Long): RecipeResponse {
        TODO()
    }

    fun createRecipe(courseId: Long, request: CreateRecipeRequest): RecipeResponse {
        TODO()
    }

    fun updateRecipe(courseId: Long, recipeId: Long, request: UpdateRecipeRequest): RecipeResponse {
        TODO()
    }

    fun deleteRecipe(courseId: Long, recipeId: Long) {
        TODO()
    }
}