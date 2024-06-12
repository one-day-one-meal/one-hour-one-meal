package team.sparta.onehouronemeal.domain.recipe.repository.v1

import org.springframework.data.jpa.repository.JpaRepository
import team.sparta.onehouronemeal.domain.recipe.model.v1.Recipe

interface RecipeJpaRepository : JpaRepository<Recipe, Long>