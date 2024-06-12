package team.sparta.onehouronemeal.domain.recipe.dto.v1

data class UpdateRecipeRequest(
    val title: String,
    val description: String
    // 기타 필요한 속성 추가
)