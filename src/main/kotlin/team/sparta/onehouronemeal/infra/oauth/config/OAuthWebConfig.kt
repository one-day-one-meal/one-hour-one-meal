package team.sparta.onehouronemeal.infra.oauth.config

import org.springframework.context.annotation.Configuration
import org.springframework.format.FormatterRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import team.sparta.onehouronemeal.infra.oauth.util.OAuth2ProviderConverter

@Configuration
class OAuthWebConfig(private val oauth2ProviderConverter: OAuth2ProviderConverter) : WebMvcConfigurer {
    override fun addFormatters(registry: FormatterRegistry) {
        registry.addConverter(oauth2ProviderConverter)
    }
}