package team.sparta.onehouronemeal.infra.s3

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.MediaType
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter
import org.springframework.lang.Nullable
import org.springframework.stereotype.Component
import java.lang.reflect.Type


@Component
class CustomJackson2HttpMessageConverter(objectMapper: ObjectMapper) :
    AbstractJackson2HttpMessageConverter(objectMapper, MediaType.APPLICATION_OCTET_STREAM) {
    override fun canWrite(clazz: Class<*>, @Nullable mediaType: MediaType?): Boolean {
        return false
    }

    override fun canWrite(@Nullable type: Type?, clazz: Class<*>, @Nullable mediaType: MediaType?): Boolean {
        return false
    }

    override fun canWrite(mediaType: MediaType?): Boolean {
        return false
    }
}