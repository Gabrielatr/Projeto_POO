package com.DoacaoSemFronteiras.controller

import com.DoacaoSemFronteiras.model.Category
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import com.DoacaoSemFronteiras.model.ONG
import com.DoacaoSemFronteiras.repository.ONGRepository
import com.DoacaoSemFronteiras.DTO.ONGDto

@RestController
@RequestMapping("/ongs")
@CrossOrigin(origins = ["http://localhost:63342"])
class ONGController(private val service : IONGService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody ong: ONGDto): ONGDto = service.create(ong)

    @PostMapping("/all")
    @ResponseStatus(HttpStatus.CREATED)
    fun createAll(@RequestBody ongs: MutableList<ONGDto>): MutableList<ONGDto> = service.createAll(ongs)

    @GetMapping
    fun getAll(): List<ONGDto> = service.getAll()

    @GetMapping("/{id:\\d+}")
    fun getById(@PathVariable id: Long) : ResponseEntity<ONGDto> =
            service.getById(id)
                ?.let { ResponseEntity.ok(it) }
                ?: ResponseEntity.notFound().build()

    @GetMapping("/Brasil")
    fun getBrasilONGs() : ResponseEntity<List<ONGDto>> {
        return ResponseEntity.ok(service.getBrasilONGs())
    }

    @GetMapping("/Portugal")
    fun getPortugalONGs() : ResponseEntity<List<ONGDto>> {
        return ResponseEntity.ok(service.getPortugalONGs())
    }
    @GetMapping("/{category:[\\p{L}_]+}")
    fun getByCategory(@PathVariable category: String): ResponseEntity<List<ONGDto>> {
        return ResponseEntity.ok(service.getByCategory(category))
    }

    @PutMapping("/{id:\\d+}")
    fun update(@PathVariable id: Long, @RequestBody ong: ONGDto) : ResponseEntity<ONGDto> =
            service.update(id, ong)
                ?.let { ResponseEntity.ok(it) }
                ?: ResponseEntity.notFound().build()

    @DeleteMapping("/{id:\\d+}")
    fun delete(@PathVariable id: Long) : ResponseEntity<Void> {
        service.delete(id)
        return ResponseEntity(HttpStatus.OK)
    }

}