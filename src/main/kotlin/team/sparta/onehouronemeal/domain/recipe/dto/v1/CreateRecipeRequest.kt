package team.sparta.onehouronemeal.domain.recipe.dto.v1

data class CreateRecipeRequest(
    val title: String,
    val describe: String,
    val videoUrl: String?,
)
