package com.DoacaoSemFronteiras

import com.DoacaoSemFronteiras.ONGRepository
import org.springframework.data.domain.Example
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/ONGs")
class ONGController(private val repository: ONGRepository) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody ONG: ONG): ONG = repository.save(ONG)

    @PostMapping("/all")
    @ResponseStatus(HttpStatus.CREATED)
    fun createAll(@RequestBody ongs: MutableList<ONG>): MutableList<ONG> = repository.saveAll(ongs)

    @GetMapping
    fun getAll(): List<ONG> = repository.findAll()

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long) : ResponseEntity<ONG> =
            repository.findById(id).map {
                ResponseEntity.ok(it)
            }.orElse(ResponseEntity.notFound().build())

    @GetMapping("/Brasil")
    fun getBrasilONGs() : ResponseEntity<List<ONG>> {
        val ongsBrasileiras = repository.findAll().filter { it.country == "Brasil" }
        return ResponseEntity.ok(ongsBrasileiras)
    }

    @GetMapping("/Portugal")
    fun getPortugalONGs() : ResponseEntity<List<ONG>> {
        val ongsPortuguesas = repository.findAll().filter { it.country == "Portugal" }
        return ResponseEntity.ok(ongsPortuguesas)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody ONG: ONG) : ResponseEntity<ONG> =
            repository.findById(id).map {
                val ONGToUpdate = it.copy(
                        name = ONG.name,
                        category = ONG.category,
                        url = ONG.url
                )
                ResponseEntity.ok(repository.save(ONGToUpdate))
            }.orElse(ResponseEntity.notFound().build())

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) : ResponseEntity<Void> =
            repository.findById(id).map {
                repository.delete(it)
                ResponseEntity<Void>(HttpStatus.OK)
            }.orElse(ResponseEntity.notFound().build())

}