package team.sparta.onehouronemeal.domain.user.controller.v1

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import team.sparta.onehouronemeal.domain.user.dto.v1.*
import team.sparta.onehouronemeal.domain.user.service.v1.UserService
import team.sparta.onehouronemeal.infra.security.UserPrincipal

@RestController
@RequestMapping("/api/v1")
class UserController(
    val userService: UserService
) {
    @PostMapping(
        "/auth/sign-up/{role}", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE]
    )
    fun signUpUser(
        @PathVariable role: String,
        @RequestPart("request") request: SignUpRequest,
        @RequestPart("image", required = false) image: MultipartFile?
    ): ResponseEntity<UserResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.signUp(role, request, image))
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
    fun getUserProfile(
        @PathVariable userId: Long,
        @AuthenticationPrincipal principal: UserPrincipal,
    ): ResponseEntity<UserResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserProfile(userId, principal))
    }

    @PutMapping("/users/{userId}/profiles")
    fun updateUserProfile(
        @PathVariable userId: Long,
        @AuthenticationPrincipal principal: UserPrincipal,
        @RequestBody request: UpdateUserRequest,
    ): ResponseEntity<UserResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUserProfile(userId, principal, request))
    }

    @PostMapping("/users/subscribe/{chefId}")
    fun subscribeChef(
        @AuthenticationPrincipal principal: UserPrincipal, @PathVariable chefId: Long
    ): ResponseEntity<SubscriptionResponse> {
        if (principal.id == chefId) throw IllegalArgumentException("You can't subscribe to yourself")

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.subscribeChef(principal, chefId))
    }

    @DeleteMapping("/users/subscribe/{chefId}")
    fun unsubscribeChef(
        @AuthenticationPrincipal principal: UserPrincipal, @PathVariable chefId: Long
    ): ResponseEntity<Unit> {
        if (principal.id == chefId) throw IllegalArgumentException("You can't unsubscribe to yourself")
        userService.unsubscribeChef(principal, chefId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

    @GetMapping("/auth/token-test-generate")
    fun tokenTestGenerate(): ResponseEntity<SignInResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(userService.tokenTestGenerate())
    }

    @GetMapping("/auth/token-check")
    fun tokenTestCheck(
        @AuthenticationPrincipal principal: UserPrincipal, httpServlet: HttpServletRequest
    ): ResponseEntity<TokenCheckResponse> {
        val accessToken =
            httpServlet.getHeader("Authorization") ?: throw IllegalArgumentException("Authorization header is required")

        return ResponseEntity.status(HttpStatus.OK)
            .body(userService.tokenTestCheck(accessToken = accessToken, principal = principal))
    }
}