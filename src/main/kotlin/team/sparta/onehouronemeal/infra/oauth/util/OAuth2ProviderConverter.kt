package team.sparta.onehouronemeal.infra.oauth.util

import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import team.sparta.onehouronemeal.infra.oauth.common.OAuth2Provider

@Component
class OAuth2ProviderConverter : Converter<String, OAuth2Provider> {
    override fun convert(source: String): OAuth2Provider? {
        return OAuth2Provider.entries.firstOrNull { it.name.equals(source, ignoreCase = true) }
    }
}
