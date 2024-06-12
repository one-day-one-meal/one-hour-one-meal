package team.sparta.onehouronemeal.domain.user.controller.v1

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import team.sparta.onehouronemeal.domain.user.dto.v1.SignInRequest
import team.sparta.onehouronemeal.domain.user.dto.v1.SignInResponse
import team.sparta.onehouronemeal.domain.user.dto.v1.SignUpRequest
import team.sparta.onehouronemeal.domain.user.dto.v1.TokenCheckResponse
import team.sparta.onehouronemeal.domain.user.dto.v1.UpdateUserRequest
import team.sparta.onehouronemeal.domain.user.dto.v1.UserResponse
import team.sparta.onehouronemeal.domain.user.service.v1.UserService

@RestController
@RequestMapping("/api/v1")
class UserController(
    val userService: UserService
) {

    @PostMapping("/auth/sign-up/{role}")
    fun signUpUser(@PathVariable role: String, @RequestBody request: SignUpRequest): ResponseEntity<UserResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.signUp(role, request))
    }

    @PostMapping("/auth/sign-in")
    fun signUpUser(@RequestBody request: SignInRequest): ResponseEntity<SignInResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(userService.signIn(request))
    }

    @PostMapping("/auth/sign-out")
    fun signOutUser(): ResponseEntity<Unit> {
        userService.signOut()
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

    @GetMapping("/users/{userId}/profiles")
    fun getUserProfile(@PathVariable userId: Long): ResponseEntity<UserResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserProfile(userId))
    }

    @PutMapping("/users/{userId}/profiles")
    fun updateUserProfile(
        @PathVariable userId: Long,
        @RequestBody request: UpdateUserRequest
    ): ResponseEntity<UserResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUserProfile(userId, request))
    }

    @GetMapping("/auth/token-test-generate")
    fun tokenTestGenerate(): ResponseEntity<SignInResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(userService.tokenTestGenerate())
    }

    @GetMapping("/auth/token-check")
    fun tokenTestCheck(httpServlet: HttpServletRequest): ResponseEntity<TokenCheckResponse> {
        val accessToken = httpServlet.getHeader("Authorization")
            ?: throw IllegalArgumentException("Authorization header is required")

        return ResponseEntity.status(HttpStatus.OK).body(userService.tokenTestCheck(accessToken))
    }
}