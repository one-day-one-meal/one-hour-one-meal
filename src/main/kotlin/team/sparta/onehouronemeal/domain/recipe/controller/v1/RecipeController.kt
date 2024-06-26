package team.sparta.onehouronemeal.domain.recipe.controller.v1

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import team.sparta.onehouronemeal.domain.recipe.dto.v1.CreateRecipeRequest
import team.sparta.onehouronemeal.domain.recipe.dto.v1.RecipeResponse
import team.sparta.onehouronemeal.domain.recipe.dto.v1.UpdateRecipeRequest
import team.sparta.onehouronemeal.domain.recipe.service.v1.RecipeService
import team.sparta.onehouronemeal.infra.security.UserPrincipal

@RestController
@RequestMapping("/api/v1/courses/{courseId}/recipes")
class RecipeController(
    private val recipeService: RecipeService
) {
    @GetMapping
    fun getRecipeList(@PathVariable courseId: Long): ResponseEntity<List<RecipeResponse>> {
        return ResponseEntity.status(HttpStatus.OK).body(recipeService.getAllRecipeList(courseId))
    }

    @GetMapping("/{recipeId}")
    fun getRecipe(
        @PathVariable courseId: Long,
        @PathVariable recipeId: Long,
        @AuthenticationPrincipal principal: UserPrincipal
    ): ResponseEntity<RecipeResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(recipeService.getRecipeById(courseId, recipeId, principal))
    }

    @PostMapping
    fun createRecipe(
        @PathVariable courseId: Long,
        @Valid @RequestBody request: CreateRecipeRequest,
        @AuthenticationPrincipal principal: UserPrincipal
    ): RecipeResponse {
        return recipeService.createRecipe(courseId, request, principal)
    }

    @PutMapping("/{recipeId}")
    fun updateRecipe(
        @PathVariable courseId: Long,
        @PathVariable recipeId: Long,
        @Valid @RequestBody updateRecipeRequest: UpdateRecipeRequest,
        @AuthenticationPrincipal principal: UserPrincipal
    ): ResponseEntity<RecipeResponse> {
        return ResponseEntity.status(HttpStatus.OK)
            .body(recipeService.updateRecipe(courseId, recipeId, updateRecipeRequest, principal))
    }

    @DeleteMapping("/{recipeId}")
    fun deleteRecipe(
        @PathVariable courseId: Long,
        @PathVariable recipeId: Long,
        @AuthenticationPrincipal principal: UserPrincipal
    ): ResponseEntity<Unit> {
        recipeService.deleteRecipe(courseId, recipeId, principal)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }
}
