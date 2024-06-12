package team.sparta.onehouronemeal.domain.recipe.repository.v1

import org.springframework.data.jpa.repository.JpaRepository
import team.sparta.onehouronemeal.domain.recipe.model.v1.Recipe

interface RecipeRepository : JpaRepository<Recipe, Long>, CustomRecipeRepository
// 나중에 Jpa이름으로 쿼리 작성할 때 이곳에 작성