package team.sparta.onehouronemeal.domain.recipe.repository.v1

import team.sparta.onehouronemeal.domain.recipe.model.v1.Recipe

interface CustomRecipeRepository {
    fun findByCourseId(courseId: Long): List<Recipe>
    fun findByCourseIdAndRecipeId(courseId: Long, recipeId: Long): Recipe?
}