package com.DoacaoSemFronteiras.service

import com.DoacaoSemFronteiras.model.ONG
import com.DoacaoSemFronteiras.repository.ONGRepository
import com.DoacaoSemFronteiras.DTO.ONGDto
import java.util.Optional


interface IONGService {

    //CRUD Operations
    fun create(ong: ONGDto): ONGDto
    fun createAll(ongs: MutableList<ONGDto>): MutableList<ONGDto>
    fun getAll(): List<ONGDto>
    fun getById(id: Long) : ONGDto?
    fun update(id: Long, ONGDto: ONGDto) : ONGDto?
    fun delete(id: Long)

    //Custom Getter Functions
    fun getBrasilONGs(): List<ONGDto>
    fun getPortugalONGs(): List<ONGDto>
    fun getByCategory(category: String): List<ONGDto>
}