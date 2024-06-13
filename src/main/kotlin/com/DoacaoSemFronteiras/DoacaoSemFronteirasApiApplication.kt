package com.DoacaoSemFronteiras

import com.DoacaoSemFronteiras.external_data.CSVFile
import com.DoacaoSemFronteiras.repository.ONGRepository
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class DoacaoSemFronteirasApiApplication {

	@Bean
	fun initializer(ongRepository: ONGRepository): ApplicationRunner {
		return ApplicationRunner {
			val csvFile = CSVFile("src/main/kotlin/com/DoacaoSemFronteiras/external_data/ongs.csv")
			csvFile.ongs.forEach { ongRepository.save(it) }
		}
	}
}

fun main(args: Array<String>) {
	runApplication<DoacaoSemFronteirasApiApplication>(*args)
}
