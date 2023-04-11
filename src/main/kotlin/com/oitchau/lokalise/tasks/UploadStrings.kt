package com.oitchau.lokalise.tasks

import org.apache.commons.codec.binary.Base64
import org.apache.commons.io.FileUtils
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import com.oitchau.lokalise.ApiConfig
import com.oitchau.lokalise.UploadEntry
import com.oitchau.lokalise.api.Api
import com.oitchau.lokalise.api.dto.UploadFileDto
import com.oitchau.lokalise.taskGroup
import java.io.File
import java.nio.charset.StandardCharsets

open class UploadStrings : DefaultTask() {

    @Input
    var apiConfig: ApiConfig = ApiConfig("", "")
    @Input
    var uploadEntries: List<UploadEntry> = emptyList()

    init {
        group = taskGroup
    }

    //todo configure what to upload

    @TaskAction
    fun upload() {
        uploadEntries.forEach { entry ->
            println("Uploading...")
            println(entry)

            val file = File(entry.path)

            val encoded =
                Base64.encodeBase64(FileUtils.readFileToByteArray(file))
            val data = String(encoded, StandardCharsets.US_ASCII)

            val dto = UploadFileDto(
                data,
                file.name,
                entry.lang,
                entry.convertPlaceholders
            )

            val response = Api.api.uploadFile(apiConfig.projectId,dto).execute()

            if (!response.isSuccessful) {
                throw RuntimeException("${response.message()} - ${response.code()}")
            }else{
                println(response.body()?.string())
            }
        }
    }
}