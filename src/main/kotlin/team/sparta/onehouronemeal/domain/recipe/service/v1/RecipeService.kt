package team.sparta.onehouronemeal.domain.recipe.service.v1

import org.springframework.stereotype.Service
import team.sparta.onehouronemeal.domain.recipe.dto.v1.CreateRecipeRequest
import team.sparta.onehouronemeal.domain.recipe.dto.v1.RecipeResponse
import team.sparta.onehouronemeal.domain.recipe.dto.v1.UpdateRecipeRequest

@Service
class RecipeService {
    fun getAllRecipeList(courseId: Long): List<RecipeResponse> {
        // 실제 구현 필요
    }

    fun getRecipeById(courseId: Long, recipeId: Long): RecipeResponse {
        // 실제 구현 필요
    }

    fun createRecipe(courseId: Long, createRecipeRequest: CreateRecipeRequest): RecipeResponse {
        // 실제 구현 필요
    }

    fun updateRecipe(courseId: Long, recipeId: Long, updateRecipeRequest: UpdateRecipeRequest): RecipeResponse {
        // 실제 구현 필요
    }

    fun deleteRecipe(courseId: Long, recipeId: Long) {
        // 실제 구현 필요
    }
}