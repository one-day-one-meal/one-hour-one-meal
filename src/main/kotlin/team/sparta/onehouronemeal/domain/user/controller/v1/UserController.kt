package team.sparta.onehouronemeal.domain.user.controller.v1

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import team.sparta.onehouronemeal.domain.user.dto.v1.SignInResponse
import team.sparta.onehouronemeal.domain.user.dto.v1.TokenCheckResponse
import team.sparta.onehouronemeal.domain.user.service.v1.UserService

@RestController
@RequestMapping("/api/v1")
class UserController(
    val userService: UserService
) {

    @GetMapping("/auth/token-test-generate")
    fun tokenTestGenerate(): ResponseEntity<SignInResponse> {
        return ResponseEntity.ok(userService.tokenTestGenerate())
    }

    @GetMapping("/auth/token-check")
    fun tokenTestCheck(httpServlet: HttpServletRequest): ResponseEntity<TokenCheckResponse> {
        val accessToken = httpServlet.getHeader("Authorization")
            ?: throw IllegalArgumentException("Authorization header is required")

        return ResponseEntity.ok(userService.tokenTestCheck(accessToken))
    }
}