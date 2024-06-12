package team.sparta.onehouronemeal.exception

data class ModelNotFoundException(
    val modelName: String,
    val id: Long
) : RuntimeException(
    "Model $modelName with given id $id not found"
)
