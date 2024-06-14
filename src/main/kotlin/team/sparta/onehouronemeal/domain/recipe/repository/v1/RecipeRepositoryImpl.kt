package team.sparta.onehouronemeal.domain.recipe.repository.v1

import org.springframework.stereotype.Repository
import team.sparta.onehouronemeal.domain.recipe.model.v1.QRecipe.recipe
import team.sparta.onehouronemeal.domain.recipe.model.v1.Recipe
import team.sparta.onehouronemeal.infra.querydsl.QueryDslSupport

@Repository
class RecipeRepositoryImpl : CustomRecipeRepository, QueryDslSupport() {

    override fun searchRecipeListByTitle(title: String): List<Recipe> {
        return queryFactory.selectFrom(recipe)
            .leftJoin(recipe.course).fetchJoin()
            .where(recipe.title.containsIgnoreCase(title))
            .fetch()
    }

    fun findByCourseIdAndRecipeId(courseId: Long, recipeId: Long): Recipe? {
        return queryFactory.selectFrom(recipe)
            .leftJoin(recipe.course).fetchJoin()
            .where(recipe.course.id.eq(courseId).and(recipe.id.eq(recipeId)))
            .fetchOne()
    }
}