package team.sparta.onehouronemeal.infra.oauth.controller

import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import team.sparta.onehouronemeal.domain.user.dto.v1.SignInResponse
import team.sparta.onehouronemeal.infra.oauth.client.OAuth2ClientService
import team.sparta.onehouronemeal.infra.oauth.common.OAuth2Provider
import team.sparta.onehouronemeal.infra.oauth.service.OAuth2LoginService

@RestController
@RequestMapping("/api/v1/auth")
class OAuth2LoginController(
    private val oauth2LoginService: OAuth2LoginService,
    private val oauth2ClientService: OAuth2ClientService
) {

    @GetMapping("/oauth2/{provider}/login")
    fun redirectLoginPage(@PathVariable provider: OAuth2Provider, response: HttpServletResponse) {
        oauth2ClientService.redirectUrlBy(provider)
            .let { response.sendRedirect(it) }
    }

    @GetMapping("/oauth2/{provider}/callback")
    fun callback(
        @PathVariable provider: OAuth2Provider,
        @RequestParam(name = "code") code: String
    ): ResponseEntity<SignInResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(oauth2LoginService.login(provider, code))
    }
}