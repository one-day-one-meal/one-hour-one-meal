package team.sparta.onehouronemeal.domain.course.dto.v1

import jakarta.validation.constraints.Size

data class CreateCourseRequest(
    @Size(max = 30, message = "Title must be less than 30 characters")
    val title: String,
    val describe: String
)
