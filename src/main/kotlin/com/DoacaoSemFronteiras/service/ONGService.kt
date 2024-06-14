package com.DoacaoSemFronteiras.service

import com.DoacaoSemFronteiras.repository.ONGRepository
import com.DoacaoSemFronteiras.DTO.ONGDto
import com.DoacaoSemFronteiras.DTO.toEntity
import org.springframework.stereotype.Service

@Service
class ONGService(private val repository: ONGRepository) : IONGService{

    override fun create(ong: ONGDto): ONGDto {
        return repository.save(ong.toEntity()).toDto()
     }

    override fun createAll(ongs: MutableList<ONGDto>): MutableList<ONGDto> {
        val entities = ongs.map { it.toEntity() }               // Convert to Entity
        val savedEntities = repository.saveAll(entities)        // Save the entities
        return savedEntities.map { it.toDto() }.toMutableList() // Return on DTO format
     }

    override fun getAll(): List<ONGDto> { 
        return repository.findAll().map { it.toDto() }
     }

    override fun getById(id: Long): ONGDto? {
        return repository.findById(id).orElse(null)?.toDto()
     }

    override fun update(id: Long, ongData: ONGDto): ONGDto? {
        return repository.findById(id).map{
            val newOng = it.copy(
                name = ongData.name,
                category = ongData.category,
                country = ongData.country,
                url = ongData.url
            )

            repository.save(newOng).toDto()
        }.orElse(null)
     }

    override fun delete(id: Long) {
        //If the ong wasn't found, it throws EmptyResultDataAccessException
        repository.deleteById(id)
     }

    override fun getBrasilONGs(): List<ONGDto> {
        return repository.findAll().filter { 
            it.country == "Brasil" 
        }.map { it.toDto() }
     }

    override fun getPortugalONGs(): List<ONGDto> {
        return repository.findAll().filter { 
            it.country == "Portugal" 
        }.map { it.toDto() }
     }

    override fun getByCategory(category: String): List<ONGDto> {
        return repository.findAll().filter { 
            it.category == category
        }.map { it.toDto() }
     }

}