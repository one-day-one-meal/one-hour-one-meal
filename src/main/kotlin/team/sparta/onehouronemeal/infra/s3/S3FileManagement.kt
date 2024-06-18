package team.sparta.onehouronemeal.infra.s3

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.ObjectMetadata
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Component
class S3FileManagement(
    @Value("\${cloud.aws.s3.bucket}")
    private val bucket: String,
    private val amazonS3: AmazonS3,
) {
    fun uploadImage(multipartFile: MultipartFile): String {
        val originalFilename = multipartFile.originalFilename
            ?: throw IllegalArgumentException("The image file name is incorrect or missing.")

        checkImageFormat(originalFilename)

        val fileName = "${UUID.randomUUID()}-${originalFilename}"

        val objectMetadata = setFileDateOption(
            multipartFile = multipartFile
        )

        amazonS3.putObject(bucket, fileName, multipartFile.inputStream, objectMetadata)

        return fileName
    }

    fun getUrl(fileName: String): String {
        return amazonS3.getUrl(bucket, fileName).toString()
    }

    fun delete(fileName: String) {
        amazonS3.deleteObject(bucket, fileName)
    }

    private fun setFileDateOption(
        multipartFile: MultipartFile
    ): ObjectMetadata {
        val objectMetadata = ObjectMetadata()
        objectMetadata.contentType = "image/jpeg"
        objectMetadata.contentLength = multipartFile.inputStream.available().toLong()
        objectMetadata.contentDisposition = "inline"
        return objectMetadata
    }

    private fun checkImageFormat(fileName: String) {
        val regex = Regex("""(?i)\.(jpg|jpeg|png|gif|bmp|webp)$""")
        if (!regex.containsMatchIn(fileName)) throw IllegalArgumentException("Invalid file format: $fileName")
    }
}