package com.DoacaoSemFronteiras.external_data

import com.DoacaoSemFronteiras.model.ONG
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import java.io.*

class CSVFile(val path: String) {
    val ongs = mutableListOf<ONG>()

    init {
        try {
            readFile(File(path))
        } catch (e: IOException) {
            println(e.message)
        } catch (e: Exception) {
            println(e.message)
        }
    }

    fun readFile(csvFile: File) {
        csvFile.forEachLine { line ->
            val props = line.split(",")
            val ong = ONG(
                id = props[0].trim().toLong(),
                name = props[1].trim(),
                category = props[2].trim(),
                country = props[3].trim(),
                url = props[4].trim()
            )
            when (ong.country) {
                "Portugal", "Brasil" -> ongs.add(ong)
            }

        }

    }
}

