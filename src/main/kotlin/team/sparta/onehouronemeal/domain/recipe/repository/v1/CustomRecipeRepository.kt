package team.sparta.onehouronemeal.domain.recipe.repository.v1

import team.sparta.onehouronemeal.domain.recipe.model.v1.Recipe

interface CustomRecipeRepository {
    fun searchRecipeListByTitle(title: String): List<Recipe>
}