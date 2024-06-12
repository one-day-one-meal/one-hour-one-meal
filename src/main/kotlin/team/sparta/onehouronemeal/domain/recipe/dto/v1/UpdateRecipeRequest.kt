package team.sparta.onehouronemeal.domain.recipe.dto.v1

data class UpdateRecipeRequest(
    val title: String,
    val describe: String,
    val videoUrl: String?,
)