package team.sparta.onehouronemeal.domain.recipe.repository.v1

import org.springframework.stereotype.Repository
import team.sparta.onehouronemeal.domain.recipe.model.v1.QRecipe
import team.sparta.onehouronemeal.domain.recipe.model.v1.Recipe
import team.sparta.onehouronemeal.infra.querydsl.QueryDslSupport

@Repository
class QueryDslRecipeRepository : QueryDslSupport() {

    private val recipe = QRecipe.recipe

    fun searchRecipeListByTitle(title: String): List<Recipe> {
        return queryFactory.selectFrom(recipe)
            .where(recipe.title.containsIgnoreCase(title))
            .fetch()
    }
}