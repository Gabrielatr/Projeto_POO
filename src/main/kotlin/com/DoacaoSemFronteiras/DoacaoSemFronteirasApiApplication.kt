package com.DoacaoSemFronteiras

import com.DoacaoSemFronteiras.external_data.CSVFile
import com.DoacaoSemFronteiras.repository.ONGRepository
import com.DoacaoSemFronteiras.service.ONGService
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import com.DoacaoSemFronteiras.DTO.toDto

@SpringBootApplication
class DoacaoSemFronteirasApiApplication {

	@Bean
	fun initializer(service: ONGService): ApplicationRunner {
		return ApplicationRunner {
			try {
				if (service.getAll().isEmpty()) {
					val csvFile =
						CSVFile("C:\\Users\\conta\\OneDrive\\Ambiente de Trabalho\\dsfproject\\POOprojeto\\Projeto_POO\\src\\main\\kotlin\\com\\DoacaoSemFronteiras\\external data\\ongs.csv")
					val ongDTos = csvFile.ongs.map { it.toDto() }
					service.createAll(ongDTos.toMutableList())
				}
			}catch (e: Exception) {
				e.printStackTrace()
			}

		}
	}
}
fun main(args: Array<String>) {
	runApplication<DoacaoSemFronteirasApiApplication>(*args)
}
