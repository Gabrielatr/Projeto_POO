package com.DoacaoSemFronteiras

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/ongs")
class ONGController(private val repository: ONGRepository) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody ONG: ONG): ONG = repository.save(ONG)

    @PostMapping("/all")
    @ResponseStatus(HttpStatus.CREATED)
    fun createAll(@RequestBody ongs: MutableList<ONG>): MutableList<ONG> = repository.saveAll(ongs)

    @GetMapping
    fun getAll(): List<ONG> = repository.findAll()


    @GetMapping("/{id:\\d+}")
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
    @GetMapping("/{category:[\\p{L}_]+}")
    fun getByCategory(@PathVariable category: Category): ResponseEntity<List<ONG>> {
        val ongs = repository.findAll().filter { it.category == category }
        return ResponseEntity.ok(ongs)
//        val ongs = repository.findByCategory(category)
//        return if (ongs.isNotEmpty()) {
//            ResponseEntity.ok(ongs)
//        } else {
//            ResponseEntity.notFound().build()
//        }
    }

    @PutMapping("/{id:\\d+}")
    fun update(@PathVariable id: Long, @RequestBody ONG: ONG) : ResponseEntity<ONG> =
            repository.findById(id).map {
                val ONGToUpdate = it.copy(
                        name = ONG.name,
                        category = ONG.category,
                        url = ONG.url
                )
                ResponseEntity.ok(repository.save(ONGToUpdate))
            }.orElse(ResponseEntity.notFound().build())

    @DeleteMapping("/{id:\\d+}")
    fun delete(@PathVariable id: Long) : ResponseEntity<Void> =
            repository.findById(id).map {
                repository.delete(it)
                ResponseEntity<Void>(HttpStatus.OK)
            }.orElse(ResponseEntity.notFound().build())

}